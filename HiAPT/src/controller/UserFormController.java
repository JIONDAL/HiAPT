package controller;

import java.net.URL;
import java.util.ResourceBundle;

import DTO.AptDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.Opener;

public class UserFormController implements Initializable {
	private Opener opener = new Opener();

	@FXML Label idLabel;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idLabel.setText(AptDTO.getId());
	}
	
	//마이페이지 버튼 누르면 마이페이지 창 새로 열리게
	public void myPageProc() {
		opener.myPageFormOpen();
	}

	//커뮤니티 버튼 누르면 커뮤니티 창 새로 열리게
	public void communityProc() {
		opener.communityFormOpen();
	}

	
}
