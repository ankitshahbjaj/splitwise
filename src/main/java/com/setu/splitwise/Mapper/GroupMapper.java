package com.setu.splitwise.Mapper;

import com.setu.splitwise.exchange.businessObject.GroupBO;
import com.setu.splitwise.exchange.businessObject.UserBO;
import com.setu.splitwise.model.Group;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created by anketjain on 13/04/21.
 */
@Mapper
public interface GroupMapper {
    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    GroupBO map(Group group, List<UserBO> participants);

    List<GroupBO> map(List<Group> groups);
}
