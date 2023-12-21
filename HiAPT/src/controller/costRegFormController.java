package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import login.CommonService;
import login.CostDTO;
import login.CostRegFormService;
import login.Opener;

public class costRegFormController implements Initializable {
	@FXML
	private ComboBox<String> yearCmb;
	@FXML
	private ComboBox<String> monthCmb;
	@FXML
	private ComboBox<String> complexCmb;
	private ObservableList<String> complexs = FXCollections.observableArrayList("101동", "102동", "103동", "104동");
	@FXML
	private ComboBox<String> unitCmb;
	private ObservableList<String> unit101 = FXCollections.observableArrayList
			("101호", "102호", "201호", "202호", "301호", "302호", "401호", "402호", "501호", "502호");
	private ObservableList<String> unit102 = FXCollections.observableArrayList
			("101호", "102호", "103호", "201호", "202호", "203호", "301호", "302호", "303호", "401호", "402호", "403호", 
			"501호", "502호", "503호", "601호", "602호", "603호", "701호", "702호", "703호");
	private ObservableList<String> unit103 = FXCollections.observableArrayList("101호", "102호", "103호", "104호", "301호",
			"302호");
	
	@FXML
	private TextField commonCostFld;
	@FXML
	private TextField cleanCostFld;
	@FXML
	private TextField secCostFld;
	@FXML
	private TextField elvCostFld;
	@FXML
	private TextField heatCostFld;
	@FXML
	private TextField powerCostFld;
	@FXML
	private TextField waterCostFld;
	@FXML
	private TextField operCostFld;
	@FXML
	private TextField totalCostFld;
	@FXML
	private Button costRegBtn;
	
	//일반 관리비, 청소비, 경비비, 승강기 유지비, 난방비, 전기료, 수도료, 입주자 운영비
	private int commonCost;
	private int cleanCost;
	private int secCost;
	private int elvCost;
	private int heatCost;
	private int powerCost;
	private int waterCost;
	private int operCost;

	private int totalCost;

	private CostRegFormService service;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		service = new CostRegFormService();
		
		yearCmb.setValue("연도 선택");
		yearCmb.getItems().addAll("2026년", "2025년", "2024년", "2023년", "2022년", "2021년", "2020년", "2019년");
		monthCmb.setValue("월 선택");
		monthCmb.getItems().addAll("01월", "02월", "03월", "04월", "05월", "06월", "07월", "08월", "09월", "10월", "11월",
				"12월");
		
		complexCmb.setValue("동 선택");
		complexCmb.getItems().addAll("101동", "102동", "103동", "104동");

		unitCmb.setValue("호수 선택");

		commonCostFld.setText("0");
		cleanCostFld.setText("0");
		secCostFld.setText("0");
		elvCostFld.setText("0");
		heatCostFld.setText("0");
		powerCostFld.setText("0");
		waterCostFld.setText("0");
		operCostFld.setText("0");
		totalCostFld.setText("0");

		costRegBtn.setDisable(true);
		totalCostFld.setDisable(true);
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
		String year = yearCmb.getValue();
		String month = monthCmb.getValue();
		String complex = complexCmb.getValue();
		String unit = unitCmb.getValue();
		
		if (year.equals("연도 선택")) {
			CommonService.msg("연도를 선택하세요.");
		} else if(month.equals("월 선택")) {
			CommonService.msg("월을 선택하세요.");
		} else if(complex.equals("동 선택")) {
			CommonService.msg("동을 선택하세요.");
		} else if(unit.equals("호수 선택")) {
			CommonService.msg("호수를 선택하세요.");
		} else {
			complex = complex.substring(0,complex.length()-1);
			unit = unit.substring(0,unit.length()-1);
			year = year.substring(0, yearCmb.getValue().length() - 1);
			month = month.substring(0, monthCmb.getValue().length() - 1);
			service.idCheck(complex, unit, year, month);
		}
		
	}

	public void totalCostProc() {
		commonCost = Integer.parseInt(commonCostFld.getText());
		cleanCost = Integer.parseInt(cleanCostFld.getText());
		secCost = Integer.parseInt(secCostFld.getText());
		elvCost = Integer.parseInt(elvCostFld.getText());
		heatCost = Integer.parseInt(heatCostFld.getText());
		powerCost = Integer.parseInt(powerCostFld.getText());
		waterCost = Integer.parseInt(waterCostFld.getText());
		operCost = Integer.parseInt(operCostFld.getText());
		totalCost = commonCost + cleanCost + secCost + elvCost + heatCost + powerCost + waterCost + operCost;

		totalCostFld.setText(Integer.toString(totalCost));

		costRegBtn.setDisable(false);
	}

	public void costRegProc() {
		CostDTO costDto = new CostDTO();
		String complex = complexCmb.getValue();
		String unit = unitCmb.getValue();

		String id = complex.substring(0, complex.length() - 1) + "-" + unit.substring(0, complex.length() - 1);
		costDto.setId(id);
		costDto.setCommonCost(commonCostFld.getText());
		costDto.setCleanCost(cleanCostFld.getText());
		costDto.setSecCost(secCostFld.getText());
		costDto.setElvCost(elvCostFld.getText());
		costDto.setHeatCost(heatCostFld.getText());
		costDto.setPowerCost(powerCostFld.getText());
		costDto.setWaterCost(waterCostFld.getText());
		costDto.setOperCost(operCostFld.getText());
		costDto.setTotalCost(totalCostFld.getText());

		String year = yearCmb.getValue().substring(0,yearCmb.getValue().length()-1);
		String month = monthCmb.getValue().substring(0,monthCmb.getValue().length()-1);
		
		costDto.setYear(year);
		costDto.setMonth(month);
		
		service.costReg(costDto);
		
	}

	public void initProc() {
		costRegBtn.setDisable(true);

		commonCostFld.setText("0");
		cleanCostFld.setText("0");
		secCostFld.setText("0");
		elvCostFld.setText("0");
		heatCostFld.setText("0");
		powerCostFld.setText("0");
		waterCostFld.setText("0");
		operCostFld.setText("0");
		totalCostFld.setText("0");

	}
	
	public void cancelProc() {
		Opener.costManageStageClose();
	}
}
