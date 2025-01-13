package mysite.service;

import org.springframework.stereotype.Service;

import mysite.repository.UserRepository;
import mysite.vo.UserVo;

@Service
public class UserService {
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void join(UserVo userVo) {
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
		return userRepository.findByEmail(email);
	}

	public void update(long id, UserVo userVo) {
		userRepository.updateByUserNo(id, userVo);
	}



}
