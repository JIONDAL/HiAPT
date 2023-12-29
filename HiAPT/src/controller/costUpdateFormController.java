package controller;

import java.net.URL;
import java.util.ResourceBundle;

import DTO.CostDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.Opener;
import service.costUpdateFormService;

public class costUpdateFormController implements Initializable{
	@FXML
	ComboBox<String> yearCmb;
	@FXML
	ComboBox<String> monthCmb;
	@FXML
	ComboBox<String> complexCmb;
	@FXML
	ComboBox<String> unitCmb;
	@FXML
	TextField commonCostFld;
	@FXML
	TextField cleanCostFld;
	@FXML
	TextField secCostFld;
	@FXML
	TextField elvCostFld;
	@FXML
	TextField heatCostFld;
	@FXML
	TextField powerCostFld;
	@FXML
	TextField waterCostFld;
	@FXML
	TextField operCostFld;
	@FXML
	TextField totalCostFld;
	@FXML
	Button costUpdateBtn;
	@FXML
	Button totalCostBtn;
	
	private costUpdateFormService service;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		totalCostFld.setDisable(true);
		costUpdateBtn.setDisable(true);
		totalCostBtn.setDisable(true);
	}

	public costUpdateFormController() {
		service = new costUpdateFormService();
	}
	
	public void selectProc() {
		String year = yearCmb.getValue().substring(0, yearCmb.getValue().length()-1);
		String month = monthCmb.getValue().substring(0, monthCmb.getValue().length()-1);
		String complex = complexCmb.getValue().substring(0, complexCmb.getValue().length()-1);
		String unit = unitCmb.getValue().substring(0, unitCmb.getValue().length()-1);
		
		CostDTO cost = service.select(year, month, complex, unit);
		commonCostFld.setText(cost.getCommonCost());
		cleanCostFld.setText(cost.getCleanCost());
		secCostFld.setText(cost.getSecCost());
		elvCostFld.setText(cost.getElvCost());
		heatCostFld.setText(cost.getHeatCost());
		powerCostFld.setText(cost.getPowerCost());
		waterCostFld.setText(cost.getWaterCost());
		operCostFld.setText(cost.getOperCost());
		totalCostFld.setText(cost.getTotalCost());
		
		totalCostBtn.setDisable(false);
	}
	
	public void calProc() {
		int commonCost = Integer.parseInt(commonCostFld.getText());
		int cleanCost = Integer.parseInt(cleanCostFld.getText());
		int secCost = Integer.parseInt(secCostFld.getText());
		int elvCost = Integer.parseInt(elvCostFld.getText());
		int heatCost = Integer.parseInt(heatCostFld.getText());
		int powerCost = Integer.parseInt(powerCostFld.getText());
		int waterCost = Integer.parseInt(waterCostFld.getText());
		int operCost = Integer.parseInt(operCostFld.getText());
		int totalCost = commonCost + cleanCost + secCost + elvCost + heatCost + powerCost + waterCost + operCost;

		totalCostFld.setText(Integer.toString(totalCost));
		costUpdateBtn.setDisable(false);
	}
	
	public void updateProc() {
		CostDTO cost = new CostDTO();
		cost.setCommonCost(commonCostFld.getText());
		cost.setCleanCost(cleanCostFld.getText());
		cost.setSecCost(secCostFld.getText());
		cost.setElvCost(elvCostFld.getText());
		cost.setHeatCost(heatCostFld.getText());
		cost.setPowerCost(powerCostFld.getText());
		cost.setWaterCost(waterCostFld.getText());
		cost.setOperCost(operCostFld.getText());
		cost.setTotalCost(totalCostFld.getText());
		
		String complex = complexCmb.getValue().substring(0, complexCmb.getValue().length()-1);
		String unit = unitCmb.getValue().substring(0, unitCmb.getValue().length()-1);
		String id = complex + "-" + unit;
		cost.setId(id);

		String year = yearCmb.getValue().substring(0, yearCmb.getValue().length()-1);
		cost.setYear(year);
		
		String month = monthCmb.getValue().substring(0, monthCmb.getValue().length()-1);
		cost.setMonth(month);
		
		service.update(cost);
	}
	
	public void cancelProc() {
		Opener.costManageStageClose();
	}


}
