package com.setu.splitwise.Mapper;

import com.setu.splitwise.exchange.businessObject.ExpenseBO;
import com.setu.splitwise.model.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created by anketjain on 13/04/21.
 */
@Mapper
public interface ExpenseMapper {
    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    ExpenseBO map(Expense expense);

    List<ExpenseBO> map(List<Expense> expense);

}
