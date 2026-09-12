package com.iotech.qualitrack.platform.iam.application.queryservices;

import com.iotech.qualitrack.platform.iam.domain.model.aggregates.User;
import com.iotech.qualitrack.platform.iam.domain.model.queries.GetAllUsersQuery;
import com.iotech.qualitrack.platform.iam.domain.model.queries.GetUserByIdQuery;
import com.iotech.qualitrack.platform.iam.domain.model.queries.GetUserByUsernameQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application query service contract for IAM user read operations.
 */
public interface UserQueryService {

    Optional<User> handle(GetUserByIdQuery query);

    Optional<User> handle(GetUserByUsernameQuery query);

    List<User> handle(GetAllUsersQuery query);
}