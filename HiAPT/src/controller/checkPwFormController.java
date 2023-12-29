package controller;

import java.net.URL;
import java.util.ResourceBundle;

import DTO.AptDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import main.Opener;
import service.checkPwFormService;

public class checkPwFormController implements Initializable {
	
	@FXML PasswordField pwFld;
	private checkPwFormService service;
	private Stage checkPwStage;
	private Opener opener = new Opener();
	
	public void setcheckPwStage(Stage checkPwStage) {
		this.checkPwStage = checkPwStage;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		service = new checkPwFormService();
	}
	
	//확인버튼 누르면 실행
	public void checkPwProc() {
		int result = service.checkPw(AptDTO.getId(), pwFld.getText()); 
		if(result == 1) {
			//비밀번호 일치시 - > 회원정보 수정 창 열기
			opener.changeInfoFormOpen();
			checkPwStage.close();
		}else{
			//비밀번호 확인 창 닫기
			checkPwStage.close();
		}
	}
	
	//창 닫기
	public void cancleProc() {
		checkPwStage.close();
	}
}
