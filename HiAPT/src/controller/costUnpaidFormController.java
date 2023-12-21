package controller;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import login.CommonService;
import login.CostDTO;
import login.Opener;
import login.costUnpaidFormService;

public class costUnpaidFormController implements Initializable {
	@FXML
	ComboBox<String> payCmb;
	@FXML
	ComboBox<String> yearCmb;
	@FXML
	ComboBox<String> monthCmb;
	@FXML
	private TableView<CostDTO> costUnpaidTableView;
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
	Button checkBtn;

	private costUnpaidFormService service;
	
	ObservableList<CostDTO> observableList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		service = new costUnpaidFormService();
		
		payCmb.setValue("미납");
		payCmb.getItems().addAll("전체", "미납", "납부", "확인요청");
		yearCmb.setValue("연도 선택");
		yearCmb.getItems().addAll("연도 선택", "2023년", "2022년", "2021년", "2020년", "2019년");

		monthCmb.setValue("월 선택");
		monthCmb.getItems().addAll("월 선택", "01월", "02월", "03월", "04월", "05월", "06월", "07월", "08월", "09월", "10월", "11월", "12월");
		
		checkBtn.setDisable(true);
	}

	public void selectProc() {
		observableList = FXCollections.observableArrayList();

		yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
		monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		costColumn.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
		payColumn.setCellValueFactory(new PropertyValueFactory<>("pay"));

		String pay = payCmb.getValue();
		String year = yearCmb.getValue();
		String month = monthCmb.getValue();
		
		Collection<CostDTO> list = service.select(pay, year, month);
		observableList.addAll(list);
		costUnpaidTableView.setItems(observableList);
		
		if(pay.equals("확인요청")) {
			checkBtn.setDisable(false);
		} else {
			checkBtn.setDisable(true);
		}
	}
	
	
	public void checkProc() {
		observableList = costUnpaidTableView.getSelectionModel().getSelectedItems();
		if(observableList.get(0) == null) {
			CommonService.msg("입금 확인하려는 행을 선택하세요.");
		} else {
				String id = observableList.get(0).getId();
				String year = observableList.get(0).getYear();
				String month = observableList.get(0).getMonth();
				service.check(id, year, month);
		}
	}
	
	public void cancelProc() {
		Opener.costManageStageClose();
	}

}
