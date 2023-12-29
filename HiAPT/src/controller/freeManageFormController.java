package controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.ResourceBundle;

import DAO.BoardDAO;
import DTO.AptDTO;
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
import service.freeManageFormService;

public class freeManageFormController implements Initializable{
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
	// 댓글 테이블 
	@FXML
	private TableView<BoardDTO> replyTableView;
	@FXML
	private TableColumn<BoardDTO, String> replyColumn;
	@FXML
	private TableColumn<BoardDTO, String> replyIdColumn;
	@FXML
	private TableColumn<BoardDTO, String> replyTimeColumn;
	@FXML
	private TextField titleFld;
	@FXML
	private TextArea contentArea;
	@FXML
	private TextField commentFld;
	
	private Parent freeManageForm;
	private freeManageFormService service;
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
	
	public void setFreeManageForm(Parent freeManageForm) {
		this.freeManageForm = freeManageForm;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		service = new freeManageFormService();
		observableList = FXCollections.observableArrayList();
		  
		numColumn.setCellValueFactory(new PropertyValueFactory<>("num"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
		writerColumn.setCellValueFactory(new PropertyValueFactory<>("writer"));
		writeTimeColumn.setCellValueFactory(new PropertyValueFactory<>("writeTime"));
		
		Collection<BoardDTO> all = dao.freeAll();
		observableList.addAll(all);
		tableView.setItems(observableList);
		  
	}

	public void insertProc() {
		if(commentFld.getText().isEmpty()) {
			title = titleFld.getText();
			content = contentArea.getText();
			service.insert(title, content);
		}else {
			CommonService.msg("게시글 등록 후 댓글을 작성하세요.");
		}
		
		Collection<BoardDTO> all = dao.freeAll();
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
	
	public void commentProc(ActionEvent event) {
		comment = commentFld.getText();
		BoardDTO up = tableView.getSelectionModel().getSelectedItem();
		
		title = up.getTitle();
		content = up.getContent();
		writer = AptDTO.getId();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();
		writeTime = dtf.format(now);
		
		if(comment.isEmpty()) {
			CommonService.msg("댓글을 작성하세요.");
		}else {
			up.setTitle(title);
			up.setContent(content);
			up.setWriter(writer);
			up.setWriteTime(writeTime);
			up.setReplyComment(comment);
			dao.insertReply(up);
			CommonService.msg("댓글이 등록되었습니다.");
			commentFld.clear();
		}
		
		replyColumn.setCellValueFactory(new PropertyValueFactory<>("replyComment"));
		replyIdColumn.setCellValueFactory(new PropertyValueFactory<>("replyId"));
		replyTimeColumn.setCellValueFactory(new PropertyValueFactory<>("replyTime"));
		Collection<BoardDTO> commentAll = dao.replyAll(board.getTitle(), board.getContent());
		observableList = FXCollections.observableArrayList(commentAll);
		replyTableView.setItems(observableList); 
	}


	@FXML
	void tableClick(MouseEvent event) {
		if (!tableView.getSelectionModel().isEmpty()) {

			board = tableView.getSelectionModel().getSelectedItem();

			titleFld.setText(board.getTitle());
			contentArea.setText(board.getContent());
			
			// 댓글 테이블에 보이기 
			replyColumn.setCellValueFactory(new PropertyValueFactory<>("replyComment"));
			replyIdColumn.setCellValueFactory(new PropertyValueFactory<>("replyId"));
			replyTimeColumn.setCellValueFactory(new PropertyValueFactory<>("replyTime"));
			Collection<BoardDTO> commentAll = dao.replyAll(board.getTitle(), board.getContent());
			observableList = FXCollections.observableArrayList(commentAll);
			replyTableView.setItems(observableList); 
		} 
	}
	
	@FXML
	void replyTableClick(MouseEvent event) {
		if(event.getClickCount() > 1) {
			
			ButtonType btnType = confirm("삭제확인", "댓글이 삭제됩니다.\n정말로 삭제하시겠습니까?");
			if(btnType == ButtonType.OK) {
				BoardDTO re = replyTableView.getSelectionModel().getSelectedItem();
				re.setTitle(titleFld.getText());
				re.setContent(contentArea.getText());
				dao.deleteReply(re);
				CommonService.msg("댓글을 삭제했습니다.");
			}else {
				CommonService.msg("취소했습니다.");
			}
		}
		
		replyColumn.setCellValueFactory(new PropertyValueFactory<>("replyComment"));
		replyIdColumn.setCellValueFactory(new PropertyValueFactory<>("replyId"));
		replyTimeColumn.setCellValueFactory(new PropertyValueFactory<>("replyTime"));
		Collection<BoardDTO> commentAll = dao.replyAll(board.getTitle(), board.getContent());
		observableList = FXCollections.observableArrayList(commentAll);
		replyTableView.setItems(observableList); 
		
	}
	
	public void updateProc(ActionEvent event) {
		if(tableView.getSelectionModel().isEmpty()) {
			CommonService.msg("수정할 데이터를 선택하세요.");
			return;
		}
		
		BoardDTO up = tableView.getSelectionModel().getSelectedItem();
		
		if(up.getTitle().equals(titleFld.getText()) && up.getContent().equals(contentArea.getText())) {
			CommonService.msg("수정된 내용이 없습니다.");
		}else {
			up.setTitle(titleFld.getText());
			up.setContent(contentArea.getText());
			dao.updateFree(up);
			
			CommonService.msg("게시글 수정완료!");
		}
		Collection<BoardDTO> all = dao.freeAll();
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
			
			dao.deleteFree(tit);
			CommonService.msg("삭제했습니다.");
		}else {
			CommonService.msg("취소했습니다.");
		}

		Collection<BoardDTO> all = dao.freeAll();
		observableList = FXCollections.observableArrayList(all);
		tableView.setItems(observableList);
	}
}
