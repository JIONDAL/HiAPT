package login;

import java.util.Collection;

import javafx.scene.control.ButtonType;

public class costUnpaidFormService {
	private costDAO costDao = new costDAO();
	
	public Collection<CostDTO> select(String pay, String year, String month) {
		if(year.equals("연도 선택")) {
			if(month.equals("월 선택") == false) {
				CommonService.msg("연도를 선택하세요.");
			}
		}
		
		if(pay.equals("전체")) {
			if(year.equals("연도 선택") == false) {
				year = year.substring(0, year.length()-1);
				if(month.equals("월 선택") == false) {
					month = month.substring(0, month.length()-1);
					return costDao.selectAllMonth(year, month);
				}
				return costDao.selectYear(year);
			}
			return costDao.selectAll();
		} else {
			if(year.equals("연도 선택") == false) {
				year = year.substring(0, year.length()-1);
				if(month.equals("월 선택") == false) {
					month = month.substring(0, month.length()-1);
					return costDao.selectUnpaidMonth(pay, year, month);
				} else {
					return costDao.selectUnpaidYear(pay, year);
				}
			} 
			return costDao.selectUnpaid(pay);
		}
	}
	
	public void check(String id, String year, String month) {
		if(CommonService.confirmMsg(year + "년 " + month + "월 " + id + "세대의 관리비를 납부처리 하시겠습니까?").get().equals(ButtonType.OK)){
			String pay = "납부";
			costDao.updatePay(id, year, month, pay);
			CommonService.msg(id + "세대의 관리비를 납부처리하였습니다.");
		} else {
			CommonService.msg("납부처리를 취소하였습니다.");
		}
	}

}
