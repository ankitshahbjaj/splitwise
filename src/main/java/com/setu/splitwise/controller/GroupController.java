package com.setu.splitwise.controller;

import com.setu.splitwise.exchange.businessObject.ExpenseBO;
import com.setu.splitwise.exchange.businessObject.GroupBO;
import com.setu.splitwise.exchange.request.CreateGroupRequest;
import com.setu.splitwise.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by anketjain on 13/04/21.
 */
@RestController
public class GroupController {
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("groups")
    public GroupBO createGroup(@Valid @RequestBody CreateGroupRequest request) {
        return groupService.createGroup(request);
    }

    @GetMapping("groups/user/{userId}")
    public List<GroupBO> getGroupsForUser(@PathVariable("userId") Long userId) {
        return groupService.getGroupsForUser(userId);
    }

    @GetMapping("groups/{groupId}")
    public GroupBO getGroupsById(@PathVariable("groupId") Long groupId) {
        return groupService.getGroupsById(groupId);
    }
}
