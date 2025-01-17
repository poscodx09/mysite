package mysite.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import mysite.repository.UserRepository;
import mysite.vo.UserVo;

@Service
public class UserService {
	private PasswordEncoder passwordEncoder;
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public void join(UserVo userVo) {
		userVo.setPassword(passwordEncoder.encode(userVo.getPassword()));
		userRepository.insert(userVo);
	}

	// 로그인
	public UserVo getUser(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

	// 업데이트
	public UserVo getUser(long id) {
		return userRepository.findByUserNo(id);
	}
	
	public UserVo getUser(String email) {
		UserVo userVo = userRepository.findByEmail(email, UserVo.class);
		userVo.setPassword("");
		return userVo;
		
	}

	public void update(long id, UserVo userVo) {
		userRepository.updateByUserNo(id, userVo);
	}



}
