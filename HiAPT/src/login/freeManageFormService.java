package login;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class freeManageFormService {
	private BoardDAO boardDao;
	
	private String writer;
	private String writeTime;
	private int hits;
	private int likes;
	
	public freeManageFormService() {
		boardDao = new BoardDAO();
	}
	
	public void insert(String title, String content) {
		
		writer = Login.getId();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();
		writeTime = dtf.format(now);
		
		BoardDTO board = new BoardDTO();
		if(title.isEmpty()) {
			CommonService.msg("제목을 입력하세요.");
		}else if(content.isEmpty()) {
			CommonService.msg("내용을 입력하세요.");
		}else if(title.isEmpty() == false && content.isEmpty() == false){
			board.setTitle(title);
			board.setContent(content);
			board.setWriter(writer);
			board.setWriteTime(writeTime);
			boardDao.writeFree(board);
			CommonService.msg("자유게시글이 등록되었습니다.");
		}
	}

}
