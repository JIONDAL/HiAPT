package login;

import javafx.scene.control.ButtonType;

public class costUpdateFormService {
	
	private costDAO costDao = new costDAO();
	
	public CostDTO select(String year, String month, String complex, String unit) {
		return costDao.selectForUpdate(year, month, complex, unit);
	}
	
	public void update(CostDTO cost) {
		if(CommonService.confirmMsg(cost.getYear() + "년도 " + cost.getMonth() + "월 " + cost.getId() + "호의 관리비를 수정하시겠습니까?").get().equals(ButtonType.OK)) {
			if (costDao.update(cost) == 1) {
				CommonService.msg(cost.getYear() + "년도 " + cost.getMonth() + "월 " + cost.getId() + "호의 관리비가 수정되었습니다.");
			} else {
				CommonService.msg("수정 중 오류가 발생하였습니다.");
			}
		}
		
	}

}
