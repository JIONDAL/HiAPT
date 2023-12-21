package login;


public class costPayFormService {
	private costDAO costDao = new costDAO();

	//목록별 관리비 정보를 담은 DTO 리턴
	public CostDTO selectCosts(String year, String month, String id) {
        String[] ids = id.split("-");
		String complex = ids[0];
		String unit = ids[1];
		return costDao.selectForUpdate(year, month, complex, unit);
	}
	
	//미납금 조회
	public String unpaid() {
		if (costDao.unpaid(Login.getId()) != null) {
			return costDao.unpaid(Login.getId());
		} else {
			return "0";
		}
	}
	
	//myPay로 관리비 결제 후 잔액을 데이터 베이스에 저장
	public void setMymoney(String balance) { 
		costDao.setMymoney(Login.getId(), balance);
	}
	
	//납부 상태를 '미납 -> 납부'로 변경
	public void statusUpdate(String id, String year, String month) {
		costDao.statusUpdate(Login.getId(), year, month);
	}
}
