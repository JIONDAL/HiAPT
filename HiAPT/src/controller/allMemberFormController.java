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
import javafx.scene.Parent;
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


public class allMemberFormController implements Initializable {
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
		  Collection<MemberDTO> all = dao.selectAll();
		  
//		  ArrayList<User> list = new ArrayList<>();
//		  list.add(new User("123","123","123","123","123"));
		  
//		  observableList.add();
//		  tableView.getItems().add(new User("2","2","2","2","2") );
//		  int cnt = list.size();
//		  for(int i=0; i < cnt; i++) {
//			  observableList.addAll(new MemberDTO(list[i]));
//		  }
		  
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

}
