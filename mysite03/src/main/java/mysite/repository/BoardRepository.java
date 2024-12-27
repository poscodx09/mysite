package mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	
	// 전체 게시글 조회
	public List<BoardVo> findAll() {
		List<BoardVo> result = new ArrayList<>();
		
		try (
			Connection conn = getConnection();				
			PreparedStatement pstmt = conn.prepareStatement("select * from board order by g_no desc, o_no asc");
		) {
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				String regDate = rs.getString(5);
				int groupNo = rs.getInt(6);
				int orderNo = rs.getInt(7);
				int depth = rs.getInt(8);
				int userId = rs.getInt(9);
				
				BoardVo vo = new BoardVo();
				vo.setId(id);
				vo.setTitle(title);
				vo.setContent(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNo);
				vo.setDepth(depth);
				vo.setUserId(userId);
				
				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return result;
	}
	
	// 페이지 내 게시글 조회
	public List<BoardVo> findPageList(int page) {
		List<BoardVo> result = new ArrayList<>();
		System.out.println("repo page:" + page);
		
		try (
			Connection conn = getConnection();				
			PreparedStatement pstmt = conn.prepareStatement("select * from board order by g_no desc, o_no asc limit ? offset ?");
			PreparedStatement pstmt2 = conn.prepareStatement("select name from user where id = ?");
		) {
			pstmt.setInt(1, 10);
			pstmt.setInt(2, (page-1)*10);
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				String regDate = rs.getString(5);
				int groupNo = rs.getInt(6);
				int orderNo = rs.getInt(7);
				int depth = rs.getInt(8);
				int userId = rs.getInt(9);
				
				pstmt2.setInt(1, userId);
				ResultSet rs2 = pstmt2.executeQuery();
				
				BoardVo vo = new BoardVo();
				vo.setId(id);
				vo.setTitle(title);
				vo.setContent(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNo);
				vo.setDepth(depth);
				vo.setUserId(userId);
				if (rs2.next()) {
					String writerName = rs2.getString(1);
					vo.setWriterName(writerName);
				}
				
				
				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return result;
	}
	
	// 게시글 상세 조회
	public BoardVo findOne(int pId) {
		BoardVo vo = new BoardVo();
		System.out.println("pId" + pId);
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from board where id = ?");	
		) {
			pstmt.setInt(1, pId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) { 
                Long id = rs.getLong(1);
                String title = rs.getString(2);
                String content = rs.getString(3);
                int hit = rs.getInt(4);
                String regDate = rs.getString(5);
                int groupNo = rs.getInt(6);
                int orderNo = rs.getInt(7);
                int depth = rs.getInt(8);
                int userId = rs.getInt(9);

                vo.setId(id);
                vo.setTitle(title);
                vo.setContent(content);
                vo.setHit(hit);
                vo.setRegDate(regDate);
                vo.setGroupNo(groupNo);
                vo.setOrderNo(orderNo);
                vo.setDepth(depth);
                vo.setUserId(userId);
            }

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return vo;
	}
	
	// 글 작성
	public int insert(int parentId, BoardVo vo) {
		int newPostId = 0;
		int groupNo = 1;
		int orderNo = 1;
		int depth = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("insert into board values(null, ?, ?, ?, now(), ?, ?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS);
		) {
			// 답글 게시글인 경우
			if (parentId > 0) {
				
				PreparedStatement pstmt2 = conn.prepareStatement("select * from board where id = ?;");
				pstmt2.setInt(1, parentId);
				ResultSet rs = pstmt2.executeQuery();
				
				if (rs.next()) {
					groupNo = rs.getInt(6);
	                orderNo = rs.getInt(7);
				}
                
				PreparedStatement pstmt3 = conn.prepareStatement("update board set o_no = o_no + 1 where g_no = ? and o_no >= ?;");
				pstmt3.setInt(1, groupNo);
				pstmt3.setInt(2, orderNo + 1);
				pstmt3.executeUpdate();
				
				ResultSet rs2 = pstmt2.executeQuery();
				
				if (rs2.next()) {
					groupNo = rs2.getInt(6);
	                orderNo = rs2.getInt(7) + 1;
	                depth = rs2.getInt(8) + 1;
				}
			}
			
			else {
				PreparedStatement pstmt2 = conn.prepareStatement("select max(g_no) from board");
				ResultSet rs = pstmt2.executeQuery();
				if (rs.next()) { 
	                groupNo = rs.getInt(1) + 1;
	            }
			}
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getHit());
			pstmt.setInt(4, groupNo);
			pstmt.setInt(5, orderNo);
			pstmt.setInt(6, depth);
			pstmt.setInt(7, vo.getUserId());
			
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                vo.setId(rs.getInt(1));
                newPostId = rs.getInt(1);
            }
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return newPostId;		
	}
	
	
	public void updateBygNoAndoNo(int gNo, int oNo) {
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("update board set o_no = o_no + 1 where g_no = ? and o_no >= ?;");) {
			pstmt.setInt(1, gNo);
			pstmt.setInt(2, oNo + 1);
			System.out.println("gNo: " + gNo + ", oNo: " + oNo);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}
	}
	
	// 글 수정
	public int update(int parentId, BoardVo vo) {
		int count = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("update board set title=?, content=? where id=?");
		) {

				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContent());
				pstmt.setLong(3, parentId);
				
				System.out.println(vo.getTitle()+vo.getContent()+parentId);
				count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Error:" + e);
		}
		
		return count;			
	}

	// 글 삭제
	public int deleteById(int id) {
		int count = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("delete from board where id=?");
		) {
			pstmt.setLong(1, id);
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return count;
	}
	
	// 조회수
	public int updateHitById(int id) {
		int count = 0;
		
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("update board set hit = hit + 1 where id = ?;");) {

			pstmt.setInt(1, id);
			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}
		
		return count;
	}
	
	private Connection getConnection() throws SQLException{
		Connection conn = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		
			String url = "jdbc:mariadb://192.168.0.5:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} 
		
		return conn;
	}
}
