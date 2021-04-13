package com.setu.splitwise.exchange.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Created by anketjain on 13/04/21.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddNewTransactionRequest {
    private Long expenseId;
    private Long payeeId;
    private Long receiverId;
    private BigDecimal amount;
    private String comment;

}
