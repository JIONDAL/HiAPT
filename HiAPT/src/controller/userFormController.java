package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.stage.Stage;
import login.Opener;

public class userFormController implements Initializable {
	
	private Opener opener = new Opener();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
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
