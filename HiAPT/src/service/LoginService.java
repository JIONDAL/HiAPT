package service;

import DAO.MemberDAO;
import DTO.AptDTO;

public class LoginService {
	private MemberDAO memberDao;

	public LoginService() {
		memberDao = new MemberDAO();
	}

	public void login(String id, String pw) {
		String dbPw = memberDao.login(id);
		String dbStatus = memberDao.selectStatus(id);
		
		if (dbPw == null) {
			CommonService.msg("등록되지 않은 회원입니다.");
			return;
		}
		
		if(dbPw != null && dbPw.equals(pw)) {
			if (dbStatus.equalsIgnoreCase("a")) 
				AptDTO.setId(id);
			else {
				CommonService.msg("관리자 승인 대기중입니다.");
			}
			
		}else {
			CommonService.msg("비밀번호가 올바르지 않습니다.");
		}

	}
}