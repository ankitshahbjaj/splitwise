package com.setu.splitwise.Mapper;

import com.setu.splitwise.exchange.businessObject.UserBO;
import com.setu.splitwise.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created by anketjain on 13/04/21.
 */
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserBO map(User user);

    List<UserBO> map(List<User> user);
}
