package login;

import java.util.Collection;
import javafx.scene.control.ButtonType;

public class costSelectFormService {
	private costDAO costDao;

	public costSelectFormService() {
		costDao = new costDAO();
	}

	// 동만 있을 때, 연도 + 동있을 때, 연도 + 월 + 동있을 때 /
	// 호수만 넣고 보는건 의미 없으니까 동+호수가 기본 / 연도 + 동 + 호수 / 연도 + 월 + 동 + 호수
	public Collection<CostDTO> select(String year, String month, String complex, String unit) {
		if (year.equals("연도 선택") == false) {
			year = year.substring(0, year.length() - 1);
			if (month.equals("월 선택") == false) {
				month = month.substring(0, month.length() - 1);
				if (complex.equals("동 선택") == false) {
					complex = complex.substring(0, complex.length() - 1);
					if (unit.equals("호수 선택") == false) {
						unit = unit.substring(0, unit.length() - 1);
						return costDao.select(year, month, complex, unit);
					} else {
						return costDao.selectComplex(year, month, complex);
					}
				} else {
					return costDao.selectAllMonth(year, month);
				}
			} else { // 연도 있고 월 없을 때
				if (complex.equals("동 선택") == false) {// 연도 있고 월 없고 동은 있을 때
					complex = complex.substring(0, complex.length() - 1);
					if (unit.equals("호수 선택") == false) {
						unit = unit.substring(0, unit.length() - 1);
						return costDao.selectYearUnit(year, complex, unit);
					} else {
						return costDao.selectYearComplex(year, complex);
					}
				} else {
					return costDao.selectYear(year);
				}
			}
		} else { // 연도가 없을 때
			if (month.equals("월 선택") == false) {
				CommonService.msg("조회하려는 연도를 선택하세요.");
			} else {
				if (complex.equals("동 선택") == false) { // 연도 없이 월 없이 동만 선택했을 때
					complex = complex.substring(0, complex.length() - 1);
					if (unit.equals("호수 선택") == false) {
						unit = unit.substring(0, unit.length() - 1);
						return costDao.selectUnit(complex, unit);
					} else {
						return costDao.selectComplexOnly(complex);
					}
				}
			}
		}
		return costDao.selectAll();
	}

	public void delete(String id, String year, String month) {
		if (CommonService.confirmMsg(id + "호의 " + year + "년도 " + month + "월의\n관리비를 삭제하시겠습니까?").get()
				.equals(ButtonType.OK)) {
			if (costDao.delete(id, year, month) == 0) {
				CommonService.msg("삭제 중 오류가 발생하였습니다.");
			} else {
				CommonService.msg("삭제되었습니다.");
			}
		}
	}
}
