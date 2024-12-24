package mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mysite.vo.BoardVo;

public class BoardDao {
	
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


	// 
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
				PreparedStatement pstmt2 = conn.prepareStatement("select * from board where id = ?");
				pstmt2.setInt(1, parentId);
				ResultSet rs = pstmt2.executeQuery();
				if (rs.next()) { 
	                groupNo = rs.getInt(6);
	                orderNo = rs.getInt(7) + 1;
	                depth = rs.getInt(8) + 1;
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
