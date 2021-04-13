package com.setu.splitwise.Mapper;

import com.setu.splitwise.exchange.businessObject.GroupBO;
import com.setu.splitwise.exchange.businessObject.UserBO;
import com.setu.splitwise.model.Group;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-13T17:19:44+0530",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_111 (Oracle Corporation)"
)
public class GroupMapperImpl implements GroupMapper {

    @Override
    public GroupBO map(Group group, List<UserBO> participants) {
        if ( group == null && participants == null ) {
            return null;
        }

        GroupBO groupBO = new GroupBO();

        if ( group != null ) {
            if ( group.getId() != null ) {
                groupBO.setId( String.valueOf( group.getId() ) );
            }
            groupBO.setGroupName( group.getGroupName() );
        }
        if ( participants != null ) {
            List<UserBO> list = participants;
            if ( list != null ) {
                groupBO.setParticipants( new ArrayList<UserBO>( list ) );
            }
            else {
                groupBO.setParticipants( null );
            }
        }

        return groupBO;
    }

    @Override
    public List<GroupBO> map(List<Group> groups) {
        if ( groups == null ) {
            return null;
        }

        List<GroupBO> list = new ArrayList<GroupBO>( groups.size() );
        for ( Group group : groups ) {
            list.add( groupToGroupBO( group ) );
        }

        return list;
    }

    protected GroupBO groupToGroupBO(Group group) {
        if ( group == null ) {
            return null;
        }

        GroupBO groupBO = new GroupBO();

        if ( group.getId() != null ) {
            groupBO.setId( String.valueOf( group.getId() ) );
        }
        groupBO.setGroupName( group.getGroupName() );

        return groupBO;
    }
}
