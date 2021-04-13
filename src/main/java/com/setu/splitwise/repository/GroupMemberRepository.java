package com.setu.splitwise.repository;

import com.setu.splitwise.model.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by anketjain on 13/04/21.
 */
@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long>{
    List<GroupMember> findByUser_id(Long userId);

    List<GroupMember> findByGroup_id(Long groupId);

    Optional<GroupMember> findByGroup_idAndUser_id(Long groupId, Long userId);
}
