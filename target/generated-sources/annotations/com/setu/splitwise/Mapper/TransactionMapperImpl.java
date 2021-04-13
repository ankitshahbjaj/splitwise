package com.setu.splitwise.Mapper;

import com.setu.splitwise.exchange.businessObject.TransactionBO;
import com.setu.splitwise.exchange.businessObject.UserBO;
import com.setu.splitwise.model.Transaction;
import com.setu.splitwise.model.User;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-13T17:19:44+0530",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_111 (Oracle Corporation)"
)
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public TransactionBO map(Transaction transaction, UserBO payee, UserBO receiver) {
        if ( transaction == null && payee == null && receiver == null ) {
            return null;
        }

        TransactionBO transactionBO = new TransactionBO();

        if ( transaction != null ) {
            transactionBO.setId( transaction.getId() );
            transactionBO.setPayee( userToUserBO( transaction.getPayee() ) );
            transactionBO.setReceiver( userToUserBO( transaction.getReceiver() ) );
            transactionBO.setAmount( transaction.getAmount() );
            transactionBO.setComment( transaction.getComment() );
        }

        return transactionBO;
    }

    protected UserBO userToUserBO(User user) {
        if ( user == null ) {
            return null;
        }

        UserBO userBO = new UserBO();

        userBO.setId( user.getId() );
        userBO.setName( user.getName() );
        userBO.setPhoneNumber( user.getPhoneNumber() );
        userBO.setEmailId( user.getEmailId() );

        return userBO;
    }
}
