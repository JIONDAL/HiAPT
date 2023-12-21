package login;

import javafx.beans.property.SimpleStringProperty;

public class costMonthly {
	private SimpleStringProperty month;
	private SimpleStringProperty costs;
	
	
	public costMonthly(String month, String costs) {
		super();
		this.month = new SimpleStringProperty(month);
		this.costs = new SimpleStringProperty(costs);
	}

	public String getMonth() {
		return month.get();
	}

	public void setMonth(String month) {
		this.month = new SimpleStringProperty(month);
	}

	public String getCosts() {
		return costs.get();
	}

	public void setCosts(String costs) {
		this.costs = new SimpleStringProperty(costs);
	}

}
