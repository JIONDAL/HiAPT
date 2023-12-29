package controller;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import DAO.MemberDAO;
import DTO.MemberDTO;
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
import service.CommonService;


public class agreeMemberTableController implements Initializable {
	  @FXML
	  private TableView<MemberDTO> tableView;
	  @FXML
	  private TableColumn<MemberDTO, String> idColumn;
	  @FXML
	  private TableColumn<MemberDTO, String> nameColumn;
	  @FXML
	  private TableColumn<MemberDTO, String> phoneColumn;
	  @FXML
	  private TableColumn<MemberDTO, String> carColumn;
	  @FXML
	  private TableColumn<MemberDTO, String> statusColumn;
	  
	  
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
		  Collection<MemberDTO> all = dao.selectDisagree();
		  
		  observableList.addAll(all);
		  tableView.setItems(observableList);
		  
		  }
	  
	  @FXML Button checkBtn;
	  @FXML TextField idFld;
	  public void checkProc(ActionEvent event) {
		  MemberDAO dao = new MemberDAO();
		  MemberDTO check = dao.selectId(idFld.getText());
		  if(idFld.getText().isEmpty()) {
			  CommonService.msg("검색할 아이디를 입력하세요.");
		  }else if(check == null){
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
		  Collection<MemberDTO> disMember = dao.selectDisagree();
		  observableList.addAll(disMember);
		  tableView.setItems(observableList);
	  }
	}