package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import login.CommonService;
import login.Login;
import login.Opener;
import login.accountFormService;
import login.costDAO;

public class accountFormContoller implements Initializable{
	costDAO costDao = new costDAO();
	accountFormService service = new accountFormService();
	
	@FXML
	ComboBox<String> combo;
	ObservableList<String> comboData;
	
	Stage accountStage;
	public void setAccountStage(Stage accountStage) {
		this.accountStage = accountStage;
	}
	
	Stage costPayStage;
	public void setCostPayStage(Stage costPayStage) {
		this.costPayStage = costPayStage;
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		comboData = FXCollections.observableArrayList("국민", "기업", "우리", "하나", "농협", "신한");
		combo.setItems(comboData);
		combo.setValue("은행 선택");
		combo.setStyle("-fx-font-size: 20px;");
		combo.setStyle("-fx-background-color: #fcf0d5;");
		combo.setStyle("-fx-border-color: #6b4418; -fx-border-width: 2px; -fx-border-radius: 30px; -fx-background-radius: 30px;");
		
	}
	
	@FXML Label bankLabel;
	@FXML Label accountingLabel;
	private String bank;
	private String accounting;
	
	@FXML
	private void useHandleChange(ActionEvent event) {
		//게좌 선택
		bank = combo.getSelectionModel().getSelectedItem();
		bankLabel.setText(bank + "은행");
		
		//계좌번호 보여주기
		accounting = service.selectAccount(Login.getId(), bank);
		accountingLabel.setText(accounting);
	}
	
	String year;
	String month;
	public void setDate(String year, String month) {
		// TODO Auto-generated method stub
		this.year = year;
		this.month = month;
	}
	
	Opener opener = new Opener();
	//입금 요청 버튼 클릭시
	public void requestProc() {
		if(CommonService.confirmMsg(year + "년 " + month + "월 관리비 납부를 확인 요청하시겠습니까?").get().equals(ButtonType.OK)){
			String status = "확인요청";
			service.request(year, month, status);
			CommonService.msg("관리자 확인 후 '납부' 처리됩니다.");
			accountStage.close();
		} else {
			CommonService.msg("납부 확인 요청을 취소하였습니다.");
		}
	}
	
	//닫기 버튼 클릭시
	public void closeProc() {
		accountStage.close();
	}
}
