package com.hotel.user.service;

import com.hotel.common.entity.User;
import com.hotel.user.exception.UserAlreadyExistsException;
import com.hotel.user.model.dto.reponse.UserResponse;
import com.hotel.user.model.dto.request.UserRequest;


import java.util.List;

public interface UserService {
    User registerUser(User user) throws UserAlreadyExistsException;
    UserResponse login (UserRequest userRequest) throws Exception;
    List<User> getUsers();
    void deleteUser(String email);
    User getUser(String email);
}
