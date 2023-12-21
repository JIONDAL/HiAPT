package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import login.Login;
import login.Opener;
import login.costDAO;

public class myMoneyFormController implements Initializable {
	costDAO costDao = new costDAO();
	Opener opener = new Opener();
	
	Stage myMoneyStage;
	public void setMyMoneyStage(Stage myMoneyStage) {
		this.myMoneyStage = myMoneyStage;
	}
	
	@FXML Label idLabel;
	@FXML Label moneyLabel;
	
	String money = "0";
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idLabel.setText(Login.getId());
		
		//잔액 조회
		if(costDao.selectMoney(Login.getId()) != null) {
			money = costDao.selectMoney(Login.getId());
		}
		
		moneyLabel.setText(money);
	}

	// 충전하기 버튼
	public void chargeProc() {
		opener.chargeMoenyFormOpen();
		myMoneyStage.close();
	}

	// 닫기 버튼
	public void closeProc() {
		myMoneyStage.close();
	}

}
