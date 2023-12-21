package controller;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import login.CommonService;
import login.MemberDAO;
import login.MemberDTO;
import login.costDAO;


public class DisMemberFormController implements Initializable {
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
	  
	  @Override
	  public void initialize(URL location, ResourceBundle resources) {
		  observableList = FXCollections.observableArrayList();
		  
		  idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		  nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		  phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
		  carColumn.setCellValueFactory(new PropertyValueFactory<>("carNum"));
		  statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
		  
		  
		  MemberDAO dao = new MemberDAO();
		  Collection<MemberDTO> dis = dao.selectDisagree();
		  
		  observableList.addAll(dis);
		  tableView.setItems(observableList);
		  
		  }
	  
	  @FXML Button checkBtn;
	  @FXML TextField idFld;
	  public void checkProc(ActionEvent event) {
		  MemberDAO dao = new MemberDAO();
		  Collection<MemberDTO> check = dao.selectMember(idFld.getText());
		  if(idFld.getText().isEmpty()) {
			  Collection<MemberDTO> dis = dao.selectDisagree();
			  observableList = FXCollections.observableArrayList(dis);
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
	  
	  @FXML Button agreeBtn;
	  public void agreeProc(ActionEvent event) {
		  MemberDAO dao = new MemberDAO();
//		  String id = idFld.getText();
//		  MemberDTO check = dao.selectId(idFld.getText());
		  
		  if(tableView.getSelectionModel().isEmpty()) {
				CommonService.msg("승인할 회원을 선택하세요.");
				return;
			}
		  
		  String d = "D";
		  String status = tableView.getSelectionModel().getSelectedItem().getStatus();
		  if(status.equals(d)) {
		  	String agreeId = tableView.getSelectionModel().getSelectedItem().getId();
			dao.updateAgree(agreeId);
			
			//관리자가 회원 승인시 무통장 입금 계좌 난수로 생성
			costDAO costDao = new costDAO();
			costDao.insertAccount(agreeId);
			
		  	CommonService.msg(agreeId + " 회원 승인되었습니다.");
		  }else {
			  CommonService.msg("승인된 회원입니다.");
		  }
		  
		  Collection<MemberDTO> dis = dao.selectDisagree();
		  observableList = FXCollections.observableArrayList(dis);
		  tableView.setItems(observableList);
	  }
	}