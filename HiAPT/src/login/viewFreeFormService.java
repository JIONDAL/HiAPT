package login;

public class viewFreeFormService {
	BoardDAO dao;
	
	public viewFreeFormService() {
		super();
		dao = new BoardDAO();
	}
	
	//자유게시판 글 수정
	public int update(BoardDTO dto){
		int result = dao.myFreeUpdate(dto);
		if(result == 1) {
			CommonService.msg("수정되었습니다.");
			return 1;
		}
		return 0;
	}
	
	//자유게시판 글 삭제
	public int delete(BoardDTO dto) {
		int result = dao.myFreeDelete(dto);
		if(result == 1) {
			CommonService.msg("삭제되었습니다.");
			return 1;
		}
		return 0;
	}
}
