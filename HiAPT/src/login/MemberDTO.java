package login;

import javafx.beans.property.SimpleStringProperty;

public class MemberDTO {
	  private SimpleStringProperty id;
	  private SimpleStringProperty pw;
	  private SimpleStringProperty name;
	  private SimpleStringProperty phone;
	  private SimpleStringProperty carNum;
	  private SimpleStringProperty status;
	  
	  
	  public MemberDTO(String id, String pw, String name, String phone, String carNum, String status) {
	    super();
	    this.id = new SimpleStringProperty(id);
	    this.pw = new SimpleStringProperty(pw);
	    this.name = new SimpleStringProperty(name);
	    this.phone = new SimpleStringProperty(phone);
	    this.carNum = new SimpleStringProperty(carNum);
	    this.status = new SimpleStringProperty(status);
	  }
	  
	  public String getId() {
		  return id.get();
	  }
	  
	  public void setId(String id) {
		  this.id = new SimpleStringProperty(id);
	  }
	  
	  public String getPw() {
		  return pw.get();
	  }
	  
	  public void setPw(String pw) {
		  this.pw = new SimpleStringProperty(pw);
	  }
	  
	  public String getName() {
		  return name.get();
	  }
	  public void setName(String name) {
		  this.name = new SimpleStringProperty(name);
	  }
	  public String getPhone() {
		  return phone.get();
	  }
	  public void setPhone(String phone) {
		  this.phone = new SimpleStringProperty(phone);
	  }
	  
	  public String getCarNum() {
		  return carNum.get();
	  }
	  public void setCarNum(String carNum) {
		  this.carNum = new SimpleStringProperty(carNum);
	  }
	  
	  public String getStatus() {
		  return status.get();
	  }
	  public void setStatus(String status) {
		  this.status = new SimpleStringProperty(status);
	  }
	 
	}