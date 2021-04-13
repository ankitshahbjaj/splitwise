package com.setu.splitwise.Mapper;

import com.setu.splitwise.exchange.businessObject.UserBO;
import com.setu.splitwise.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-13T17:11:52+0530",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_111 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserBO map(User user) {
        if ( user == null ) {
            return null;
        }

        UserBO userBO = new UserBO();

        userBO.setId( user.getId() );
        userBO.setName( user.getName() );
        userBO.setPhoneNumber( user.getPhoneNumber() );
        userBO.setEmailId( user.getEmailId() );

        return userBO;
    }

    @Override
    public List<UserBO> map(List<User> user) {
        if ( user == null ) {
            return null;
        }

        List<UserBO> list = new ArrayList<UserBO>( user.size() );
        for ( User user1 : user ) {
            list.add( map( user1 ) );
        }

        return list;
    }
}
