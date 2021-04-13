package com.setu.splitwise.Mapper;

import com.setu.splitwise.exchange.businessObject.TransactionBO;
import com.setu.splitwise.exchange.businessObject.UserBO;
import com.setu.splitwise.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by anketjain on 13/04/21.
 */
@Mapper
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionBO map(Transaction transaction, UserBO payee, UserBO receiver);

}
