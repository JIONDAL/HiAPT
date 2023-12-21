package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import login.Opener;

public class userManageFormController implements Initializable {
	
	private Opener opener = new Opener();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	
	public void allMemberProc() { // 모든 회원 조회 
		opener.allMemberFormOpen();
	}
//	public void agreeMemberProc() { // 승인완료 회원 조회 
//	}
	public void disagreeMemberProc() { // 승인대기 회원 조회 
		opener.DisMemberFormOpen();
	}
	public void deleteMemberProc() { // 회원 삭제 
		opener.deleteMemberFormOpen();
	}

}

