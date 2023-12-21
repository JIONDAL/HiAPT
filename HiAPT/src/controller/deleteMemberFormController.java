package controller;

import java.net.URL;
import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import login.CommonService;
import login.MemberDAO;
import login.MemberDTO;


public class deleteMemberFormController implements Initializable {
	  @FXML
	  TableView<MemberDTO> tableView;
	  @FXML
	  TableColumn<MemberDTO, String> idColumn;
	  @FXML
	  TableColumn<MemberDTO, String> nameColumn;
	  @FXML
	  TableColumn<MemberDTO, String> phoneColumn;
	  @FXML
	  TableColumn<MemberDTO, String> carColumn;
	  @FXML
	  TableColumn<MemberDTO, String> statusColumn;
	  
	  
	  ObservableList<MemberDTO> observableList;
	  private MemberDAO dao = new MemberDAO();
	  
	  @Override
	  public void initialize(URL location, ResourceBundle resources) {
		  observableList = FXCollections.observableArrayList();
		  
		  idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		  nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		  phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
		  carColumn.setCellValueFactory(new PropertyValueFactory<>("carNum"));
		  statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
		  
		  
//		  MemberDAO dao = new MemberDAO();
		  Collection<MemberDTO> all = dao.selectAll();
		  
		  observableList.addAll(all);
		  tableView.setItems(observableList);
		  
		  }
	  
	  @FXML Button checkBtn;
	  @FXML TextField idFld;
	  public void checkProc(ActionEvent event) {
		  MemberDAO dao = new MemberDAO();
		  Collection<MemberDTO> check = dao.selectMember(idFld.getText());
		  if(idFld.getText().isEmpty()) {
			  Collection<MemberDTO> all = dao.selectAll();
			  observableList = FXCollections.observableArrayList(all);
			  tableView.setItems(observableList);
			  
//			  CommonService.msg("검색할 아이디를 입력하세요.");
		  }else if(check.isEmpty()){
			  CommonService.msg("일치하는 아이디가 없습니다.");
			  idFld.clear();
	  	  }else {
			  observableList = FXCollections.observableArrayList(check);
			  tableView.setItems(observableList);  
		  }  
	  }
	  
	  
	  @FXML Button deleteBtn;
	  public void deleteProc(ActionEvent event) { 
		  String id = idFld.getText();
		  
		  if(tableView.getSelectionModel().isEmpty()) {
				CommonService.msg("삭제할 회원을 선택하세요.");
				return;
			}
		  
//		  MemberDTO check = dao.selectId(idFld.getText());
		  
//		  if(check != null) {
			  Alert alert = new Alert(AlertType.CONFIRMATION);
			  alert.setHeaderText("알림");
			  alert.setContentText("정말 삭제하시겠습니까?");
			  
			  Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
					 String deleteId = tableView.getSelectionModel().getSelectedItem().getId();
					 dao.delete(deleteId);
					 CommonService.msg(deleteId + " 회원 삭제되었습니다.");
				}else{
				     CommonService.msg("취소하셨습니다.");
				}
				
				Collection<MemberDTO> all = dao.selectAll();
				observableList = FXCollections.observableArrayList(all);
				tableView.setItems(observableList);
				
//		  }else {
//			  CommonService.msg("ID를 조회하세요.");
//		  }
	  }
}
