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
	
	public List<BoardVo> findPageList(int page) {
		List<BoardVo> result = new ArrayList<>();
		
		try (
			Connection conn = getConnection();				
			PreparedStatement pstmt = conn.prepareStatement("select * from board order by g_no desc, o_no asc limit ? offset ?");
		) {
			pstmt.setInt(1, page*10);
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


	public int insert(BoardVo vo) {
		int newId = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("insert into board values(null, ?, ?, ?, now(), ?, ?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS);
		) {
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getHit());
			pstmt.setInt(4, vo.getGroupNo());
			pstmt.setInt(5, vo.getOrderNo());
			pstmt.setInt(6, vo.getDepth());
			pstmt.setInt(7, vo.getUserId());
			
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                vo.setId(rs.getInt(1));
                newId = rs.getInt(1);
            }
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return newId;		
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
