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

public class secretManageFormController implements Initializable{
	@FXML
	private TableView<BoardDTO> tableView;
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
	private TableColumn<BoardDTO, String> commentColumn;
	@FXML
	private TextField titleFld;
	@FXML
	private TextArea contentArea;
	@FXML
	private TextArea commentFld;
	
	private Parent secretManageForm;
	private BoardDAO dao = new BoardDAO();
	private BoardDTO board;
	private int num;
	private String title;
	private String content;
	private String writer;
	private String writeTime;
	private int hits;
	private int likes;
	private String comment;

	ObservableList<BoardDTO> observableList;
	
	public void setSecretManageForm(Parent secretManageForm) {
		this.secretManageForm = secretManageForm;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		observableList = FXCollections.observableArrayList();
		  
		numColumn.setCellValueFactory(new PropertyValueFactory<>("num"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
		writerColumn.setCellValueFactory(new PropertyValueFactory<>("writer"));
		writeTimeColumn.setCellValueFactory(new PropertyValueFactory<>("writeTime"));
		commentColumn.setCellValueFactory(new PropertyValueFactory<>("comments"));
		  
		Collection<BoardDTO> all = dao.secretViewAll();
		  
		observableList.addAll(all);
		tableView.setItems(observableList);
		  
	}

	public void insertProc(ActionEvent event) {
		comment = commentFld.getText();
		BoardDTO up = tableView.getSelectionModel().getSelectedItem();
		
		if(comment.isEmpty()) {
			CommonService.msg("답변을 작성하세요.");
		}else {
			up.setComments(comment);
			dao.insertComment(up);
			CommonService.msg("답변이 등록되었습니다.");
		}
		
		Collection<BoardDTO> all = dao.secretViewAll();
		observableList = FXCollections.observableArrayList(all);
		tableView.setItems(observableList); 
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
		if (!tableView.getSelectionModel().isEmpty()) {

			board = tableView.getSelectionModel().getSelectedItem();
			
			titleFld.setText(board.getTitle());
			contentArea.setText(board.getContent());
			commentFld.setText(board.getComments());	
			
		} 
	}
	
//	@SuppressWarnings("unlikely-arg-type")
	public void updateProc(ActionEvent event) {
		if(tableView.getSelectionModel().isEmpty()) {
			CommonService.msg("수정할 데이터를 선택하세요.");
			return;
		}
		BoardDTO up = tableView.getSelectionModel().getSelectedItem();
		
		comment = commentFld.getText();
		if(up.getComments().equals(comment)) {
			CommonService.msg("수정된 내용이 없습니다.");
		}else {
			up.setComments(comment);
			dao.updateComment(up);
			
			CommonService.msg("답변 수정완료!");
		}
		Collection<BoardDTO> all = dao.secretViewAll();
		observableList = FXCollections.observableArrayList(all);
		tableView.setItems(observableList); 
	}
	
	
	public void deleteProc(ActionEvent event) {
		if(tableView.getSelectionModel().isEmpty()) {
			CommonService.msg("삭제할 데이터를 선택하세요.");
			return;
		}
		
		ButtonType btnType = confirm("삭제확인", "게시글이 '전체' 삭제됩니다.\n정말로 삭제하시겠습니까?");
		if(btnType == ButtonType.OK) {
			BoardDTO tit = tableView.getSelectionModel().getSelectedItem();
			
			dao.deleteSecret(tit);
			CommonService.msg("삭제했습니다.");
		}else {
			CommonService.msg("취소했습니다.");
		}
		Collection<BoardDTO> all = dao.secretViewAll();
		observableList = FXCollections.observableArrayList(all);
		tableView.setItems(observableList); 
	}
}
