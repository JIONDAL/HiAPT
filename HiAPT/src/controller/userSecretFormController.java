package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import login.Opener;

public class userSecretFormController implements Initializable{
	Opener opener = new Opener();
	
	private Stage communityStage;
	public void setCommunityStage(Stage communityStage) {
		this.communityStage = communityStage;
	}
	
	@FXML ComboBox<String> combo;
	ObservableList<String> comboData;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		comboData = FXCollections.observableArrayList("공지사항", "1:1 문의", "자유게시판");
		combo.setItems(comboData);
		combo.setValue("1:1 문의");
		
		combo.setStyle("-fx-font-size: 15px;");
		combo.setStyle("-fx-background-color: #fcf0d5;");
		combo.setStyle("-fx-border-color: #6b4418; -fx-border-width: 2px; -fx-border-radius: 30px; -fx-background-radius: 30px;");
	}
	
	//콤보박스에서 카테고리 선택시 게시판 이동
	@FXML
	private void useHandleChange(ActionEvent event) {
		if (combo.getSelectionModel().getSelectedItem() == "공지사항") {
			opener.communityFormOpen();
			communityStage.close();
		}
		if (combo.getSelectionModel().getSelectedItem() == "자유게시판") {
			opener.userFreeFormOpen();
			communityStage.close();
		}
	}
	
	//1:1 문의하기 버튼 클릭시 -> 문의창 새로 열기 
	public void writeSecret() {
		opener.writeSecretFormOpen();
	}
	
	//내 문의 보기 버튼 클릭시 - > 새창으로 열기
	public void mySecretProc() {
		opener.mySecretFormOpen();
	}
}
