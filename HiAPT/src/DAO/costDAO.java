package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import DTO.AptDTO;
import DTO.CostDTO;
import DTO.accountingDTO;

public class costDAO {
	public Connection con;
	private String totalCost;
	private String month;

	public costDAO() {
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

	public String idChk(String complex, String unit) {
		String dbId = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT * FROM apt where id = ?");
			String id = complex + "-" + unit;
			ps.setString(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dbId = rs.getString("id");
			} else {
				dbId = "";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dbId;
	}

	public int insert(CostDTO cost) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "INSERT INTO cost VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, cost.getId());
			ps.setString(2, cost.getCommonCost());
			ps.setString(3, cost.getCleanCost());
			ps.setString(4, cost.getSecCost());
			ps.setString(5, cost.getElvCost());
			ps.setString(6, cost.getHeatCost());
			ps.setString(7, cost.getPowerCost());
			ps.setString(8, cost.getWaterCost());
			ps.setString(9, cost.getOperCost());
			ps.setString(10, cost.getTotalCost());
			ps.setString(11, cost.getYear());
			ps.setString(12, cost.getMonth());
			ps.setString(13, "미납");
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public Collection<CostDTO> selectAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CostDTO> list = new ArrayList<>();
		try {
			ps = con.prepareStatement(
					"SELECT year, month, id, total_cost, pay FROM cost ORDER BY year DESC, month DESC, id ASC");
			rs = ps.executeQuery();
			while (rs.next()) {
				CostDTO costDto = new CostDTO();
				costDto.setYear(rs.getString(1));
				costDto.setMonth(rs.getString(2));
				costDto.setId(rs.getString(3));
				costDto.setTotalCost(rs.getString(4));
				costDto.setPay(rs.getString(5));
				list.add(costDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Collection<CostDTO> selectYear(String year) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CostDTO> list = new ArrayList<>();
		try {
			ps = con.prepareStatement(
					"SELECT year, month, id, total_cost, pay FROM cost where year = ? ORDER BY month DESC, id ASC");
			ps.setString(1, year);
			rs = ps.executeQuery();
			while (rs.next()) {
				CostDTO costDto = new CostDTO();
				costDto.setYear(rs.getString(1));
				costDto.setMonth(rs.getString(2));
				costDto.setId(rs.getString(3));
				costDto.setTotalCost(rs.getString(4));
				costDto.setPay(rs.getString(5));
				list.add(costDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Collection<CostDTO> selectAllMonth(String year, String month) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CostDTO> list = new ArrayList<>();
		try {
			ps = con.prepareStatement(
					"SELECT year, month, id, total_cost, pay FROM cost where year = ? and month = ? ORDER BY id ASC");
			ps.setString(1, year);
			ps.setString(2, month);
			rs = ps.executeQuery();
			while (rs.next()) {
				CostDTO costDto = new CostDTO();
				costDto.setYear(rs.getString(1));
				costDto.setMonth(rs.getString(2));
				costDto.setId(rs.getString(3));
				costDto.setTotalCost(rs.getString(4));
				costDto.setPay(rs.getString(5));
				list.add(costDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Collection<CostDTO> selectComplex(String year, String month, String complex) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CostDTO> list = new ArrayList<>();
		try {
			ps = con.prepareStatement(
					"SELECT year, month, id, total_cost, pay FROM cost where year = ? and month = ? and id like ? ORDER BY id ASC");
			ps.setString(1, year);
			ps.setString(2, month);
			ps.setString(3, complex + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				CostDTO costDto = new CostDTO();
				costDto.setYear(rs.getString(1));
				costDto.setMonth(rs.getString(2));
				costDto.setId(rs.getString(3));
				costDto.setTotalCost(rs.getString(4));
				costDto.setPay(rs.getString(5));
				list.add(costDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Collection<CostDTO> selectComplexOnly(String complex) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CostDTO> list = new ArrayList<>();
		try {
			ps = con.prepareStatement(
					"SELECT year, month, id, total_cost, pay FROM cost where id like ? ORDER BY year DESC, month DESC, id ASC");
			ps.setString(1, complex + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				CostDTO costDto = new CostDTO();
				costDto.setYear(rs.getString(1));
				costDto.setMonth(rs.getString(2));
				costDto.setId(rs.getString(3));
				costDto.setTotalCost(rs.getString(4));
				costDto.setPay(rs.getString(5));
				list.add(costDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Collection<CostDTO> selectYearComplex(String year, String complex) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CostDTO> list = new ArrayList<>();
		try {
			ps = con.prepareStatement(
					"SELECT year, month, id, total_cost, pay FROM cost where year = ? and id like ? ORDER BY year DESC, month DESC, id ASC");
			ps.setString(1, year);
			ps.setString(2, complex + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				CostDTO costDto = new CostDTO();
				costDto.setYear(rs.getString(1));
				costDto.setMonth(rs.getString(2));
				costDto.setId(rs.getString(3));
				costDto.setTotalCost(rs.getString(4));
				costDto.setPay(rs.getString(5));
				list.add(costDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Collection<CostDTO> selectUnit(String complex, String unit) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CostDTO> list = new ArrayList<>();
		try {
			ps = con.prepareStatement(
					"SELECT year, month, id, total_cost, pay FROM cost where id = ? ORDER BY year DESC, month DESC");
			String id = (complex + "-" + unit);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				CostDTO costDto = new CostDTO();
				costDto.setYear(rs.getString(1));
				costDto.setMonth(rs.getString(2));
				costDto.setId(rs.getString(3));
				costDto.setTotalCost(rs.getString(4));
				costDto.setPay(rs.getString(5));
				list.add(costDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Collection<CostDTO> selectYearUnit(String year, String complex, String unit) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CostDTO> list = new ArrayList<>();
		try {
			ps = con.prepareStatement(
					"SELECT year, month, id, total_cost, pay FROM cost where year = ? and id = ? ORDER BY year DESC, month DESC");
			String id = (complex + "-" + unit);
			ps.setString(1, year);
			ps.setString(2, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				CostDTO costDto = new CostDTO();
				costDto.setYear(rs.getString(1));
				costDto.setMonth(rs.getString(2));
				costDto.setId(rs.getString(3));
				costDto.setTotalCost(rs.getString(4));
				costDto.setPay(rs.getString(5));
				list.add(costDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Collection<CostDTO> select(String year, String month, String complex, String unit) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CostDTO> list = new ArrayList<>();
		try {
			ps = con.prepareStatement(
					"SELECT year, month, id, total_cost, pay FROM cost where id = ? and year = ? and month = ?");
			String id = (complex + "-" + unit);
			ps.setString(1, id);
			ps.setString(2, year);
			ps.setString(3, month);
			rs = ps.executeQuery();
			while (rs.next()) {
				CostDTO costDto = new CostDTO();
				costDto.setYear(rs.getString(1));
				costDto.setMonth(rs.getString(2));
				costDto.setId(rs.getString(3));
				costDto.setTotalCost(rs.getString(4));
				costDto.setPay(rs.getString(5));
				list.add(costDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public CostDTO selectForUpdate(String year, String month, String complex, String unit) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("SELECT * FROM cost where id = ? and year = ? and month = ?");
			String id = (complex + "-" + unit);
			ps.setString(1, id);
			ps.setString(2, year);
			ps.setString(3, month);
			rs = ps.executeQuery();
			while (rs.next()) {
				CostDTO costDto = new CostDTO();
				costDto.setId(rs.getString(1));
				costDto.setCommonCost(rs.getString(2));
				costDto.setCleanCost(rs.getString(3));
				costDto.setSecCost(rs.getString(4));
				costDto.setElvCost(rs.getString(5));
				costDto.setHeatCost(rs.getString(6));
				costDto.setPowerCost(rs.getString(7));
				costDto.setWaterCost(rs.getString(8));
				costDto.setOperCost(rs.getString(9));
				costDto.setTotalCost(rs.getString(10));
				costDto.setYear(rs.getString(11));
				costDto.setMonth(rs.getString(12));
				costDto.setPay(rs.getString(13));
				return costDto;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Collection<CostDTO> selectUnpaid(String pay) { // 미납 화면 조회용 - 납부구분에 따라서
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CostDTO> list = new ArrayList<>();
		try {
			ps = con.prepareStatement(
					"SELECT year, month, id, total_cost, pay FROM cost WHERE pay = ? ORDER BY year DESC, month DESC, id ASC");
			ps.setString(1, pay);
			rs = ps.executeQuery();
			while (rs.next()) {
				CostDTO costDto = new CostDTO();
				costDto.setYear(rs.getString(1));
				costDto.setMonth(rs.getString(2));
				costDto.setId(rs.getString(3));
				costDto.setTotalCost(rs.getString(4));
				costDto.setPay(rs.getString(5));
				list.add(costDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Collection<CostDTO> selectUnpaidYear(String pay, String year) { // 미납 화면 조회용 - 연도 선택
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CostDTO> list = new ArrayList<>();
		try {
			ps = con.prepareStatement(
					"SELECT year, month, id, total_cost, pay FROM cost WHERE pay = ? and year = ? ORDER BY year DESC, month DESC, id ASC");
			ps.setString(1, pay);
			ps.setString(2, year);
			rs = ps.executeQuery();
			while (rs.next()) {
				CostDTO costDto = new CostDTO();
				costDto.setYear(rs.getString(1));
				costDto.setMonth(rs.getString(2));
				costDto.setId(rs.getString(3));
				costDto.setTotalCost(rs.getString(4));
				costDto.setPay(rs.getString(5));
				list.add(costDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Collection<CostDTO> selectUnpaidMonth(String pay, String year, String month) { // 미납 화면 조회용 - 월 선택
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CostDTO> list = new ArrayList<>();
		try {
			ps = con.prepareStatement(
					"SELECT year, month, id, total_cost, pay FROM cost WHERE pay = ? and year = ? and month =? ORDER BY year DESC, month DESC, id ASC");
			ps.setString(1, pay);
			ps.setString(2, year);
			ps.setString(3, month);
			rs = ps.executeQuery();
			while (rs.next()) {
				CostDTO costDto = new CostDTO();
				costDto.setYear(rs.getString(1));
				costDto.setMonth(rs.getString(2));
				costDto.setId(rs.getString(3));
				costDto.setTotalCost(rs.getString(4));
				costDto.setPay(rs.getString(5));
				list.add(costDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int update(CostDTO cost) { // 관리비 수정
		PreparedStatement ps = null;
		String sql = "UPDATE cost SET common_cost = ?, clean_cost = ?, sec_cost = ?, elv_cost = ?, heat_cost = ?, power_cost = ?, water_cost = ?, oper_cost = ?, total_cost = ? WHERE id = ? and year = ? and month = ?";
		int result = 0;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, cost.getCommonCost());
			ps.setString(2, cost.getCleanCost());
			ps.setString(3, cost.getSecCost());
			ps.setString(4, cost.getElvCost());
			ps.setString(5, cost.getHeatCost());
			ps.setString(6, cost.getPowerCost());
			ps.setString(7, cost.getWaterCost());
			ps.setString(8, cost.getOperCost());
			ps.setString(9, cost.getTotalCost());
			ps.setString(10, cost.getId());
			ps.setString(11, cost.getYear());
			ps.setString(12, cost.getMonth());

			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int delete(String id, String year, String month) { // 관리비 삭제
		PreparedStatement ps = null;
		String sql = "DELETE FROM cost WHERE id = ? and year = ? and month = ?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, year);
			ps.setString(3, month);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// 계좌번호 생성 - > 관리자가 입주민 승인 시
	public void insertAccount(String id) { 
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("INSERT INTO account VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, id);

			String kb = "564390-26-" + Integer.toString((int) (Math.random() * 10) + 100)
					+ Integer.toString((int) (Math.random() * 10) + 100);
			String wr = "2775-907-" + Integer.toString((int) (Math.random() * 10) + 10) + "18"
					+ Integer.toString((int) (Math.random() * 10) + 100);
			String nh = "792-" + Integer.toString((int) (Math.random() * 10) + 1000) + "-"
					+ Integer.toString((int) (Math.random() * 10) + 1000) + "-"
					+ Integer.toString((int) (Math.random() * 10) + 100);
			String ibk = Integer.toString((int) (Math.random() * 10) + 100) + "-"
					+ Integer.toString((int) (Math.random() * 10) + 100)
					+ Integer.toString((int) (Math.random() * 10) + 100) + "-97-"
					+ Integer.toString((int) (Math.random() * 10) + 100);
			String keb = Integer.toString((int) (Math.random() * 10) + 100) + "-"
					+ Integer.toString((int) (Math.random() * 10) + 100)
					+ Integer.toString((int) (Math.random() * 10) + 100) + "-"
					+ Integer.toString((int) (Math.random() * 10) + 100) + "37";
			String sh = "560-" + Integer.toString((int) (Math.random() * 10) + 100) + "-"
					+ Integer.toString((int) (Math.random() * 10) + 100)
					+ Integer.toString((int) (Math.random() * 10) + 100)
					+ Integer.toString((int) (Math.random() * 10) + 10);

			ps.setString(2, kb);
			ps.setString(3, wr);
			ps.setString(4, nh);
			ps.setString(5, ibk);
			ps.setString(6, keb);
			ps.setString(7, sh);
			ps.setString(8, "0");

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//유저---------------------------------------------------------------------------------------------------------------
	// 월별 관리비 조회
	public Collection<CostDTO> selectMonthly(String year) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CostDTO> list = new ArrayList<>();
		try {
			ps = con.prepareStatement("SELECT total_cost, month FROM cost WHERE id=? and year=? ORDER BY month ASC");
			ps.setString(1, AptDTO.getId());
			ps.setString(2, year);
			rs = ps.executeQuery();
			while (rs.next()) {
				CostDTO costDto = new CostDTO(totalCost, month);
				costDto.setTotalCost(rs.getString(1));
				costDto.setMonth(rs.getString(2));
				list.add(costDto);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}
	
	//이번달 관리비 조회
	public CostDTO selectMonth() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		CostDTO costDto = null;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("MM");
		try {
			ps = con.prepareStatement("SELECT * FROM cost WHERE id=? and month=?");
			ps.setString(1, AptDTO.getId());
			ps.setString(2, formatter.format(calendar.getTime()));// 현재 날짜(월) 가져오기

			rs = ps.executeQuery();

			if (rs.next()) {
				costDto = new CostDTO();
				costDto.setCommonCost(rs.getString(2));
				costDto.setCleanCost(rs.getString(3));
				costDto.setSecCost(rs.getString(4));
				costDto.setElvCost(rs.getString(5));
				costDto.setHeatCost(rs.getString(6));
				costDto.setPowerCost(rs.getString(7));
				costDto.setWaterCost(rs.getString(8));
				costDto.setOperCost(rs.getString(9));
				costDto.setTotalCost(rs.getNString(10));
				costDto.setYear(rs.getNString(11));
				costDto.setMonth(rs.getNString(12));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return costDto;
	}
	
	//목록별 관리비 정보를 담은 DTO 리턴
	public CostDTO selectCosts(String year, String month, String complex, String unit) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("SELECT * FROM cost where id = ? and year = ? and month = ?");
			String id = (complex + "-" + unit);
			ps.setString(1, id);
			ps.setString(2, year);
			ps.setString(3, month);
			rs = ps.executeQuery();
			while (rs.next()) {
				CostDTO costDto = new CostDTO();
				costDto.setId(rs.getString(1));
				costDto.setCommonCost(rs.getString(2));
				costDto.setCleanCost(rs.getString(3));
				costDto.setSecCost(rs.getString(4));
				costDto.setElvCost(rs.getString(5));
				costDto.setHeatCost(rs.getString(6));
				costDto.setPowerCost(rs.getString(7));
				costDto.setWaterCost(rs.getString(8));
				costDto.setOperCost(rs.getString(9));
				costDto.setTotalCost(rs.getString(10));
				costDto.setYear(rs.getString(11));
				costDto.setMonth(rs.getString(12));
				costDto.setPay(rs.getString(13));
				return costDto;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	//무통장 입금 계좌 선택
	public accountingDTO selectAccount(String id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		accountingDTO accounts = new accountingDTO();

		try {
			ps = con.prepareStatement("SELECT * FROM account WHERE id =?");
			ps.setString(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				accounts.setKb(rs.getString("kb"));
				accounts.setWr(rs.getString("wr"));
				accounts.setNh(rs.getString("nh"));
				accounts.setIbk(rs.getString("ibk"));
				accounts.setKeb(rs.getString("keb"));
				accounts.setSh(rs.getString("sh"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accounts;
	}

	// 사용자가 입금확인요청 눌렀을 때(미납->확인요청), 관리자가 입금확인 했을 때(확인요청 -> 납부)
	public void updatePay(String id, String year, String month, String status) { 
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("UPDATE cost SET pay = ? WHERE id = ? and year = ? and month = ?");
			ps.setString(1, status);
			ps.setString(2, id);
			ps.setString(3, year);
			ps.setString(4, month);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//미납금 조회
	public String unpaid(String id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT sum(total_cost) FROM cost WHERE id = ? AND pay = ?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, "미납");
			rs = ps.executeQuery();
			
			if(rs.next()) {
				return rs.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "0";
	}
	
	//myPay 잔액 조회
	public String selectMoney(String id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT money FROM account where id = ?");
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "0";
	}
	
	//myPay 충전
	public void charge(String id, String money) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("INSERT INTO account (money) VALUES ? WHERE id =?");
			ps.setString(1, money);
			ps.setString(2, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//myPay 충전시 or 관리비 납부시 잔액 변동 있을 때
	public void setMymoney(String id, String balance) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("UPDATE account SET money = ? WHERE id = ?");
			ps.setString(1, balance);
			ps.setString(2, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//myPay 결제시 '미납' -> '납부' 상태 변경
	public void statusUpdate(String id, String year, String month) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("UPDATE cost SET pay = ? WHERE id = ? "
									+ "AND year = ? AND month = ? AND pay = ? ");
			ps.setString(1, "납부");
			ps.setString(2, id);
			ps.setString(3, year);
			ps.setString(4, month);
			ps.setString(5, "미납");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
