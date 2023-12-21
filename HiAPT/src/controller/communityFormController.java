package controller;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import login.BoardDAO;
import login.BoardDTO;
import login.CommonService;
import login.Opener;

public class communityFormController implements Initializable {
	Opener opener = new Opener();
	
	private Stage communityStage;

	public void setCommunityStage(Stage communityStage) {
		this.communityStage = communityStage;
	}

	@FXML
	ComboBox<String> combo;
	ObservableList<String> comboData;

	@FXML
	TableView<BoardDTO> tableView;
	@FXML
	TableColumn<BoardDTO, String> numCol;
	@FXML
	TableColumn<BoardDTO, String> titleCol;
	@FXML
	TableColumn<BoardDTO, String> contentCol;
	@FXML
	TableColumn<BoardDTO, String> writeTimeCol;
	
	ObservableList<BoardDTO> observableList;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		comboData = FXCollections.observableArrayList("공지사항", "1:1 문의", "자유게시판");
		combo.setItems(comboData);
		combo.setValue("공지사항");

		combo.setStyle("-fx-font-size: 15px;");
		combo.setStyle("-fx-background-color: #fcf0d5;");
		combo.setStyle("-fx-border-color: #6b4418; -fx-border-width: 2px; -fx-border-radius: 30px; -fx-background-radius: 30px;");
		
		//tableview 데이터 넣기
		observableList = FXCollections.observableArrayList();
		  
		numCol.setCellValueFactory(new PropertyValueFactory<>("num"));
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		contentCol.setCellValueFactory(new PropertyValueFactory<>("content"));
		writeTimeCol.setCellValueFactory(new PropertyValueFactory<>("writeTime"));
		
		BoardDAO dao = new BoardDAO();
		Collection<BoardDTO> list = dao.announceViewAll();
		if(list == null) {
			CommonService.msg("등록된 게시글이 없습니다.");
			
		}else {
			observableList.addAll(list);
			tableView.setItems(observableList);
		}
		
	}
	
	
	//콤보박스에서 선택하면 게시판 바뀌게
	@FXML
	private void useHandleChange(ActionEvent event) {
//	    System.out.println(
//			combo.getSelectionModel().getSelectedItem() + ", " + combo.getSelectionModel().getSelectedIndex());
//		출력 결과 1:1 문의, 1
		if (combo.getSelectionModel().getSelectedItem() == "1:1 문의") {
			opener.userScretFormOpen();
			communityStage.close();
		}
		if (combo.getSelectionModel().getSelectedItem() == "자유게시판") {
			opener.userFreeFormOpen();
			communityStage.close();
		}
	}
	
	//내용 검색
	@FXML ImageView search;
	@FXML TextField searchFld;
	@FXML
	public void searchProc(MouseEvent event) {
		BoardDAO dao = new BoardDAO();
		Collection<BoardDTO> search = dao.searchContent(searchFld.getText());
		if(search == null || search.isEmpty()) {
			CommonService.msg("검색하신 결과가 없습니다.");
		}else {
			observableList = FXCollections.observableArrayList(search);
			tableView.setItems(observableList);  
		}
	}
	
	@FXML
	TextField titleFld;
	@FXML
	TextArea contentFld;
	@FXML
	Label writeTime;

	// 선택한 행 필드에 보여주기
	@FXML
	void tableClick(MouseEvent event) {
		if (!tableView.getSelectionModel().isEmpty()) {

			BoardDTO dto = tableView.getSelectionModel().getSelectedItem();

			titleFld.setText(dto.getTitle());
			contentFld.setText(dto.getContent());
			writeTime.setText(dto.getWriteTime());
			
		}
	}
}
