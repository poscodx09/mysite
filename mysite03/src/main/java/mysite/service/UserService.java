package mysite.service;

import org.springframework.stereotype.Service;

import mysite.repository.UserRepository;

@Service
public class UserService {
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
}
