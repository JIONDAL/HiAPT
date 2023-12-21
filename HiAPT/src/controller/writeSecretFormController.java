package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import login.BoardDTO;
import login.CommonService;
import login.Login;
import login.writeSecretFormService;

public class writeSecretFormController implements Initializable {
	private Stage writeSecretStage;
	private writeSecretFormService service;

	public void setwriteSecretStage(Stage writeSecretStage) {
		this.writeSecretStage = writeSecretStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 서비스 필요 있는지 체크
		service = new writeSecretFormService();
	}

	@FXML
	TextField titleFld;
	@FXML
	TextArea contentFld;

	public void writeSecretProc() {
		BoardDTO boardDto = new BoardDTO();
		boardDto.setTitle(titleFld.getText());
		boardDto.setContent(contentFld.getText());
		boardDto.setWriter(Login.getId());
		boardDto.setId(Login.getId());
		int result = service.insert(boardDto);
		if (result == 1) {
			CommonService.msg("1:1 문의가 등록되었습니다. 곧 답변 드릴게요!");
			writeSecretStage.close();
		}
	}

	public void closeProc() {
		writeSecretStage.close();
	}

}
