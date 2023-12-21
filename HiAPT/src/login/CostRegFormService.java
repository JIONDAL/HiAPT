package login;

import javafx.scene.control.ButtonType;

public class CostRegFormService {
	private costDAO costDao;

	public CostRegFormService() {
		costDao = new costDAO();
	}
	
	public void idCheck(String complex, String unit, String year, String month) {
		String result = costDao.idChk(complex, unit);
		if(result.equals("")) {
			CommonService.msg("등록되지 않은 아이디입니다.");
		} else {
			if (costDao.selectForUpdate(year, month, complex, unit) != null) {
				CommonService.msg("이미 " + complex + "-" + unit + "호에 등록된 관리비가 있습니다.");
			} else {
				CommonService.msg(complex + "-" + unit + "호의 관리비를 입력합니다.");
			}
		}
	}

	public void costReg(CostDTO cost) {
		int result = costDao.insert(cost);
		if(CommonService.confirmMsg(cost.getYear() + "년도 " + cost.getMonth() + "월" + cost.getId() + "호\n관리비를 등록하시겠습니까?").get().equals(ButtonType.OK)){
				CommonService.msg("입력하신 " + cost.getId() + "호의 관리비가 등록되었습니다.");
		} else {
			CommonService.msg("관리비 등록을 취소합니다.");
		}
		
	}
}