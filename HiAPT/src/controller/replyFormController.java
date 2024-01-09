package controller;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import DAO.BoardDAO;
import DTO.AptDTO;
import DTO.BoardDTO;
import DTO.replyDTO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.CommonService;
import service.replyFormService;

public class replyFormController implements Initializable {
	replyFormService service;
	BoardDAO dao = new BoardDAO();
	
	BoardDTO dto;
	public void setBoardDto(BoardDTO dto) {
		this.dto = dto;
	}

	Stage replyStage;
	public void setReplyStage(Stage replyStage) {
		this.replyStage = replyStage;
	}

	@FXML
	TableView<replyDTO> tableView;
	@FXML
	TableColumn<replyDTO, String> replyCol;
	@FXML
	TableColumn<replyDTO, String> writerCol;
	
	ObservableList<replyDTO> observableList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		service = new replyFormService();
	}
	
	//댓글 목록
	@FXML
	void tableTouch() {
		observableList = FXCollections.observableArrayList();

		replyCol.setCellValueFactory(new PropertyValueFactory<>("reply"));
		writerCol.setCellValueFactory(new PropertyValueFactory<>("writer"));

		Collection<replyDTO> replys = dao.replyViewAll(dto);
		if (replys.isEmpty()) {
			return;
		} else {
			observableList.addAll(replys);
			tableView.setItems(observableList);
		}
	}
	
	@FXML
	TextArea replyFld;

	// 댓글 작성
	public void writeReply() {
		if (replyFld.getText().isEmpty()) {
			CommonService.msg("내용을 입력하세요.");
		} else {
			replyDTO replyDto = new replyDTO();
			replyDto.setTitle(dto.getTitle());// 글의 제목(어느 글에 댓글을 작성했는지 체크하기 위함)
			replyDto.setContent(dto.getContent());// 글의 내용(어느 글에 댓글을 작성했는지 체크하기 위함)
			replyDto.setWriter(AptDTO.getId());// 댓글 작성자
			replyDto.setReply(replyFld.getText());// 작성한 댓글
			
			int result = service.writeReply(replyDto);
			if (result == 1) {
				Collection<replyDTO> all = dao.replyViewAll(dto);
				observableList = FXCollections.observableArrayList(all);
				tableView.setItems(observableList);
			}
		}
	}

	@FXML
	Button writeBtn;
	@FXML
	Button changeBtn;
	@FXML
	Button deleteBtn;

	// 선택한 행 필드에 보여주기
	@FXML
	void tableClick() {
		replyDTO dto = tableView.getSelectionModel().getSelectedItem();
		
		if(dto == null) {
			return;
		}
		
		writeBtn.setDisable(true);
		//댓글 작성자 = 댓글 클릭한 사용자의 아이디가 다를 경우 삭제 버튼 비활성화
		if(!dto.getWriter().equals(AptDTO.getId())) {
			deleteBtn.setDisable(true);
		}
	}
	
	//댓글 작성란 클릭시 작성 버튼 활성화
	@FXML
	void replyFldClick() {
		writeBtn.setDisable(false);
	}
	
	//삭제버튼 클릭시
	public void deleteProc() {
		if (tableView.getSelectionModel().isEmpty()) {
			CommonService.msg("삭제할 데이터를 선택하세요.");
			return;
		}
		
		ButtonType btnType = CommonService.confirm("확인", "정말로 삭제하시겠습니까?");
		if (btnType == ButtonType.OK) {
			replyDTO replyDto = tableView.getSelectionModel().getSelectedItem();

			dao.myReplyDelete(replyDto);
			CommonService.msg("삭제되었습니다.");
			
			Collection<replyDTO> all = dao.replyViewAll(dto);
			observableList = FXCollections.observableArrayList(all);
			tableView.setItems(observableList);
		} else {
			CommonService.msg("취소했습니다.");
		}
	}
}
