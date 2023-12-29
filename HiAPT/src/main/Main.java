package main ;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loginForm.fxml"));
		Parent form = loader.load();
		LoginController loginCon = loader.getController();
		
		Opener opener = new Opener();
		loginCon.setOpener(opener);
		loginCon.setStage(primaryStage);
		
		//기존 창에서 열기 위함
		opener.setPrimaryStage(primaryStage);
		
		primaryStage.setTitle("로그인 화면");
		primaryStage.setScene(new Scene(form));
		primaryStage.show();
	}
}