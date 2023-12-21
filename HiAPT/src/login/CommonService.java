package login;

import java.util.Optional;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class CommonService {

	public static void msg(String contextText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("알림");
		alert.setContentText(contextText);
		alert.show();
	}
	
	public static Optional<ButtonType> confirmMsg(String contextText) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("알림");
		alert.setContentText(contextText);
		Optional<ButtonType> result = alert.showAndWait();
		
		return result;
	}
	
	public static ButtonType confirm(String head, String msg) {
		Alert confirm = new Alert(AlertType.CONFIRMATION);
		confirm.setTitle("확인");
		confirm.setHeaderText(head);
		confirm.setContentText(msg);
		return confirm.showAndWait().get();
	}
	
	public static void alert(String head, String msg) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("경고");
		alert.setHeaderText(head);
		alert.setContentText(msg);

		alert.showAndWait(); // Alert창 보여주기
	}
	
	public static void windowClose(Stage stage) {
		stage.close();
	}
	
	public static void windowClose(Parent form) {
		Stage stage = (Stage) form.getScene().getWindow();
		stage.close();
	}
}
