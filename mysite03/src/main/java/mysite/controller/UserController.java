package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import mysite.service.UserService;

@Controller
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
//	@RequestMapping("/join")
//	public String join() {
//		return "user/join";
//	}
	
	
}
