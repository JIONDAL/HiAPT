package controller;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import DTO.CostDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.CommonService;
import main.Opener;
import service.costSelectFormService;

public class costSelectFormController implements Initializable {
	@FXML
	private TableView<CostDTO> costSelectTableView;
	@FXML
	private TableColumn<CostDTO, String> yearColumn;
	@FXML
	private TableColumn<CostDTO, String> monthColumn;
	@FXML
	private TableColumn<CostDTO, String> idColumn;
	@FXML
	private TableColumn<CostDTO, String> costColumn;
	@FXML
	private TableColumn<CostDTO, String> payColumn;
	@FXML
	private ComboBox<String> yearCmb;
	@FXML
	private ComboBox<String> monthCmb;
	@FXML
	private ComboBox<String> complexCmb;
	@FXML
	private ComboBox<String> unitCmb;
	private ObservableList<String> unit101 = FXCollections.observableArrayList("호수 선택", "101호", "102호", "201호", "202호", "301호",
			"302호", "401호", "402호", "501호", "502호");
	private ObservableList<String> unit102 = FXCollections.observableArrayList("호수 선택", "101호", "102호", "103호", "201호", "202호",
			"203호", "301호", "302호", "303호", "401호", "402호", "403호", "501호", "502호", "503호", "601호", "602호", "603호",
			"701호", "702호", "703호");
	private ObservableList<String> unit103 = FXCollections.observableArrayList("호수 선택", "101호", "102호", "103호", "104호", "301호",
			"302호");

	private costSelectFormService service;
	private CostDTO cost;

	ObservableList<CostDTO> observableList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		service = new costSelectFormService();
		
		yearCmb.setValue("연도 선택");
		yearCmb.getItems().addAll("연도 선택", "2023년", "2022년", "2021년", "2020년", "2019년");
		monthCmb.setValue("월 선택");
		monthCmb.getItems().addAll("월 선택", "01월", "02월", "03월", "04월", "05월", "06월", "07월", "08월", "09월", "10월",
				"11월", "12월");
		complexCmb.setValue("동 선택");
		complexCmb.getItems().addAll("동 선택", "101동", "102동", "103동", "104동");
		unitCmb.setValue("호수 선택");
	}

	public void selectUnitProc() {
		String complex = complexCmb.getValue();
		switch (complex) {
		case "101동":
			unitCmb.setItems(unit101);
			break;
		case "102동":
			unitCmb.setItems(unit102);
			break;
		case "103동":
			unitCmb.setItems(unit103);
			break;
		case "104동":
			unitCmb.setItems(unit103);
			break;
		}
	}

	public void selectProc() {
		observableList = FXCollections.observableArrayList();
		
		yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
		monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		costColumn.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
		payColumn.setCellValueFactory(new PropertyValueFactory<>("pay"));
		
		String year = yearCmb.getValue();
		String month = monthCmb.getValue();
		String complex = complexCmb.getValue();
		String unit = unitCmb.getValue();
		
		Collection<CostDTO> list = service.select(year, month, complex, unit);
		observableList.addAll(list);
		costSelectTableView.setItems(observableList);
	}
	
	public void deleteProc() {
		observableList = costSelectTableView.getSelectionModel().getSelectedItems();
		if(observableList.get(0) == null) {
			CommonService.msg("삭제할 행을 선택하세요.");
		} else {
			service.delete(observableList.get(0).getId(), observableList.get(0).getYear(), observableList.get(0).getMonth());
		}
	}
	
	public void updateProc() {
		observableList = costSelectTableView.getSelectionModel().getSelectedItems();
		if(observableList.get(0) == null) {
			CommonService.msg("수정할 행을 선택하세요.");
		} else {
			if(observableList.get(0).getPay().equals("납부")) {
				CommonService.msg("이미 납부된 건은 수정할 수 없습니다.");
			} else {
				Opener opener = new Opener();
				CostDTO cost = new CostDTO();
				cost.setYear(observableList.get(0).getYear());
				cost.setMonth(observableList.get(0).getMonth());
				cost.setId(observableList.get(0).getId());
				opener.costUpdateFormOpen(cost);
			}
		}
	}
}
