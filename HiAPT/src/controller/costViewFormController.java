package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import DAO.costDAO;
import DTO.CostDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.CommonService;
import main.Opener;

public class costViewFormController implements Initializable {
	Opener opener = new Opener();
	costDAO costDao = new costDAO();
	
	Stage costViewStage;
	public void setCostViewStage(Stage costViewStage) {
		this.costViewStage = costViewStage;
	}
	
	@FXML
	RadioButton month;
	
	@FXML
	ComboBox<String> combo;
	//ObservableList<String> comboData;

	// tableview에 데이터 넣기
	@FXML
	TableView<CostDTO> tableView;
	@FXML
	TableColumn<CostDTO, String> monthCol;
	@FXML
	TableColumn<CostDTO, String> totalCostCol;
	ObservableList<CostDTO> observableList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//comboBox에 데이터 넣기 -> ObservableList 사용하거나 getItems.addAll() 메서드 사용
		//comboData = FXCollections.observableArrayList("2025", "2024", "2023", "2022");
		//combo.setItems(comboData);
		combo.getItems().addAll("2025", "2024", "2023", "2022");
		combo.setValue("년도 선택");
		combo.setStyle("-fx-font-size: 15px;");
		combo.setStyle("-fx-background-color: #fcf0d5;");
		combo.setStyle("-fx-border-color: #6b4418; -fx-border-width: 2px; -fx-border-radius: 30px; -fx-background-radius: 30px;");
		
		tableView.setFixedCellSize(33);// 행 높이
		monthCol.setStyle("-fx-pref-height: 70px;");// 헤더 높이
		monthCol.setStyle("-fx-alignment: CENTER;");//
		totalCostCol.setStyle("-fx-pref-height: 70px;");
		totalCostCol.setStyle("-fx-alignment: CENTER-RIGHT;");
		tableView.setStyle("-fx-font-size:14px;");
	}
	
	
	// 이번달 관리비 선택하고 조회 누르면 이번달 관리비창 열리게
	public void search() {
		if (month.isSelected()) {
			opener.costViewForm2Open();
			costViewStage.close();
		} else {
			CommonService.msg("현재 페이지가 '월별 관리비 조회' 페이지 입니다.");
		}
	}
	
	String year;
	@FXML
	private void useHandleChange(ActionEvent event) {
		year = combo.getSelectionModel().getSelectedItem();
		
		//테이블뷰에 월별 관리비 나타내기 
		observableList = FXCollections.observableArrayList();

		monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
		totalCostCol.setCellValueFactory(new PropertyValueFactory<>("totalCost"));

		Collection<CostDTO> list = costDao.selectMonthly(year);
		observableList.addAll(list);

		tableView.setItems(observableList);
	}
}
