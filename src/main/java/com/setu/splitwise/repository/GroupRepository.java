package com.setu.splitwise.repository;

import com.setu.splitwise.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by anketjain on 13/04/21.
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
