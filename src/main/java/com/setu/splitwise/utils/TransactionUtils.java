package com.setu.splitwise.utils;

import com.setu.splitwise.exchange.dto.UserWiseAmount;
import com.setu.splitwise.model.GroupMember;
import com.setu.splitwise.repository.GroupMemberRepository;
import com.setu.splitwise.repository.TransactionRepository;
import org.glassfish.jersey.internal.guava.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by anketjain on 13/04/21.
 */
public class TransactionUtils {
    private final TransactionRepository transactionRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Autowired
    public TransactionUtils(TransactionRepository transactionRepository,
                            GroupMemberRepository groupMemberRepository) {
        this.transactionRepository = transactionRepository;
        this.groupMemberRepository = groupMemberRepository;
    }

    public BigDecimal getTotalDuesForUser(Long userId) {
        Preconditions.checkNotNull(userId, "userId is required for fetching total dues");

        BigDecimal totalPaid = transactionRepository.findTotalPaidForUser(userId);
        BigDecimal totalReceived = transactionRepository.findTotalReceivedForUser(userId);

        totalPaid = totalPaid != null ? totalPaid : BigDecimal.ZERO;
        totalReceived = totalReceived != null ? totalReceived : BigDecimal.ZERO;

        return totalPaid.subtract(totalReceived);
    }

    public BigDecimal getDuesForUserForGroup(Long userId, Long groupId) {
        Preconditions.checkNotNull(userId, "userId is required for fetching group total dues");
        Preconditions.checkNotNull(groupId, "groupId is required for fetching group total dues");

        BigDecimal totalPaid = transactionRepository.findTotalReceivedForUserAndGroup(userId, groupId);
        BigDecimal totalReceived = transactionRepository.findTotalReceivedForUserAndGroup(userId, groupId);

        totalPaid = totalPaid != null ? totalPaid : BigDecimal.ZERO;
        totalReceived = totalReceived != null ? totalReceived : BigDecimal.ZERO;

        return totalPaid.subtract(totalReceived);
    }

    public Map<Long, BigDecimal> userWiseDuesForGroup(Long groupId) {
        Preconditions.checkNotNull(groupId, "groupId is required for fetching userWiseDuesForGroup");

        Map<Long, BigDecimal> userWisePaid = transactionRepository.findUserWisePaidForGroup(groupId).stream()
                .collect(Collectors.toMap(UserWiseAmount::getUserId, UserWiseAmount::getSumAmount));

        Map<Long, BigDecimal> userWiseReceive = transactionRepository.findUserWiseReceivedForGroup(groupId).stream()
                .collect(Collectors.toMap(UserWiseAmount::getUserId, UserWiseAmount::getSumAmount));


        List<GroupMember> members = groupMemberRepository.findByGroup_id(groupId);

        Map<Long, BigDecimal> response = new HashMap<>();

        for(GroupMember member: members) {
            Long userId = member.getUser().getId();
            BigDecimal totalPaid = userWisePaid.getOrDefault(userId, BigDecimal.ZERO);
            BigDecimal totalReceived = userWiseReceive.getOrDefault(userId, BigDecimal.ZERO);
            response.put(userId, totalPaid.subtract(totalReceived));
        }

        return response;
    }
}


