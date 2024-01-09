package service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import DAO.MemberDAO;
import DTO.MemberDTO;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegService {
	private MemberDAO memberDao;
	
	public RegService() {
		memberDao = new MemberDAO();
	}
	
	public void insert(Parent regForm, Stage regStage) {
		PasswordField pwFld = (PasswordField) regForm.lookup("#pwFld");
		PasswordField confirmFld = (PasswordField) regForm.lookup("#confirmFld");
		TextField idFld = (TextField) regForm.lookup("#idFld");
		TextField nameFld = (TextField) regForm.lookup("#nameFld");
		TextField phoneFld = (TextField) regForm.lookup("#phoneFld");
		TextField carFld = (TextField) regForm.lookup("#carFld");

		if (pwFld.getText().equals(confirmFld.getText()) == false) {
			CommonService.msg("비밀번호를 확인 후 다시 입력하세요.");
			return;
		}
		
		String idPattern = "^[0-9]{3}-[0-9]{3}$";
		Pattern pattern = Pattern.compile(idPattern);
		Matcher matcher = pattern.matcher(idFld.getText());
		if(matcher.matches() == false) {
			CommonService.msg("아이디는 형식은 동-호수 입니다.");
			return;
		}
		
		String phonePattern ="^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
		pattern = Pattern.compile(phonePattern);
		matcher = pattern.matcher(phoneFld.getText());
		if(matcher.matches() == false) {
			CommonService.msg("핸드폰 번호는 하이픈(-)을 사용하여 입력해주세요.(010/011/016/017/018/019 가능)");
			return;
		}

		MemberDTO check = memberDao.selectId(idFld.getText());
		if(check == null) {
			memberDao.insert(idFld.getText(), pwFld.getText(), nameFld.getText(), phoneFld.getText(), carFld.getText());
			CommonService.msg("회원가입이 완료되었습니다. 관리자의 승인을 기다리세요.");
			regStage.close();
		
		}else if (check.getStatus().equals("A")) {
			CommonService.msg("이미 가입된 회원입니다.");
			regStage.close();
		}else {
			CommonService.msg("관리자 승인 대기중입니다.");
			regStage.close();
		}
	}
}