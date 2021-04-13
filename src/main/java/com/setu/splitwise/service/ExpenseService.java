package com.setu.splitwise.service;

import com.setu.splitwise.Mapper.ExpenseMapper;
import com.setu.splitwise.Mapper.TransactionMapper;
import com.setu.splitwise.Mapper.UserMapper;
import com.setu.splitwise.exchange.businessObject.ExpenseBO;
import com.setu.splitwise.exchange.businessObject.TransactionBO;
import com.setu.splitwise.exchange.businessObject.UserBO;
import com.setu.splitwise.exchange.request.AddNewExpenseRequest;
import com.setu.splitwise.exchange.request.AddNewTransactionRequest;
import com.setu.splitwise.exchange.response.GetExpenseResponse;
import com.setu.splitwise.model.Expense;
import com.setu.splitwise.model.Group;
import com.setu.splitwise.model.Transaction;
import com.setu.splitwise.model.User;
import com.setu.splitwise.repository.ExpenseRepository;
import com.setu.splitwise.repository.GroupRepository;
import com.setu.splitwise.repository.TransactionRepository;
import com.setu.splitwise.repository.UserRepository;
import org.glassfish.jersey.internal.guava.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by anketjain on 13/04/21.
 */
@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public ExpenseService(ExpenseRepository expenseRepository,
                          GroupRepository groupRepository,
                          UserRepository userRepository,
                          TransactionRepository transactionRepository) {
        this.expenseRepository = expenseRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public String addNewExpense(AddNewExpenseRequest request) {
        validateNewExpenseRequest(request);

        Optional<Group> groupOptional = groupRepository.findById(request.getGroupId());
        Preconditions.checkArgument(groupOptional.isPresent(), String.format("No group found for %s", request.getGroupId()));

        Expense expense = Expense.builder()
                .amount(request.getAmount())
                .description(request.getDescription())
                .group(groupOptional.get())
                .title(request.getTitle())
                .build();

        Set<Long> userIds = request.getTransactionList().stream()
                .collect(HashSet::new, (s, m) -> {
                    s.add(m.getPayeeId());
                    s.add(m.getReceiverId());
                }, Set::addAll);


        Map<Long, User> usersMap = userRepository.findAllById(userIds).stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        List<Transaction> transactions = request.getTransactionList().stream()
                .map(t -> { return Transaction.builder()
                            .amount(t.getAmount())
                            .comment(t.getComment())
                            .payee(usersMap.get(t.getPayeeId()))
                            .receiver(usersMap.get(t.getReceiverId()))
                            .expense(expense)
                            .build();
                }).collect(Collectors.toList());

        expense.setTransactions(transactions);
        expenseRepository.save(expense);

        return "Done";
    }

    public String addNewTransaction(AddNewTransactionRequest request) {
        Preconditions.checkNotNull(request, "Request can not be null for adding transaction");
        Preconditions.checkNotNull(request.getExpenseId(), "Expense Id can not be null for adding transaction");
        Preconditions.checkNotNull(request.getPayeeId(), "Payee Id not be null for adding transaction");
        Preconditions.checkNotNull(request.getReceiverId(), "Receiver Id not be null for adding transaction");
        Preconditions.checkNotNull(request.getAmount(), "Amount can not be null for adding transaction");
        Preconditions.checkNotNull(request.getAmount().compareTo(BigDecimal.ZERO) == 1, "Amount should be greater then 0 in transaction");

        Optional<Expense> expenseOptional = expenseRepository.findById(request.getExpenseId());
        Preconditions.checkArgument(expenseOptional.isPresent(), String.format("No Expense found for Id : %s", request.getExpenseId()));

        Optional<User> payeeOptional = userRepository.findById(request.getPayeeId());
        Preconditions.checkArgument(payeeOptional.isPresent(), String.format("No User found for Id : %s", request.getPayeeId()));

        Optional<User> receiverOptional = userRepository.findById(request.getReceiverId());
        Preconditions.checkArgument(payeeOptional.isPresent(), String.format("No User found for Id : %s", request.getReceiverId()));

        Transaction txn = Transaction.builder()
                .expense(expenseOptional.get())
                .payee(payeeOptional.get())
                .receiver(receiverOptional.get())
                .amount(request.getAmount())
                .comment(request.getComment())
                .build();

        transactionRepository.save(txn);
        return "Done";
    }

    public List<ExpenseBO> getAllExpenseInGroup(Long groupId) {
        Preconditions.checkNotNull(groupId, "groupId is mandatory for fetching expenses");

        List<Expense> expenses = expenseRepository.findByGroup_id(groupId);

        return ExpenseMapper.INSTANCE.map(expenses);
    }

    public GetExpenseResponse getExpenseById(Long expenseId) {
        Preconditions.checkNotNull(expenseId, "expenseId is mandatory for fetching getExpenseById");

        Optional<Expense> expenseOptional = expenseRepository.findById(expenseId);
        Preconditions.checkArgument(expenseOptional.isPresent(), String.format("No expense found for the Id : %s", expenseId));

        List<Transaction> transactions = expenseOptional.get().getTransactions();
        Set<User> users = transactions.stream()
                .collect(HashSet::new, (s, m) -> {
                    s.add(m.getPayee());
                    s.add(m.getReceiver());
                }, Set::addAll);

        Map<Long, UserBO> userMap = users.stream()
                .collect(Collectors.toMap(User::getId, u -> UserMapper.INSTANCE.map(u), (o1, o2) -> o1));

        ExpenseBO expenseBO = ExpenseMapper.INSTANCE.map(expenseOptional.get());

        List<TransactionBO> txnBOs = transactions.stream()
                .map(t -> TransactionMapper.INSTANCE.map(t, userMap.get(t.getPayee().getId()), userMap.get(t.getReceiver().getId())))
                .collect(Collectors.toList());

        return GetExpenseResponse.builder()
                .expense(expenseBO)
                .transactions(txnBOs)
                .build();
    }

    private void validateNewExpenseRequest(AddNewExpenseRequest request) {
        Preconditions.checkNotNull(request, "Request can not be null for adding expense");
        Preconditions.checkNotNull(request.getGroupId(), "Group Id is mandatory for adding expense");
        Preconditions.checkNotNull(request.getAmount(), "Amount is mandatory for adding expense");
        Preconditions.checkArgument(request.getAmount().compareTo(BigDecimal.ZERO) == 1, "Amount should be greater then 0 for adding expense");
        Preconditions.checkArgument(!CollectionUtils.isEmpty(request.getTransactionList()), "Transaction list can not be empty for adding expense");
        for(AddNewExpenseRequest.ExpenseTransaction txn: request.getTransactionList()) {
            Preconditions.checkNotNull(txn, "Transaction can not be null for adding expense");
            Preconditions.checkNotNull(txn.getPayeeId(), "PayeeId can not be null in transaction for adding expense");
            Preconditions.checkNotNull(txn.getReceiverId(), "ReceiverId can not be null in transaction for adding expense");
            Preconditions.checkNotNull(txn.getAmount().compareTo(BigDecimal.ZERO) == 1, "Amount should be greater then 0 in transaction for adding expense");
        }
    }
}
