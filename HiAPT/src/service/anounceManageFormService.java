package service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import DAO.BoardDAO;
import DTO.AptDTO;
import DTO.BoardDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class anounceManageFormService {
	private BoardDAO boardDao;
	
	private int num;
	private String title;
	private String content;
	private String writer;
	private String writeTime;
	private int hits;
	private int likes;
	
	public anounceManageFormService() {
		boardDao = new BoardDAO();
	}
	
	public void insert(String title, String content) {
//		TextField titleFld = (TextField) anounceManageForm.lookup("#titleFld");
//		TextArea contentFld = (TextArea) anounceManageForm.lookup("#contentArea");
//		
//		String title = titleFld.getText();
//		String content = contentFld.getText();
		
		writer = AptDTO.getId();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();
		writeTime = dtf.format(now);
		
		BoardDTO board = new BoardDTO(num, title, content,writer, writeTime, hits, likes);
		if(title.isEmpty()) {
			CommonService.msg("제목을 입력하세요.");
		}else if(content.isEmpty()) {
			CommonService.msg("내용을 입력하세요.");
		}else if(title.isEmpty() == false && content.isEmpty() == false){
			board.setTitle(title);
			board.setContent(content);
			board.setWriter(writer);
			board.setWriteTime(writeTime);
			board.setHits(hits);
			board.setLikes(likes);
			boardDao.insert(board);
			CommonService.msg("공지사항이 등록되었습니다.");
		}
	}
	
	public ButtonType confirm(String head, String msg) {
		Alert confirm = new Alert(AlertType.CONFIRMATION);
		confirm.setTitle("확인");
		confirm.setHeaderText(head);
		confirm.setContentText(msg);
		return confirm.showAndWait().get();
	}
	
	
	
//	@FXML
//	private TableView<BoardDTO> anounceTableView;
//	public void delete(ActionEvent event) {
//		if(anounceTableView.getSelectionModel().isEmpty()) {
//			CommonService.msg("삭제할 데이터를 선택하세요.");
//			return;
//		}
//		
//		BoardDTO board = new BoardDTO(num, title, content, writer, writeTime, hits, likes);
//		ButtonType btnType = confirm("삭제확인", "정말로 삭제하시겠습니까?");
//		if(btnType == ButtonType.OK) {
//			BoardDTO tit = anounceTableView.getSelectionModel().getSelectedItem();
//			
//			boardDao.delete(tit);
//			CommonService.msg(tit + "을 삭제했습니다.");
//		}else {
//			CommonService.msg("취소했습니다.");
//		}
//	}

}
