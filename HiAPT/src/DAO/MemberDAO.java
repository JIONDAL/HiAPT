package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import DTO.MemberDTO;
import javafx.beans.property.SimpleStringProperty;

public class MemberDAO {
	public Connection con;
	
	private String id;
	private String pw;
	private String name;
	private String phone;
	private String carNum;
	private String status;
	
	public MemberDAO() {
		String url = "jdbc:oracle:thin:@192.168.219.126:1521:xe";
		String user = "oracle";
		String password = "oracle";

		try {
			Class.forName("oracle.jdbc.OracleDriver");
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String login(String id) {
		String sql = "SELECT pw FROM apt WHERE id=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("pw");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void insert(String id, String pw, String name, String phone, String carNum) {
		String sql = "INSERT INTO apt VALUES(?,?,?,?,?,?)";
		PreparedStatement ps = null;
		String status = "D";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pw);
			ps.setString(3, name);
			ps.setString(4, phone);
			ps.setString(5, carNum);
			ps.setString(6, status);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public MemberDTO selectId(String id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT * FROM apt WHERE id=?");
			ps.setString(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				MemberDTO member = new MemberDTO(id, pw, name, phone, carNum, status);
				member.setId(rs.getString("id"));
				member.setPw(rs.getString("pw"));
				member.setName(rs.getString("name"));
				member.setPhone(rs.getString("phone"));
				member.setCarNum(rs.getString("car_num"));
				member.setStatus(rs.getString("status"));
				return member;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Collection<MemberDTO> selectAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<MemberDTO> members = new ArrayList<>();
		try {
			ps = con.prepareStatement("SELECT * FROM apt WHERE id != 'admin'");
			rs = ps.executeQuery();
			while (rs.next()) {
				MemberDTO member = new MemberDTO(id, pw, name, phone, carNum, status);
				member.setId(rs.getString("id"));
				member.setPw(rs.getString("pw"));
				member.setName(rs.getString("name"));
				member.setPhone(rs.getString("phone"));
				member.setCarNum(rs.getString("car_num"));
				member.setStatus(rs.getString("status"));
				members.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return members;
	}
	
	public Collection<MemberDTO> selectMember(String id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<MemberDTO> members = new ArrayList<>();
		try {
			ps = con.prepareStatement("SELECT * FROM apt WHERE id LIKE ?");
			ps.setString(1, "%" + id + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				MemberDTO member = new MemberDTO(id, pw, name, phone, carNum, status);
				member.setId(rs.getString("id"));
				member.setPw(rs.getString("pw"));
				member.setName(rs.getString("name"));
				member.setPhone(rs.getString("phone"));
				member.setCarNum(rs.getString("car_num"));
				member.setStatus(rs.getString("status"));
				members.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return members;
	}

	public Collection<MemberDTO> selectDisagree() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<MemberDTO> members = new ArrayList<>();
		try {
			ps = con.prepareStatement("SELECT * FROM apt WHERE id != 'admin' and status ='D'");
			rs = ps.executeQuery();
			while (rs.next()) {
				MemberDTO member = new MemberDTO(id, pw, name, phone, carNum, status);
				member.setId(rs.getString("id"));
				member.setPw(rs.getString("pw"));
				member.setName(rs.getString("name"));
				member.setPhone(rs.getString("phone"));
				member.setCarNum(rs.getString("car_num"));
				member.setStatus(rs.getString("status"));
				members.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return members;
	}

	public String selectStatus(String id) {
		String sql = "SELECT status FROM apt WHERE id=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("status");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateAgree(String id) {
		PreparedStatement ps = null;
		MemberDTO member = new MemberDTO(id, pw, name, phone, carNum, status);
		try {
			ps = con.prepareStatement("UPDATE apt SET status='A' WHERE id=?");
			ps.setString(1, member.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(String id) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("DELETE FROM apt WHERE id=?");
			ps.setString(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//회원 정보 수정
	public int update(String id, String name, String phone, String pw) {
		PreparedStatement ps = null;
		int result = 0;
		try {
			ps = con.prepareStatement("UPDATE apt SET name=?, phone=?, pw=? WHERE id=?");
			ps.setString(1, name);
			ps.setString(2, phone);
			ps.setString(3, pw);
			ps.setString(4, id);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

}
