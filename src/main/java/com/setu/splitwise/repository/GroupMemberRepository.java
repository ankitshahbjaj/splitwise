package com.setu.splitwise.repository;

import com.setu.splitwise.model.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by anketjain on 13/04/21.
 */
@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long>{
    List<GroupMember> findByUser_id(Long userId);

    List<GroupMember> findByGroup_id(Long groupId);
}
