package controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.ResourceBundle;

import DAO.BoardDAO;
import DTO.BoardDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.CommonService;
import service.anounceManageFormService;

public class anounceManageFormController implements Initializable{
	@FXML
	private TableView<BoardDTO> anounceTableView;
	@FXML
	private TableColumn<BoardDTO, String> numColumn;
	@FXML
	private TableColumn<BoardDTO, String> titleColumn;
	@FXML
	private TableColumn<BoardDTO, String> contentColumn;
	@FXML
	private TableColumn<BoardDTO, String> writerColumn;
	@FXML
	private TableColumn<BoardDTO, String> writeTimeColumn;
	@FXML
	private TextField titleFld;
	@FXML
	private TextArea contentArea;
	
	private Parent anounceManageForm;
	private anounceManageFormService service;
	private BoardDAO dao = new BoardDAO();
	private BoardDTO board;
	private int num;
	private String title;
	private String content;
	private String writer;
	private String writeTime;
	private int hits;
	private int likes;


	
	ObservableList<BoardDTO> observableList;
	
	public void setAnounceManageForm(Parent anounceManageForm) {
		this.anounceManageForm = anounceManageForm;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		service = new anounceManageFormService();
		observableList = FXCollections.observableArrayList();
		  
		numColumn.setCellValueFactory(new PropertyValueFactory<>("num"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
		writerColumn.setCellValueFactory(new PropertyValueFactory<>("writer"));
		writeTimeColumn.setCellValueFactory(new PropertyValueFactory<>("writeTime"));
		  
//		BoardDAO dao = new BoardDAO();	
		Collection<BoardDTO> all = dao.viewAll();
		  
		observableList.addAll(all);
		anounceTableView.setItems(observableList);
		  
	}

	public void insertProc() {
		title = titleFld.getText();
		content = contentArea.getText();
		service.insert(title, content);

		// 다시 조회
		Collection<BoardDTO> all = dao.viewAll();
		observableList = FXCollections.observableArrayList(all);
		anounceTableView.setItems(observableList);  
	}
	
	public ButtonType confirm(String head, String msg) {
		Alert confirm = new Alert(AlertType.CONFIRMATION);
		confirm.setTitle("확인");
		confirm.setHeaderText(head);
		confirm.setContentText(msg);
		return confirm.showAndWait().get();
	}
	

	@FXML
	void tableClick(MouseEvent event) {
		if (!anounceTableView.getSelectionModel().isEmpty()) {

			board = anounceTableView.getSelectionModel().getSelectedItem();

			titleFld.setText(board.getTitle());
			contentArea.setText(board.getContent());

		} 
	}
	
	@FXML
	void updateProc(ActionEvent event) {
		if(anounceTableView.getSelectionModel().isEmpty()) {
			CommonService.msg("수정할 데이터를 선택하세요.");
			return;
		}
		
		BoardDTO up = anounceTableView.getSelectionModel().getSelectedItem();
		
		if(up.getTitle().equals(titleFld.getText()) && up.getContent().equals(contentArea.getText())) {
			CommonService.msg("수정된 내용이 없습니다.");
		}else {
			up.setTitle(titleFld.getText());
			up.setContent(contentArea.getText());
			dao.update(up);
			
			CommonService.msg("게시글 수정완료!");
		}
		// 다시 조회
				Collection<BoardDTO> all = dao.viewAll();
				observableList = FXCollections.observableArrayList(all);
				anounceTableView.setItems(observableList); 
	}
	
	
	public void deleteProc(ActionEvent event) {
		if(anounceTableView.getSelectionModel().isEmpty()) {
			CommonService.msg("삭제할 데이터를 선택하세요.");
			return;
		}
		
		board = new BoardDTO(num, title, content, writer, writeTime, hits, likes);
		ButtonType btnType = confirm("삭제확인", "정말로 삭제하시겠습니까?");
		if(btnType == ButtonType.OK) {
			BoardDTO tit = anounceTableView.getSelectionModel().getSelectedItem();
			
			dao.delete(tit);
			CommonService.msg("삭제했습니다.");
		}else {
			CommonService.msg("취소했습니다.");
		}
		// 다시 조회
				Collection<BoardDTO> all = dao.viewAll();
				observableList = FXCollections.observableArrayList(all);
				anounceTableView.setItems(observableList); 
	}
	
}
