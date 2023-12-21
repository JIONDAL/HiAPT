package login;

public class checkPwFormService {
	
	private MemberDAO memberDao;

	public checkPwFormService() {
		memberDao = new MemberDAO();
	}
	
	public int checkPw(String id, String pw) {	
		String dbPw = memberDao.login(id);//pw값 반환
		if(dbPw.equals(pw)) {
			//정보수정 창 열기
			return 1;
		}else {
			//checkpwform 닫기 
			CommonService.msg("비밀번호가 틀렸습니다.");
			return 0;
		}
	}
}
