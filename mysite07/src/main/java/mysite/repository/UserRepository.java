package mysite.repository;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import com.fasterxml.jackson.databind.ObjectMapper;

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
		return sqlSession.selectOne("user.findByEmailAndPassword", Map.of("email", email, "password", password));
	}
	
	public UserVo findByUserNo(Long authId) {
		return sqlSession.selectOne("user.findById", authId);
	}
	
	public <R> R findByEmail(String email, Class<R> resultType) {
		Map<String, Object> map = sqlSession.selectOne("user.findByEmail", email);
		return new ObjectMapper().convertValue(map, resultType);
		
	}
	
	public int updateByUserNo(Long authUserId, UserVo vo) {
		return sqlSession.update("user.update", vo);
		
	}



}