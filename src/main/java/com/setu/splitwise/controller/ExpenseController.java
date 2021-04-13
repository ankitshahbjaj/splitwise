package com.setu.splitwise.controller;

import com.setu.splitwise.exchange.businessObject.ExpenseBO;
import com.setu.splitwise.exchange.businessObject.TransactionBO;
import com.setu.splitwise.exchange.request.AddNewExpenseRequest;
import com.setu.splitwise.exchange.request.AddNewTransactionRequest;
import com.setu.splitwise.exchange.response.GetExpenseResponse;
import com.setu.splitwise.service.ExpenseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.ws.rs.POST;
import java.util.List;

/**
 * Created by anketjain on 13/04/21.
 */
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping("expenses")
    public String addNewExpense(@Valid @RequestBody AddNewExpenseRequest request) {
        return expenseService.addNewExpense(request);
    }

    @PostMapping("expenses/add-txn")
    public String addtransactionInExpense(@Valid @RequestBody AddNewTransactionRequest request) {
        return expenseService.addNewTransaction(request);
    }

    @GetMapping("expenses/group/{groupId}")
    public List<ExpenseBO> getAllExpenseInGroup(@PathVariable("groupId") Long groupId) {
        return expenseService.getAllExpenseInGroup(groupId);
    }

    @GetMapping("expenses/{expenseId}")
    public GetExpenseResponse getExpenseById(@PathVariable("expenseId") Long expenseId) {
        return expenseService.getExpenseById(expenseId);
    }

}
