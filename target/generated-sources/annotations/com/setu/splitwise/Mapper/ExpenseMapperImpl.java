package com.setu.splitwise.Mapper;

import com.setu.splitwise.exchange.businessObject.ExpenseBO;
import com.setu.splitwise.model.Expense;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-13T17:11:52+0530",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_111 (Oracle Corporation)"
)
public class ExpenseMapperImpl implements ExpenseMapper {

    @Override
    public ExpenseBO map(Expense expense) {
        if ( expense == null ) {
            return null;
        }

        ExpenseBO expenseBO = new ExpenseBO();

        expenseBO.setId( expense.getId() );
        expenseBO.setAmount( expense.getAmount() );
        expenseBO.setTitle( expense.getTitle() );
        expenseBO.setDescription( expense.getDescription() );

        return expenseBO;
    }

    @Override
    public List<ExpenseBO> map(List<Expense> expense) {
        if ( expense == null ) {
            return null;
        }

        List<ExpenseBO> list = new ArrayList<ExpenseBO>( expense.size() );
        for ( Expense expense1 : expense ) {
            list.add( map( expense1 ) );
        }

        return list;
    }
}
