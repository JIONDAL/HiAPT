package login;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegService {
	private MemberDAO memberDao;

	public RegService() {
		memberDao = new MemberDAO();
	}

	public void insert(Parent regForm) {
//		Button regButton = (Button) regForm.lookup("#regBtn");
		PasswordField pwFld = (PasswordField) regForm.lookup("#pwFld");
		PasswordField confirmFld = (PasswordField) regForm.lookup("#confirmFld");
		if (pwFld.getText().equals(confirmFld.getText())) {
			TextField idFld = (TextField) regForm.lookup("#idFld");
			String idPattern = "^[0-9]{3}-[0-9]{3}$";
			Pattern pattern = Pattern.compile(idPattern);
			Matcher matcher = pattern.matcher(idFld.getText());
			if(matcher.matches()) {
				TextField nameFld = (TextField) regForm.lookup("#nameFld");
				TextField phoneFld = (TextField) regForm.lookup("#phoneFld");
				String phonePattern ="^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
				pattern = Pattern.compile(phonePattern);
				matcher = pattern.matcher(phoneFld.getText());
				if(matcher.matches()) {
					TextField carFld = (TextField) regForm.lookup("#carFld");
					if(carFld.getText().length() == 4) {
						//관리자 승인을 기다리세요! 알림창 뜨도록 변경하기
						MemberDTO Dto = new MemberDTO(idFld.getText(), pwFld.getText(), nameFld.getText(), phoneFld.getText(), carFld.getText(), "D");
						MemberDTO check = memberDao.selectId(Dto.getId());
						if(check == null) {
							memberDao.insert(idFld.getText(), pwFld.getText(), nameFld.getText(), phoneFld.getText(), carFld.getText());
							CommonService.msg("회원가입이 완료되었습니다. 관리자의 승인을 기다리세요.");
							CommonService.windowClose(regForm);
						
						}else if (check.getStatus().equals("A")) {
							CommonService.msg("이미 가입된 회원입니다.");
							CommonService.windowClose(regForm);
						}else {
							CommonService.msg("관리자 승인 대기중입니다.");
							CommonService.windowClose(regForm);
						}
						
					}else {
						CommonService.msg("차량번호는 뒤 4자리 숫자입니다.");
					}
				}else {
					CommonService.msg("핸드폰 번호는 하이픈(-)을 사용하여 입력해주세요.(010/011/016/017/018/019 가능)");
				}

			}else {
				CommonService.msg("아이디는 형식은 동-호수 입니다.");
			}
		} else {
			CommonService.msg("비밀번호를 확인 후 다시 입력하세요.");
		}
	}
}