package com.setu.splitwise.exchange.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by anketjain on 13/04/21.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddNewExpenseRequest {
    private Long groupId;
    private BigDecimal amount;
    private String title;
    private String description;
    private List<ExpenseTransaction> transactionList;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ExpenseTransaction {
        private Long payeeId;
        private Long receiverId;
        private BigDecimal amount;
        private String comment;
    }
}
