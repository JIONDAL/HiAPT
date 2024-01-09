package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.CommonService;
import service.RegService;

public class RegController implements Initializable {
	private Stage regStage;
	private Parent regForm;
	private RegService regService;
	@FXML Button regBtn;
	@FXML TextField idFld;
	@FXML TextField carFld;
	
	public void setRegStage(Stage regStage) {
		this.regStage = regStage;
	}

	public void setRegForm(Parent regForm) {
		this.regForm = regForm;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		regService = new RegService();
		regBtn.setDisable(true);
	}
	
	public void idCheck() {
		if(idFld.getText().length() > 5) {
			regBtn.setDisable(false);
		}
		
		if(idFld.getText().length() > 7) {
			CommonService.msg("아이디는 동-호수 입니다");
			idFld.setText(idFld.getText().substring(0, 7));
		}
	}
	
	public void carCheck() {
		if(carFld.getText().length() > 4) {
			CommonService.msg("차량번호는 뒤 네자리만 입력하세요");
			carFld.setText(carFld.getText().substring(0, 4));
		}
	}	
	
	// 회원가입 버튼 클릭 시 "관리자 승인을 기다리세요!" 알림창 뜨도록 
	public void regProc() {
		regService.insert(regForm, regStage);
	}

	// 취소 버튼 클릭 시 호출되는 메서드
	public void cancelProc() {
		regStage.close();
	}
	
}

