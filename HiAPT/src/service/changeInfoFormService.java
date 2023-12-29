package service;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import DAO.MemberDAO;
import DTO.AptDTO;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class changeInfoFormService {
private MemberDAO memberDao;
	
	public changeInfoFormService() {
		memberDao = new MemberDAO();
	}
	
	//수정할 정보 검증
	public int change(Parent changeInfoForm) {
		TextField nameFld = (TextField) changeInfoForm.lookup("#nameFld");
		TextField phoneFld = (TextField) changeInfoForm.lookup("#phoneFld");
		PasswordField pwFld = (PasswordField) changeInfoForm.lookup("#pwFld");
		PasswordField confirmFld = (PasswordField) changeInfoForm.lookup("#confirmFld");
		int result = 0;
		
		if(nameFld.getText().length() <= 1) {
			CommonService.msg("이름은 두 자 이상 입력하세요.");
			return result;
		}
		
		String phonePattern = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
		Pattern pattern = Pattern.compile(phonePattern);
		Matcher matcher = pattern.matcher(phoneFld.getText());
		
		if(matcher.matches() == false) {
			CommonService.msg("핸드폰 번호를 확인하세요.(010/011/016/017/018/019)-000(0)-0000");
			return result;
		}
		
		if(pwFld.getText().length() > 10) {
			CommonService.msg("비밀번호를 10자리 이하로 입력하세요.");
			return result;
		}else {
			if(pwFld.getText().equals(confirmFld.getText()) == false) {
				CommonService.msg("두 비밀번호가 일치하지 않습니다.");
				return result;
			}
		}
		
		result = memberDao.update(AptDTO.getId(), nameFld.getText(), phoneFld.getText(), pwFld.getText());
		return result;
	}
}
