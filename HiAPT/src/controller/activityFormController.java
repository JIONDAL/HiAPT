package controller;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import DAO.BoardDAO;
import DTO.BoardDTO;
import DTO.replyDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.CommonService;

public class activityFormController implements Initializable {
	//작성 게시글, 작성 댓글 
	//작성게시글
	@FXML
	TableView<BoardDTO> tableView1;
	@FXML
	TableColumn<BoardDTO, String> writeCol;
	@FXML
	TableColumn<BoardDTO, String> writeTimeCol;
	ObservableList<BoardDTO> observableList1;
	
	//작성댓글
	@FXML
	TableView<replyDTO> tableView2;
	@FXML
	TableColumn<replyDTO, String> replyCol;
	@FXML
	TableColumn<replyDTO, String> replyTimeCol;
	ObservableList<replyDTO> observableList2;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		observableList1 = FXCollections.observableArrayList();
		  
		writeCol.setCellValueFactory(new PropertyValueFactory<>("content"));
		writeTimeCol.setCellValueFactory(new PropertyValueFactory<>("writeTime"));
		
		BoardDAO dao = new BoardDAO();
		Collection<BoardDTO> list = dao.myWriteAll();
		if(list == null) {
			CommonService.msg("등록된 게시글이 없습니다.");
			
		}else {
			observableList1.addAll(list);
			tableView1.setItems(observableList1);
		}
		
		observableList2 = FXCollections.observableArrayList();
		  
		replyCol.setCellValueFactory(new PropertyValueFactory<>("reply"));
		replyTimeCol.setCellValueFactory(new PropertyValueFactory<>("writeTime"));
		
		Collection<replyDTO> list2 = dao.myReplyAll();
		if(list2 == null) {
			CommonService.msg("등록된 댓글이 없습니다.");
			
		}else {
			observableList2.addAll(list2);
			tableView2.setItems(observableList2);
		}
	}

}
