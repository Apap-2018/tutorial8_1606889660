package com.apap.tutorial8.service;

import com.apap.tutorial8.model.UserRoleModel;

public interface UserRoleService {
	UserRoleModel addUser(UserRoleModel user);
	public String encrypt(String password);
	boolean validatePassword(String username,String oldPassword, String newPassword, String confirmNewPassword);
	void updatePassword(String username, String newPassword);
}