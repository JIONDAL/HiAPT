package controller;

import java.net.URL;
import java.util.ResourceBundle;

import DAO.costDAO;
import DTO.AptDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.Opener;

public class myMoneyFormController implements Initializable {
	costDAO costDao = new costDAO();
	Opener opener = new Opener();
	
	Stage myMoneyStage;
	public void setMyMoneyStage(Stage myMoneyStage) {
		this.myMoneyStage = myMoneyStage;
	}
	
	@FXML Label idLabel;
	@FXML Label moneyLabel;
	
	//String money = "0";
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idLabel.setText(AptDTO.getId());
		
		//잔액 조회
		String money = costDao.selectMoney(AptDTO.getId());
		if(money == null || money.isEmpty() == true)
			money = "0";
		
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
