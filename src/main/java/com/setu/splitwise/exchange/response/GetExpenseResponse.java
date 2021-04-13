package com.setu.splitwise.exchange.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.setu.splitwise.exchange.businessObject.ExpenseBO;
import com.setu.splitwise.exchange.businessObject.TransactionBO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by anketjain on 13/04/21.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetExpenseResponse {
    private ExpenseBO expense;
    private List<TransactionBO> transactions;
}
