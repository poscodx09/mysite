package mysite.repository;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import mysite.vo.UserVo;

@Repository
public class UserRepository {

	private SqlSession sqlSession;
	
	
	public UserRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public int insert(UserVo vo) {
		try {
			return sqlSession.insert("user.insert", vo);	
		} catch(RuntimeException e) {
			System.out.println("error:" + e);
		}
		
		return 0;
		
	}
	
	public UserVo findByEmailAndPassword(String email, String password) {
		StopWatch sw = new StopWatch();
		sw.start();
		UserVo userVo = sqlSession.selectOne("user.findByEmailAndPassword", Map.of("email", email, "password", password));
		sw.stop();
		long totalTime = sw.getTotalTimeMillis();
		System.out.println("[Execution Time][UserRepository.findByEmailAndPassword] " + totalTime + "millis");
		return userVo;
	}
	
	public UserVo findByUserNo(Long authId) {
		return sqlSession.selectOne("user.findById", authId);
	}
	
	public int updateByUserNo(Long authUserId, UserVo vo) {
		return sqlSession.update("user.update", vo);
		
	}

}