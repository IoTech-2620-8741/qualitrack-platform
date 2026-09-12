package com.iotech.qualitrack.platform.iam.application.internal.queryservices;

import com.iotech.qualitrack.platform.iam.application.queryservices.UserQueryService;
import com.iotech.qualitrack.platform.iam.domain.model.aggregates.User;
import com.iotech.qualitrack.platform.iam.domain.model.queries.GetAllUsersQuery;
import com.iotech.qualitrack.platform.iam.domain.model.queries.GetUserByIdQuery;
import com.iotech.qualitrack.platform.iam.domain.model.queries.GetUserByUsernameQuery;
import com.iotech.qualitrack.platform.iam.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application query service implementation for IAM users.
 */
@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.userId());
    }

    @Override
    public Optional<User> handle(GetUserByUsernameQuery query) {
        return userRepository.findByUsername(query.username());
    }

    @Override
    public List<User> handle(GetAllUsersQuery query) {
        return userRepository.findAll();
    }
}