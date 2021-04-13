package com.setu.splitwise.service;

import com.setu.splitwise.Mapper.UserMapper;
import com.setu.splitwise.exchange.businessObject.UserBO;
import com.setu.splitwise.exchange.request.CreateUserRequest;
import com.setu.splitwise.model.User;
import com.setu.splitwise.repository.UserRepository;
import com.setu.splitwise.utils.TransactionUtils;
import org.glassfish.jersey.internal.guava.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.ws.rs.NotAllowedException;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by anketjain on 13/04/21.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final TransactionUtils transactionUtils;

    @Autowired
    public UserService(UserRepository userRepository,
                       TransactionUtils transactionUtils) {
        this.userRepository = userRepository;
        this.transactionUtils = transactionUtils;
    }

    public UserBO createUser(CreateUserRequest request) {
        Preconditions.checkNotNull(request, "Request can not be null for creating user");
        Preconditions.checkArgument(!StringUtils.isEmpty(request.getUserName()), "User name is required for creating user");
        Preconditions.checkArgument(!StringUtils.isEmpty(request.getPhoneNumber()), "User phoneNumber is required for creating user");

        Optional<User> optionalUser = userRepository.findByPhoneNumber(request.getPhoneNumber());
        if(optionalUser.isPresent()) {
            throw new NotAllowedException(String.format("User already exist with phone number %s", request.getPhoneNumber()));
        }

        optionalUser = userRepository.findByEmailId(request.getEmailId());
        if(optionalUser.isPresent()) {
            throw new NotAllowedException(String.format("User already exist with phone number %s", request.getPhoneNumber()));
        }

        User user = User.builder()
                .name(request.getUserName())
                .phoneNumber(request.getPhoneNumber())
                .emailId(request.getEmailId())
                .build();

        user = userRepository.save(user);

        return populateUserBOFromUser(user);
    }

    public UserBO getUserByPhoneNumber(String phoneNumber) {
        Preconditions.checkArgument(!StringUtils.isEmpty(phoneNumber), "User phoneNumber is required for getting user");

        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        Preconditions.checkArgument(optionalUser.isPresent(), String.format("No user found for id %s", optionalUser));

        return populateUserBOFromUser(optionalUser.get());
    }

    public UserBO getUserById(Long id) {
        Preconditions.checkNotNull(id, "User id is required for getting user");

        Optional<User> optionalUser = userRepository.findById(id);
        Preconditions.checkArgument(optionalUser.isPresent(), String.format("No user found for id %s", id));

        return populateUserBOFromUser(optionalUser.get());
    }


    private UserBO populateUserBOFromUser(User user) {
        Preconditions.checkNotNull(user, "User is required for userBO");

        BigDecimal totalDues = transactionUtils.getTotalDuesForUser(user.getId());

        UserBO userBO = UserMapper.INSTANCE.map(user);
        userBO.setDues(totalDues);

        return userBO;
    }
}
