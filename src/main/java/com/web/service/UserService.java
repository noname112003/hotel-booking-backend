package com.web.service;

import com.web.exception.UserAlreadyExistsException;
import com.web.model.dto.reponse.UserResponse;
import com.web.model.dto.request.UserRequest;
import com.web.model.entity.User;

import java.util.List;

public interface UserService {
    User registerUser(User user) throws UserAlreadyExistsException;
    UserResponse login (UserRequest userRequest) throws Exception;
    List<User> getUsers();
    void deleteUser(String email);
    User getUser(String email);
}
