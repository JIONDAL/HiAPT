package controller;

import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import DAO.BoardDAO;
import DTO.AptDTO;
import DTO.BoardDTO;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.CommonService;
import main.Opener;
import service.viewFreeFormService;

public class viewFreeFormController implements Initializable {
	Opener opener = new Opener();
	BoardDAO dao = new BoardDAO();

	BoardDTO boardDto;
	public void setBoarDto(BoardDTO dto) {
		this.boardDto = dto;
	}

	Stage viewFreeStage;
	public void setViewFreeStage(Stage viewFreeStage) {
		this.viewFreeStage = viewFreeStage;
	}

	@FXML
	TextField titleFld;
	@FXML
	TextArea contentFld;
	@FXML
	Button changeBtn;
	@FXML
	Button deleteBtn;
	
	private viewFreeFormService service;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		service = new viewFreeFormService();
	}

	// 수정버튼
	public void updataProc() {
		if (boardDto.getTitle().equals(titleFld.getText()) 
				&& boardDto.getContent().equals(contentFld.getText())) {
			CommonService.alert("확인", "수정된 내용이 없습니다.");
		}else {
			
			ButtonType btnType = CommonService.confirm("확인", "수정하시겠습니까?");
			if (btnType == ButtonType.OK) {
				boardDto.setTitle(titleFld.getText());
				boardDto.setContent(contentFld.getText());
				int result = service.update(boardDto);
				if (result == 1) {
					viewFreeStage.close();
				}
				
			} else {
				CommonService.msg("취소했습니다.");
			}
			
		}
	}

	// 삭제버튼
	public void deleteProc() {
		ButtonType btnType = CommonService.confirm("확인", "정말 삭제하시겠습니까?");
		if (btnType == ButtonType.OK) {
			int result = service.delete(boardDto);
			if (result == 1) {
				viewFreeStage.close();
			}

		} else {
			CommonService.msg("취소했습니다.");
		}
	}

	// 닫기버튼
	public void closeProc() {
		viewFreeStage.close();
	}

	@FXML
	Label likesLabel;
	@FXML
	ImageView heart;
	
	//좋아요 누른 아이디를 hashSet에 저장
	private Set<String> clickedIds = new HashSet<>();

	// 좋아요 기능
	public void heartProc() {
		//hashSet에 아이디가 있으면 true 반환
		if (clickedIds.contains(AptDTO.getId())) {
			CommonService.msg("이미 좋아요한 글 입니다.");
			return;
		}

		clickedIds.add(AptDTO.getId());
		ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), heart);

		scaleTransition.setToX(2);
		scaleTransition.setToY(2);
		
        /*
         *더 간단하게 표현된 eventhandler 
		scaleTransition.setOnFinished(event -> {
			ScaleTransition reverseTransition = new ScaleTransition(Duration.seconds(0.5), heart);
			reverseTransition.setToX(1);
			reverseTransition.setToY(1);
			reverseTransition.play();
			
			int count = boardDto.getLikes();
			count++;
			
			likesLabel.setText(String.valueOf(count));
			dao.updateLikes(boardDto, count);
		});
		*/
		
		EventHandler<ActionEvent> event = e -> {
			ScaleTransition reverseTransition = new ScaleTransition(Duration.seconds(0.5), heart);
			reverseTransition.setToX(1);
			reverseTransition.setToY(1);
			reverseTransition.play();
			
			int count = boardDto.getLikes();
			count++;
			
			likesLabel.setText(String.valueOf(count));
			dao.updateLikes(boardDto, count);
		};
		
		scaleTransition.setOnFinished(event);

		scaleTransition.play();
	}

	// 댓글 보기
	public void replyProc() {
		opener.replyFormOpen(boardDto);
		viewFreeStage.close();
	}

}
