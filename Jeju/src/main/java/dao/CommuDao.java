package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import dao.Commu.CommuImg;

public class CommuDao {
	private static CommuDao instance;
	private CommuDao() {};
	public static CommuDao getInstance() {
		if (instance == null) {
			instance = new CommuDao();
		}
		return instance;
	}
	
	private Connection getConnection() {
		Connection conn = null;
		try {
			Context    ctx = new InitialContext();
			DataSource ds  = (DataSource) ctx.lookup("java:comp/env/jdbc/OracleDB");
			conn = ds.getConnection();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}
	// 게시글 count
	public int getTotalCnt() throws SQLException {
		int totCnt = 0;
		String sql = "select count(*) from community";
		Connection conn = null;
		Statement  stmt = null;
		ResultSet    rs = null;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs   = stmt.executeQuery(sql);
			if (rs.next()) {
				totCnt = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("getTotalCnt e" + e.getMessage());
		} finally {
			if (rs   != null) rs.close(); 
			if (stmt != null) stmt.close(); 
			if (conn != null) conn.close(); 
		}
		return totCnt;
	}
	// 게시글 list get
	public List<Commu> CommuList(int startRow , int endRow) throws SQLException {
		List<Commu> list = new ArrayList<Commu>();
		String sql = "select * from ( select rownum rn , a.* from (select * from community order by c_num desc) a) where rn between ? and ?";
		Connection        conn  = null;
		PreparedStatement pstmt = null;
		ResultSet         rs    = null;
		
		try {
			//community select(c_img 제외)
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Commu commu = new Commu();
				commu.setC_num(rs.getInt("c_num"));
				commu.setUser_id(rs.getString("user_id"));
				commu.setC_content(rs.getString("c_content"));
				commu.setC_date(rs.getDate("c_date"));
				commu.setC_hash(rs.getString("c_hash"));
				list.add(commu);
			}
		} catch (Exception e) {
			System.out.println("commuList select try" + e.getMessage());
		} finally {
			if (rs    != null) rs.close(); 
			if (pstmt != null) pstmt.close(); 
			if (conn  != null) conn.close(); 
		}
		
		return list;
	}
	
	// commu content select
	public Commu select(int c_num) throws SQLException {
		Commu commu = new Commu();

		String sql = "select * from community where c_num=" + c_num;
		Connection conn  = null;
		Statement  stmt  = null;
		ResultSet  rs    = null;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				commu.setC_num(rs.getInt("c_num"));
				commu.setUser_id(rs.getString("user_id"));
				commu.setC_content(rs.getString("c_content"));
				commu.setC_date(rs.getDate("c_date"));
				commu.setC_hash(rs.getString("c_hash"));
			}
		} catch (Exception e) {
			System.out.println("commu contetn select try..." + e.getMessage());
		} finally {
			if (rs    != null) rs.close(); 
			if (stmt  != null) stmt.close(); 
			if (conn  != null) conn.close(); 
		}
		return commu;
	}
	public List<Commu.CommuImg> selectImg(int c_num) throws SQLException {
		List<Commu.CommuImg> imgList = new ArrayList<Commu.CommuImg>();
		String sql = "select c.c_num , ci.c_img_num , ci.c_img_path "
					+ "from community c , community_img ci "
					+ "where c.c_num = ci.c_num "
					+ "and c.c_num = ? "
					+ "order by ci.c_img_num";
		Connection        conn  = null;
		PreparedStatement pstmt = null;
		ResultSet         rs    = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, c_num);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Commu.CommuImg commuImg = new CommuImg();
				commuImg.setC_num(rs.getInt("c_num"));
				commuImg.setC_img_num(rs.getInt("c_img_num"));
				commuImg.setC_img_path(rs.getString("c_img_path"));
				System.out.println("img_path->" + rs.getString("c_img_path"));
				imgList.add(commuImg);
			}
		} catch (Exception e) {
			System.out.println("selectImg try..." + e.getMessage());
		} finally {
			if (rs    != null) rs.close(); 
			if (pstmt != null) pstmt.close(); 
			if (conn  != null) conn.close(); 
		}
		return imgList;
	}
}
