package com.web.service;

import java.util.List;

import com.web.model.entity.Role;
import com.web.model.entity.User;

public interface IRoleService {
	List<Role> getRoles();

	Role createRole(Role role);

	void deleteRole(Long id);

	Role findByName(String name);

	User removeUserFromRole(Long userId, Long roleId);

	User assignRoleToUser(Long userId, Long roleId);

	Role removeAllUsersFromRole(Long roleId);
}
