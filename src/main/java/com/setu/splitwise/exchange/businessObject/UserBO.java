package com.setu.splitwise.exchange.businessObject;

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
public class UserBO {
    private Long id;
    private String name;
    private String phoneNumber;
    private String emailId;
    private BigDecimal dues;
}
