package login;

public class Login {
	private static String id;
	private String pw;
	private String name;
	private String phone;
	private String carNum;

	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public static String getId() {
		return id;
	}
	public static void setId(String id) {
		Login.id = id;
	}
}
