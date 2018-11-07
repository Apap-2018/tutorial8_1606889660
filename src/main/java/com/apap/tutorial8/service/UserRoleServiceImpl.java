package com.apap.tutorial8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.repository.UserRoleDb;

@Service
public class UserRoleServiceImpl implements UserRoleService{
	@Autowired
	private UserRoleDb userDb;
	
	@Override 
	public UserRoleModel addUser(UserRoleModel user){
		String pass = encrypt(user.getPassword());
		user.setPassword(pass);
		return userDb.save(user);
	}
	
	@Override
	public String encrypt(String password){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}
	
	

	@Override
	public boolean validatePassword(String username, String oldPassword, String newPassword, String confirmNewPassword) {
		
		UserRoleModel user = userDb.findByUsername(username);

		
		if(newPassword.equals(confirmNewPassword)) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if(passwordEncoder.matches(oldPassword, user.getPassword())) {
				return true;
			}
		}
		
		
		return false;
	}

	@Override
	public void updatePassword(String username, String newPassword) {
		UserRoleModel user = userDb.findByUsername(username);
		user.setPassword(encrypt(newPassword));
		userDb.save(user);
	}
	
}