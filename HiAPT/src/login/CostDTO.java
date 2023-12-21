package login;

import javafx.beans.property.SimpleStringProperty;

public class CostDTO {
	private String id;
	private String details; ///추가
	private String CommonCost;
	private String CleanCost;
	private String secCost;
	private String elvCost;
	private String heatCost;
	private String powerCost;
	private String waterCost;
	private String operCost;
	private SimpleStringProperty totalCost;
	private String year;
	private SimpleStringProperty month;
	private String pay;
	
	public CostDTO(String totalCost, String month) {
		super();
	    this.totalCost = new SimpleStringProperty(totalCost);
	    this.month = new SimpleStringProperty(month);
	}
	
	public CostDTO() {
		
	}
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public String getCommonCost() {
		return CommonCost;
	}
	public void setCommonCost(String commonCost) {
		CommonCost = commonCost;
	}
	public String getCleanCost() {
		return CleanCost;
	}
	public void setCleanCost(String cleanCost) {
		CleanCost = cleanCost;
	}
	public String getSecCost() {
		return secCost;
	}
	public void setSecCost(String secCost) {
		this.secCost = secCost;
	}
	public String getElvCost() {
		return elvCost;
	}
	public void setElvCost(String elvCost) {
		this.elvCost = elvCost;
	}
	public String getHeatCost() {
		return heatCost;
	}
	public void setHeatCost(String heatCost) {
		this.heatCost = heatCost;
	}
	public String getPowerCost() {
		return powerCost;
	}
	public void setPowerCost(String powerCost) {
		this.powerCost = powerCost;
	}
	public String getWaterCost() {
		return waterCost;
	}
	public void setWaterCost(String waterCost) {
		this.waterCost = waterCost;
	}
	public String getOperCost() {
		return operCost;
	}
	public void setOperCost(String operCost) {
		this.operCost = operCost;
	}
	public String getTotalCost() {
		return totalCost.get();
	}
	public void setTotalCost(String totalCost) {
		this.totalCost = new SimpleStringProperty(totalCost);
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getMonth() {
		return month.get();
	}

	public void setMonth(String month) {
		this.month = new SimpleStringProperty(month);
	}
	
	public String getPay() {
		return pay;
	}
	
	public void setPay(String pay) {
		this.pay = pay;
	}
}
