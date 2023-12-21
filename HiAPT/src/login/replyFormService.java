package login;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.stage.Stage;

public class replyFormService {
	BoardDAO dao; 
	
	Stage replyStage;
	public replyFormService() {
		dao = new BoardDAO();
	}
	
	//댓글 작성
	public int writeReply(replyDTO replyDto) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");  
		LocalDateTime now = LocalDateTime.now();
		replyDto.setWriteTime(dtf.format(now));
		
		int result = dao.writeReply(replyDto);
		if(result == 1) {
			CommonService.msg("댓글이 등록되었습니다.");
		}
		return result;
	}
}
