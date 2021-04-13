package com.setu.splitwise.repository;

import com.setu.splitwise.exchange.dto.UserWiseAmount;
import com.setu.splitwise.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by anketjain on 13/04/21.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value = "SELECT sum(t.amount) FROM transaction t WHERE receiver_id = :userId", nativeQuery = true)
    BigDecimal findTotalReceivedForUser(@Param("userId") Long userId);

    @Query(value = "SELECT sum(t.amount) FROM transaction t WHERE payee_id = :userId", nativeQuery = true)
    BigDecimal findTotalPaidForUser(@Param("userId") Long userId);

    @Query(value = "SELECT sum(t.amount) FROM transaction t WHERE receiver_id = :userId and group_id = :groupId", nativeQuery = true)
    BigDecimal findTotalReceivedForUserAndGroup(@Param("userId") Long userId, @Param("groupId") Long groupId);

    @Query(value = "SELECT sum(t.amount) FROM transaction t WHERE payee_id = :userId and group_id = :groupId", nativeQuery = true)
    BigDecimal findTotalPaidForUserAndGroup(@Param("userId") Long userId, @Param("groupId") Long groupId);


    @Query(value = "SELECT t.payee_id as userId, sum(t.amount) as sumAMount FROM transaction t WHERE group_id = :groupId group by payee_id", nativeQuery = true)
    List<UserWiseAmount> findUserWisePaidForGroup(@Param("groupId") Long groupId);

    @Query(value = "SELECT t.receiver_id as userId, sum(t.amount) as sumAMount FROM transaction t WHERE group_id = :groupId group by receiver_id", nativeQuery = true)
    List<UserWiseAmount> findUserWiseReceivedForGroup(@Param("groupId") Long groupId);


}
