package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import DTO.AptDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.CommonService;
import service.LoginService;
import main.Opener;

public class LoginController implements Initializable {
	@FXML
	TextField idFld;
	@FXML
	PasswordField pwFld;
	@FXML
	Button loginBtn;

	private LoginService service;
	private Opener opener;
	private Stage primaryStage;
	
	public void setOpener(Opener opener) {
		this.opener = opener;
	}
	
	public void setStage(Stage primaryStage) {
		// TODO Auto-generated method stub
		this.primaryStage = primaryStage;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginBtn.setDisable(true);
		service = new LoginService();
	}

	// 아이디 입력시 실행되는 메서드(onKeyReleased)
	public void idCheck() {
		idFld.textProperty().addListener((a, oldText, newText) -> {
			if (newText.length() < 1)
				loginBtn.setDisable(true);
			else
				loginBtn.setDisable(false);

			if (newText.length() > 7) {
				CommonService.msg("아이디는 동-호수 입니다");
				idFld.setText(oldText);
			}

		});
	}

	// 비밀번호 입력시 실행되는 메서드(onKeyReleased)
	public void pwCheck() {
		if (pwFld.getText().length() > 10) {
			CommonService.msg("비밀번호는 10자리 이하입니다.");
			pwFld.setText(pwFld.getText().substring(0, 10));
		}
	}

	// 로그인 버튼을 클릭하면 동작하는 메서드
	public void loginProc() {
		if (idFld.getText() == null || idFld.getText().isEmpty()) {
			CommonService.msg("아이디를 입력하세요.");
			return;
		}

		if (pwFld.getText() == null || pwFld.getText().isEmpty()) {
			CommonService.msg("비밀번호를 입력하세요.");
			return;
		}

		service.login(idFld.getText(), pwFld.getText());

		if (AptDTO.getId() == null)
			return;

		// 로그인 성공이라면 로그인 창에 userPage or adminPage을 실행
		if (AptDTO.getId().equals("admin"))
			opener.adminFormOpen();
		else
			opener.userFormOpen();
	}

	// 회원가입 버튼을 클릭하면 회원가입창 새로 열리게
	public void regProc() {
		opener.regFormOpen();
	}

	// 닫기 버튼을 클릭하면 동작하는 메서드
	public void cancelProc() {
		primaryStage.close();
	}

	
}