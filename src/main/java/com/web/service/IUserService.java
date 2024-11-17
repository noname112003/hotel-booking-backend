package com.web.service;

import java.util.List;

import com.web.model.entity.User;

public interface IUserService {
	User registerUser(User user);

	List<User> getUsers();

	void deleteUser(String email);

	User getUserByEmail(String email);
}