package login;


public class accountFormService {
	costDAO costDao = new costDAO();
	
	//사용자가 콤보박스에서 은행 선택시 계좌번호 리턴
	public String selectAccount(String id, String bank) {
		// TODO Auto-generated method stub
		accountingDTO accountings = costDao.selectAccount(id);
		if(bank.equals("국민"))
			return accountings.getKb();
		if(bank.equals("기업"))
			return accountings.getIbk();
		if(bank.equals("우리"))
			return accountings.getWr();
		if(bank.equals("하나"))
			return accountings.getKeb();
		if(bank.equals("농협"))
			return accountings.getNh();
		if(bank.equals("신한"))
			return accountings.getSh();
		
		return null;
	}
	
	//사용자가 계좌번호 입금 후 확인요청 버튼 클릭시
	public void request(String year, String month, String status) {
		costDao.updatePay(Login.getId(), year, month, status);
	}
}
