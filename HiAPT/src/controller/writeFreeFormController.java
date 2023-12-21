package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import login.BoardDTO;
import login.CommonService;
import login.Login;
import login.writeFreeFormService;

public class writeFreeFormController implements Initializable{
	private writeFreeFormService service;
	
	Stage writeFreeStage;
	public void setWriteFreeStage(Stage writeFreeStage){
		this.writeFreeStage = writeFreeStage;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		service = new writeFreeFormService();
	}
	
	
	@FXML
	TextField titleFld;
	@FXML
	TextArea contentFld;
	//글쓰기 버튼 누르면 DB에 등록
	public void writeFreeProc() {
	      BoardDTO boardDto = new BoardDTO();
	      if(titleFld.getText() == null || contentFld.getText() == null || titleFld.getText().isEmpty() 
	    		  || contentFld.getText().isEmpty() ) {
	         CommonService.msg("제목 또는 내용을 입력하세요.");
	      }else {
	         boardDto.setTitle(titleFld.getText());
	         boardDto.setContent(contentFld.getText());
	         boardDto.setWriter(Login.getId());
	         
	         int result = service.insert(boardDto);
	         if (result == 1) {
	            CommonService.msg("게시글이 등록되었습니다.");
	            writeFreeStage.close();
	         }
	      }

	  }
	
	//닫기 버튼 -> 창닫기
	public void closeProc() {
		writeFreeStage.close();
	}

}
