package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import main.Opener;

public class adminFormController implements Initializable {

	private Opener opener = new Opener();

//	public void setOpener(Opener opener) { // 이거 진짜 왜안됨..?ㅜㅜ..
//		this.opener = opener;
//	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void userManageProc() {
		opener.userManageFormOpen();
	}
	
	public void boardManageProc() {
		opener.boardManageFormOpen();
	}
	
	public void costManageProc() {
		opener.costManageFormOpen();
	}

}
