package login;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LoginService {
	private MemberDAO memberDao;
	
	public LoginService() {
		memberDao = new MemberDAO();
	}
	
	public void login(String id, String pw) {
		String dbPw = memberDao.login(id);
		String dbStatus = memberDao.selectStatus(id);
		String a = "A";
		String d = "D";
		
//		if(dbPw != null && dbPw.equals(pw) && dbStatus.equals(a)) {
//			// 로그인 성공
//			Login.setId(id);
//		}else if(dbStatus.equals(d)) {
//			CommonService.msg("관리자 승인 대기중입니다.");
//		}else if(dbPw == null) { // 안됨....
//			CommonService.msg("등록된 아이디가 없습니다.");
//		}else{ // 안됨....
//			CommonService.msg("아이디 또는 비밀번호가 틀렸습니다.");
//		}
		
		
		if(dbPw == null) {
	         CommonService.msg("등록된 아이디가 없습니다.");
	      }else if(dbPw.equals(pw) == false && dbPw != null){
	         CommonService.msg("비밀번호가 틀렸습니다.");
	      }else if(dbStatus.equals(d)) {
	         CommonService.msg("관리자 승인 대기중입니다.");
	      }else if(dbPw != null && dbPw.equals(pw) && dbStatus.equals(a)){
	         Login.setId(id);
	      }else { // 안나옴.....
	         CommonService.msg("관리자에게 문의하세요.");
	      }
		
	}
}