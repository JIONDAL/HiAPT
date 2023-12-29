package controller;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import DAO.costDAO;
import DTO.CostDTO;
import DTO.costMonth;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.CommonService;
import main.Opener;

public class costViewForm2Controller implements Initializable{
	Opener opener = new Opener();
	costDAO costDao = new costDAO();
	
	Stage costViewStage;
	public void setCostViewStage(Stage costViewStage) {
		this.costViewStage = costViewStage;
	}
	
	//tableview에 데이터 넣기
	@FXML TableView<costMonth> tableView;
	@FXML TableColumn<costMonth, String> detailsCol;
	@FXML TableColumn<costMonth, String> costsCol;
	ObservableList<costMonth> observableList;
	
	@FXML RadioButton monthly;
	@FXML Label moon; //*월달 관리비 조회(상단 라벨)
	@FXML Label moons; //*월달 납부하실 관리비는(하단 라벨)
	@FXML Label total;
	
	//이번달 관리비 조회
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CostDTO costDto = costDao.selectMonth();
		if(costDto == null) {
			CommonService.msg("데이터가 존재하지 않습니다.");
		}else {
			observableList = FXCollections.observableArrayList();
			
			detailsCol.setCellValueFactory(new PropertyValueFactory<>("details"));
			costsCol.setCellValueFactory(new PropertyValueFactory<>("costs"));
			
			observableList.add(new costMonth("일반관리비", costDto.getCommonCost()));
			observableList.add(new costMonth("청소비", costDto.getCleanCost())); 
			observableList.add(new costMonth("경비비", costDto.getSecCost())); 
			observableList.add(new costMonth("승강기 유지비", costDto.getElvCost())); 
			observableList.add(new costMonth("난방비", costDto.getHeatCost())); 
			observableList.add(new costMonth("전기료", costDto.getPowerCost())); 
			observableList.add(new costMonth("수도료", costDto.getWaterCost())); 
			observableList.add(new costMonth("입주자 운영비", costDto.getOperCost())); 
			
			tableView.setItems(observableList);
			
			tableView.setFixedCellSize(33);//행 높이
			detailsCol.setStyle("-fx-pref-height: 70px;");//헤더 높이
			detailsCol.setStyle("-fx-alignment: CENTER;");//
			costsCol.setStyle("-fx-pref-height: 70px;");
			costsCol.setStyle("-fx-alignment: CENTER-RIGHT;");
			moon.setText(costDto.getMonth());
			moons.setText(costDto.getMonth());
			total.setText(costDto.getTotalCost());
			
		}
	}
	
	//월별 관리비 조회 OR 이번달 관리비 조회 선택하고 조회 버튼 눌렀을 때
	public void search() {
		if(monthly.isSelected()) {//월별 관리비 조회로 이동
			opener.costViewFormOpen();
			costViewStage.close();
		}else {
			CommonService.msg("현재 페이지가 '이번달 관리비 조회' 페이지 입니다.");
		}
	}
	
	//바로 납부 버튼 클릭시
	public void payFormOpen() {
		opener.costPayFormOpen();
	}
}
