package mysite.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	
	private SqlSession sqlSession;
	
	public BoardRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	// 전체 게시글 조회
	public List<BoardVo> findAll() {
		return sqlSession.selectList("board.findAll");
	}
	
	// 페이지 내 게시글 조회
	public List<BoardVo> findPageList(int page) {
		int limit = 10;
		int offset = (page-1)*10;
		return sqlSession.selectList("board.findPageList", Map.of("limit", limit, "offset", offset));
	}
	
	// 게시글 상세 조회
	public BoardVo findOne(int pId) {
		return sqlSession.selectOne("board.findOne", Map.of("id", pId));
	}
	
	// 글 작성
	public int insert(int parentId, BoardVo vo) {
		int newPostId = 0;
		int groupNo = 1;
		int orderNo = 1;
		int depth = 0;
		
		// 답글 게시글인 경우
		if (parentId > 0) {
			
			BoardVo boardVo = sqlSession.selectOne("board.findOne", Map.of("id", parentId));

			groupNo = boardVo.getGroupNo();
            orderNo = boardVo.getOrderNo();
            
            sqlSession.update("board.updateBygNoAndoNo", Map.of("g_no", groupNo, "o_no", orderNo+1));
            
            BoardVo updatedBoardVo = sqlSession.selectOne("board.findOne", Map.of("id", parentId));
			
			groupNo = updatedBoardVo.getGroupNo();
            orderNo = updatedBoardVo.getOrderNo() + 1;
            depth = updatedBoardVo.getDepth() + 1;
		}
		
		else {
			int maxGroupNo = sqlSession.selectOne("board.selectMaxGroupNo");
			groupNo = maxGroupNo + 1;
		}
			
		vo.setGroupNo(groupNo);
		vo.setOrderNo(orderNo);
		vo.setDepth(depth);
			
			
		sqlSession.insert("board.insert", vo);
		newPostId = (int) vo.getId();
			
		return newPostId;		
	}
	
	public void updateBygNoAndoNo(int gNo, int oNo) {
		sqlSession.update("board.updateBygNoAndoNo", Map.of("g_no", gNo, "o_no", oNo));
	}
	
	// 글 수정
	public int update(int parentId, BoardVo vo) {
		vo.setId(parentId);
		return sqlSession.update("board.update", vo);		
	}

	// 글 삭제
	public int deleteById(int id) {
		return sqlSession.delete("board.deleteById", Map.of("id", id));
	}
	
	// 조회수
	public int updateHitById(int id) {
		return sqlSession.update("board.updateHitById", Map.of("id", id));
	}
}
