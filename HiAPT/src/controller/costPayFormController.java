package controller;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.DefaultCellEditor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import login.CommonService;
import login.CostDTO;
import login.Login;
import login.Opener;
import login.costDAO;
import login.costMonth;
import login.costPayFormService;

public class costPayFormController implements Initializable {
	Stage costPayStage;
	Opener opener = new Opener();
	private costPayFormService service;
	private costDAO costDao = new costDAO();;

	@FXML
	ComboBox<String> yearCmb;
	@FXML
	ComboBox<String> monthCmb;
	
	@FXML
	TableView<costMonth> tableView;
	@FXML
	TableColumn<costMonth, String> detailsCol;
	@FXML
	TableColumn<costMonth, String> costsCol;
	
	@FXML //납부 기한
	Label deadlineLabel;
	@FXML //이번달 관리비
	Label monthCostLabel;
	@FXML //납부여부
	Label statusLabel;
	@FXML //미납금
	Label unpaidLabel;

	@FXML
	Button myPayBtn;
	@FXML
	Button bankPayBtn;
	
	ObservableList<costMonth> observableList;
	
	public void setCostPayStage(Stage costPayStage) {
		this.costPayStage = costPayStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		service = new costPayFormService();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
		LocalDateTime now = LocalDateTime.now();

		yearCmb.setValue(dtf.format(now).substring(0, 4));
		yearCmb.getItems().addAll("2026", "2025", "2024", "2023", "2022", "2021", "2020", "2019");
		yearCmb.setStyle("-fx-font-size: 15px;");
		yearCmb.setStyle("-fx-background-color: #fcf0d5;");
		yearCmb.setStyle("-fx-border-color: #6b4418; -fx-border-width: 2px; -fx-border-radius: 30px; -fx-background-radius: 30px;");
		

		monthCmb.setValue(dtf.format(now).substring(5, 7));
		monthCmb.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
		monthCmb.setStyle("-fx-font-size: 15px;");
		monthCmb.setStyle("-fx-background-color: #fcf0d5;");
		monthCmb.setStyle("-fx-border-color: #6b4418; -fx-border-width: 2px; -fx-border-radius: 30px; -fx-background-radius: 30px;");
		
		detailsCol.setStyle("-fx-pref-height: 70px;");// 헤더 높이
		detailsCol.setStyle("-fx-alignment: CENTER;");
		costsCol.setStyle("-fx-pref-height: 70px;");
		costsCol.setStyle("-fx-alignment: CENTER-RIGHT;");
		//tableView.setStyle("-fx-font-size:14px;");
	}
	
	//콤보박스에서 년, 월 선택 후 조회 버튼 클릭시
	CostDTO cost;
	String year;
	String month;
	public void selectProc() {
		year = yearCmb.getValue();
		month = monthCmb.getValue();
		
		//내 관리비 조회
		cost = service.selectCosts(year, month, Login.getId());
		if (cost == null) {
			CommonService.msg("등록된 관리비가 없습니다. 관리자에 문의하세요.");
			myPayBtn.setDisable(true);
			bankPayBtn.setDisable(true);
		} else {
			observableList = FXCollections.observableArrayList();

			detailsCol.setCellValueFactory(new PropertyValueFactory<>("details"));
			costsCol.setCellValueFactory(new PropertyValueFactory<>("costs"));

			observableList.add(new costMonth("일반관리비", cost.getCommonCost()));
			observableList.add(new costMonth("청소비", cost.getCleanCost()));
			observableList.add(new costMonth("경비비", cost.getSecCost()));
			observableList.add(new costMonth("승강기 유지비", cost.getElvCost()));
			observableList.add(new costMonth("난방비", cost.getHeatCost()));
			observableList.add(new costMonth("전기료", cost.getPowerCost()));
			observableList.add(new costMonth("수도료", cost.getWaterCost()));
			observableList.add(new costMonth("입주자 운영비", cost.getOperCost()));

			tableView.setItems(observableList);
			
			//납부기한
			String deadLine = "";
			if(cost.getMonth().equals("12")) {
				deadLine += (Integer.parseInt(cost.getYear()) + 1) + "-";
				deadLine += "1";
			}else {
				deadLine += cost.getYear() + "-";
				deadLine += (Integer.parseInt(cost.getMonth()) + 1);
			}
			deadLine += "-10";
			deadlineLabel.setText(deadLine);
			
			//미납, 요청, 납부 등 상태 표시
			statusLabel.setText(cost.getPay());
			
			//이번달 관리비, 미납금 표시
			monthCostLabel.setText(cost.getTotalCost() + " 원");
			unpaidLabel.setText(service.unpaid() + "원");
			monthCost = Integer.parseInt(monthCostLabel.getText().replaceAll("[^0-9]", ""));
			unpaidTotal = Integer.parseInt(unpaidLabel.getText().replaceAll("[^0-9]", ""));
			
			if (statusLabel.getText().equals("미납")){
				myPayBtn.setDisable(false);
				bankPayBtn.setDisable(false);
			}else {
				myPayBtn.setDisable(true);
				bankPayBtn.setDisable(true);
			}
		}
	}
	
	int monthCost;
	int unpaidTotal;
	//myPay 결제하기 눌렀을 때
	public void myPayProc() {
		String myMoney = costDao.selectMoney(Login.getId());
		
		if(Integer.parseInt(myMoney) < monthCost) {
			CommonService.msg("출금 가능한 금액이 부족합니다.");
		} else {
			ButtonType btnType = CommonService.confirm("현재 잔액 : " + myMoney + "원", monthCost + "원을 납부하시겠습니까?");
			if(btnType == ButtonType.OK) {
				String balance = Integer.toString(Integer.parseInt(myMoney) - monthCost);
				service.setMymoney(balance);
				service.statusUpdate(Login.getId(), year, month);
				CommonService.msg(year + "년 " + month + "월 " + "관리비가 납부되었습니다.");
				statusLabel.setText("납부");
				unpaidLabel.setText(service.unpaid() + " 원");
				myPayBtn.setDisable(true);
				bankPayBtn.setDisable(true);
			}else {
				CommonService.msg("납부를 취소하였습니다.");
			}
		}
	}
	
	//무통장 입금하기 버튼 클릭시
	public void bankPayProc() {
		opener.accountFormOpen(yearCmb.getValue(), monthCmb.getValue());
		statusLabel.setText("확인 요청");
		myPayBtn.setDisable(true);
		bankPayBtn.setDisable(true);
	}
	
	public void cancelProc() {
		costPayStage.close();
	}
	
}
