package com.apap.tutorial8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.service.UserRoleService;

@Controller
@RequestMapping("/user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;
	
	@RequestMapping(value= "/addUser", method = RequestMethod.POST)
	private String addUserSubmit(@ModelAttribute UserRoleModel user){
		userService.addUser(user);
		return "home";
	}
	
	@RequestMapping(value= "/updatePassword", method = RequestMethod.POST)
	private String updatePasswordSubmit(@RequestParam(value="oldPassword", required=true) String oldPassword,
			@RequestParam(value="newPassword", required=true) String newPassword,
			@RequestParam(value="confirmNewPassword", required=true) String confirmNewPassword, Model model) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		
		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}
		
		boolean isValid = userService.validatePassword(username,oldPassword, newPassword, confirmNewPassword);
		
		if ( isValid ) {
			userService.updatePassword(username,newPassword);
			model.addAttribute("msg", "password berhasil diubah");
		}
		
		return "home";
	}
}