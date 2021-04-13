package com.setu.splitwise.service;

import com.setu.splitwise.Mapper.GroupMapper;
import com.setu.splitwise.Mapper.UserMapper;
import com.setu.splitwise.exchange.businessObject.GroupBO;
import com.setu.splitwise.exchange.businessObject.UserBO;
import com.setu.splitwise.exchange.request.CreateGroupRequest;
import com.setu.splitwise.model.Group;
import com.setu.splitwise.model.GroupMember;
import com.setu.splitwise.model.User;
import com.setu.splitwise.repository.GroupMemberRepository;
import com.setu.splitwise.repository.GroupRepository;
import com.setu.splitwise.repository.UserRepository;
import com.setu.splitwise.utils.TransactionUtils;
import org.glassfish.jersey.internal.guava.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by anketjain on 13/04/21.
 */
@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;
    private final TransactionUtils transactionUtils;

    public GroupService(GroupRepository groupRepository,
                        GroupMemberRepository groupMemberRepository,
                        UserRepository userRepository,
                        TransactionUtils transactionUtils) {
        this.groupRepository = groupRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.userRepository = userRepository;
        this.transactionUtils = transactionUtils;
    }

    public GroupBO createGroup(CreateGroupRequest request) {
        Preconditions.checkNotNull(request, "Request can not be null for creating group");
        Preconditions.checkNotNull(request.getCreatorId(), "Creator is required for creating group");
        Preconditions.checkArgument(!StringUtils.isEmpty(request.getGroupName()), "Group name is required for creating group");


        Group group = Group.builder()
                .groupName(request.getGroupName())
                .build();

        Set<Long> userIds = CollectionUtils.isEmpty(request.getParticipantIds()) ? new HashSet<>() : request.getParticipantIds();
        userIds.add(request.getCreatorId());

        List<User> users = userRepository.findAllById(userIds);

        List<GroupMember> groupMembers = users.stream()
                .map(u -> GroupMember.builder()
                        .group(group)
                        .user(u)
                        .build())
                .collect(Collectors.toList());

        group.setMembers(groupMembers);

        Group createdGroup = groupRepository.save(group);

        List<User> userList = createdGroup.getMembers().stream()
                .map(GroupMember::getUser)
                .collect(Collectors.toList());

        List<UserBO> userBOs = UserMapper.INSTANCE.map(userList);
        userBOs.forEach(u -> { u.setDues(BigDecimal.ZERO);});

        return GroupMapper.INSTANCE.map(createdGroup, userBOs);
    }

    public List<GroupBO> getGroupsForUser(Long userId) {
        Preconditions.checkNotNull(userId, "userId is required for fetching groups");

        List<Group> groups = groupMemberRepository.findByUser_id(userId).stream()
                .map(GroupMember::getGroup)
                .collect(Collectors.toList());

        return GroupMapper.INSTANCE.map(groups);
    }

    public GroupBO getGroupsById(Long groupId) {
        Preconditions.checkNotNull(groupId, "groupId is required for fetching group");

        Optional<Group> groupOptional = groupRepository.findById(groupId);
        Preconditions.checkArgument(groupOptional.isPresent(), String.format("No group found for %s", groupId));

        List<User> userList = groupOptional.get().getMembers().stream()
                .map(GroupMember::getUser)
                .collect(Collectors.toList());

        Map<Long, BigDecimal> userDues = transactionUtils.userWiseDuesForGroup(groupId);

        List<UserBO> userBOs = UserMapper.INSTANCE.map(userList);
        userBOs.forEach(u -> { u.setDues(userDues.getOrDefault(u.getId(), BigDecimal.ZERO));});

        return GroupMapper.INSTANCE.map(groupOptional.get(), userBOs);
    }
}
