package login;

import javafx.beans.property.SimpleStringProperty;

public class costMonth {
	private SimpleStringProperty details;
	private SimpleStringProperty costs;

	public costMonth(String details, String costs) {
		// TODO Auto-generated constructor stub
		super();
		this.details = new SimpleStringProperty(details);
		this.costs = new SimpleStringProperty(costs);
	}

	public String getDetails() {
		return details.get();
	}

	public void setDetails(String details) {
		this.details = new SimpleStringProperty(details);
	}

	public String getCosts() {
		return costs.get();
	}

	public void setCosts(String costs) {
		this.costs = new SimpleStringProperty(costs);
	}
}
