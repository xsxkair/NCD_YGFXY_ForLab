package com.org.xsx.UI.DeviceTestPage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.org.xsx.beans.DataPointUI;
import com.org.xsx.beans.DeviceInfoBean;
import com.org.xsx.beans.TestDataBean;
import com.org.xsx.beans.TestDataItem;
import com.org.xsx.beans.TesterBean;
import com.org.xsx.dao.TesterDao;
import com.org.xsx.dao.TestDataDao;
import com.org.xsx.tools.ClientSocket;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DeviceTestPage {
	
	private DeviceInfoBean S_DeviceInfo = null;
	private SimpleObjectProperty<TesterBean> S_TesterBean = null;
	private int GB_TestCount = 0;
	private int GB_CurrentCount = 0;
	private int GB_TestRelay = 0;
	private SimpleIntegerProperty GB_TestStatus = null;
	private ObservableList<TestDataItem> GB_TestData;
	
	private int ShowSeriesIndex = 0;					//当前显示曲线的索引
	
	private Tab S_Tab = null;
	
	@FXML
	VBox GB_LoginPane;
	
	@FXML
	TextField GB_UserNameTextField;
	
	@FXML
	TextField GB_UserPassTextField;
	
	@FXML
	LineChart<Number, Number> GB_Chart;
	
	@FXML
	TextField GB_TestCountFiled;
	
	@FXML
	TextField GB_TestRelayFiled;
	
	@FXML
	TableView<TestDataItem> GB_TableView;
	@FXML
	TableColumn<TestDataItem, String> GB_TableColumn0;
	@FXML
	TableColumn<TestDataItem, Double> GB_TableColumn1;
	@FXML
	TableColumn<TestDataItem, Double> GB_TableColumn2;
	
	@FXML
	TextArea GB_LogTextArea;
	
	String LastSaveDirectory = null;
	
	public DeviceTestPage(DeviceInfoBean deviceInfo){
		S_DeviceInfo = deviceInfo;
	}
	
	public void Init(){
		this.UI_Init();
		this.Data_Init();
		Fun_Init();
		this.S_TesterBean.set(null);
		
		TestThread t = new TestThread();
		Thread thread = new Thread(t);  
		thread.start(); 
	}
	
	private void UI_Init(){
		AnchorPane root = null;
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("DeviceTestPage_FXML.fxml"));
        InputStream in = this.getClass().getResourceAsStream("DeviceTestPage_FXML.fxml");
        loader.setController(this);
        try {
        	root = loader.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
		GB_TableColumn0.setCellValueFactory(new PropertyValueFactory<TestDataItem, String>("my_desc"));
		GB_TableColumn0.setCellFactory(TextFieldTableCell.forTableColumn());
		
        GB_TableColumn1.setCellValueFactory(new PropertyValueFactory<TestDataItem, Double>("my_T_C_bili"));
        
        GB_TableColumn2.setCellValueFactory(new PropertyValueFactory<TestDataItem, Double>("my_T_TC_bili"));
        
        S_Tab = new Tab(S_DeviceInfo.getDeviceid()+" - 无");
        S_Tab.setContent(root);
        S_Tab.setClosable(false);
	}
	
	private void Data_Init(){
		
		S_TesterBean = new SimpleObjectProperty<>();
		GB_TestStatus = new SimpleIntegerProperty(0);
		
		GB_TestData = FXCollections.observableArrayList();
	}
	
	private void Fun_Init(){
		
		S_TesterBean.addListener(new ChangeListener<TesterBean>() {

			@Override
			public void changed(ObservableValue<? extends TesterBean> observable, TesterBean oldValue,
					TesterBean newValue) {
				
				if(newValue != null){
					GB_LoginPane.setVisible(false);
					S_Tab.setText(S_DeviceInfo.getDeviceid()+" - "+S_TesterBean.get().getUsername());
				}
				else{
					GB_LoginPane.setVisible(true);
					S_Tab.setText(S_DeviceInfo.getDeviceid()+" - 无");
				}	
			}
		});
		
		GB_TestStatus.addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				if(newValue.intValue() > 0){
					GB_TestRelayFiled.setDisable(true);
					GB_TestCountFiled.setDisable(true);
				}
				else{
					GB_TestRelayFiled.setDisable(false);
					GB_TestCountFiled.setDisable(false);
				}
			}
		});
		
		GB_TestCountFiled.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				Integer temp = null;
				
				try {
					temp = Integer.valueOf(newValue);
				} catch (NumberFormatException e) {
					// TODO: handle exception
					temp = null;
				}
				
				GB_TestData.clear();
				GB_TestCount = 0;
				
				if(temp != null){
					for (int i=0; i<temp.intValue(); i++) {
						GB_TestData.add(new TestDataItem(i+"/"+temp.intValue(), S_DeviceInfo, S_TesterBean.get()));
					}
					
					GB_TestCount = temp.intValue();
				}
			}
		});
		
		GB_TestRelayFiled.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				Integer temp = null;
				
				try {
					temp = Integer.valueOf(newValue);
				} catch (NumberFormatException e) {
					// TODO: handle exception
					temp = null;
				}
				GB_TestRelay = 0;
				
				if(temp != null){
					GB_TestRelay = temp.intValue();
				}
			}
		});
		
		GB_TestData.addListener(new ListChangeListener<TestDataItem>() {

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends TestDataItem> c) {
				// TODO Auto-generated method stub
				while(c.next()){
					for (TestDataItem remitem : c.getRemoved()) {
						GB_Chart.getData().remove(remitem.getMy_seires());
						GB_TableView.getItems().remove(remitem);
                   }
					
					for (TestDataItem additem : c.getAddedSubList()) {
						GB_Chart.getData().add(additem.getMy_seires());
						GB_TableView.getItems().add(additem);
                   }
				}
			}
		});
	}
	
	public Tab GetDeviceTabPane(){
		return S_Tab;
	}
	
	@FXML
	public void GB_LoginAction(ActionEvent e){
		TesterBean tempuser =  TesterDao.GetTester(GB_UserNameTextField.getText(), GB_UserPassTextField.getText());

		if(tempuser != null){
			S_TesterBean.set(tempuser);
			GB_UserNameTextField.setText(null);
			GB_UserPassTextField.setText(null);
			GB_LogTextArea.clear();
			GB_LogTextArea.appendText("欢迎你："+tempuser.getUsername()+"\n");
		}
		else {
			ButtonType loginButtonType = new ButtonType("确定", ButtonData.OK_DONE);
			Dialog<String> dialog = new Dialog<>();
			dialog.getDialogPane().setContentText("登陆失败！");
			dialog.getDialogPane().getButtonTypes().add(loginButtonType);
			dialog.showAndWait();
		}
	}
	
	@FXML
	public void GB_LoginKeyEvent(KeyEvent e){
		if(e.getCode() == KeyCode.ENTER){
			TesterBean tempuser =  TesterDao.GetTester(GB_UserNameTextField.getText(), GB_UserPassTextField.getText());

			if(tempuser != null){
				S_TesterBean.set(tempuser);
				GB_UserNameTextField.setText(null);
				GB_UserPassTextField.setText(null);
				GB_LogTextArea.clear();
				GB_LogTextArea.appendText("欢迎你："+tempuser.getUsername()+"\n");
			}
			else {
				ButtonType loginButtonType = new ButtonType("确定", ButtonData.OK_DONE);
				Dialog<String> dialog = new Dialog<>();
				dialog.getDialogPane().setContentText("登陆失败！");
				dialog.getDialogPane().getButtonTypes().add(loginButtonType);
				dialog.showAndWait();
			}
        }
	}
	
	@FXML
	public void GB_ExitHandle(ActionEvent e){
		GB_TestCountFiled.setText(null);
		S_TesterBean.set(null);
	}
	
	@FXML
	public void GB_SelectSeriesAction(MouseEvent e){
		MouseButton button = e.getButton();
		if(button.equals(MouseButton.SECONDARY)){
			ShowSeriesIndex = 0;
			 for (TestDataItem testDataItem : GB_TestData) {
				 testDataItem.ShowSeries();
			}
		}
		else if(button.equals(MouseButton.MIDDLE)){
			ShowSeriesIndex++;
			if(ShowSeriesIndex >= GB_TestData.size())
				ShowSeriesIndex = 0;
			for(int i=0; i< GB_TestData.size(); i++){
				if(i != ShowSeriesIndex)
					GB_TestData.get(i).HideSeries();
				else
					GB_TestData.get(i).ShowSeries();
			}
		}
	}
	
	@FXML
	public void GB_StartTestAction(ActionEvent e){
		if(GB_TestStatus.get() == 0){
			Integer temp = null;
			
			try {
				temp = Integer.valueOf(GB_TestCountFiled.getText());
			} catch (Exception e2) {
				// TODO: handle exception
				temp = null;
			}

			GB_TestData.clear();
			GB_TestCount = 0;
			
			if(temp != null){
				for (int i=0; i<temp.intValue(); i++) {
					GB_TestData.add(new TestDataItem(i+"/"+temp.intValue(), S_DeviceInfo, S_TesterBean.get()));
				}
				
				GB_TestCount = temp.intValue();
			}
			
			GB_TestStatus.set(1);
			GB_CurrentCount = 0;
		}
		else{
			ButtonType loginButtonType = new ButtonType("确定", ButtonData.OK_DONE);
			Dialog<String> dialog = new Dialog<>();
			dialog.getDialogPane().setContentText("正在测试，请等待！");
			dialog.getDialogPane().getButtonTypes().add(loginButtonType);
			dialog.showAndWait();
		}
	}
	
	@FXML
	public void GB_SaveDataAction(ActionEvent e){
		FileChooser fileChooser = new FileChooser();
		 fileChooser.setTitle("保存文件");
		 
		 if(LastSaveDirectory != null){
			 fileChooser.setInitialFileName(LastSaveDirectory);
		 	fileChooser.setInitialDirectory(new File(LastSaveDirectory.substring(0, LastSaveDirectory.lastIndexOf("\\"))));
		 }
		 fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Excel Files", "*.xlsx"));
		 File selectedFile = fileChooser.showSaveDialog(null);
		 
		 if(selectedFile != null){ 
			 SaveData_Fun(selectedFile.getPath());
		 }
	}
	
	public void SaveData_Fun(String filepath){

		ButtonType loginButtonType = new ButtonType("确定", ButtonData.OK_DONE);
		Dialog<String> dialog = new Dialog<>();
		
		dialog.getDialogPane().getButtonTypes().add(loginButtonType);
		
		
		FileInputStream myfileinput = null;
		try {
			myfileinput = new FileInputStream(filepath);	
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block

		}
		
		XSSFWorkbook wb = null;
		if(myfileinput != null){
			try {
				wb = new XSSFWorkbook(myfileinput);
			} catch (IOException e1) {
					// TODO Auto-generated catch block
				dialog.getDialogPane().setContentText("文件打开错误，请重试！");
				dialog.showAndWait();
				return;
			}
		}
		else{
			wb = new XSSFWorkbook();
		}

		XSSFSheet mysheet = null;
		
		if(wb.getNumberOfSheets() == 0){
			mysheet = wb.createSheet(S_TesterBean.get().getUsername());
		}
		else {
			mysheet = wb.getSheetAt(wb.getNumberOfSheets()-1);
		}
		
		XSSFRow myrow0 = mysheet.getRow(0);
		if(myrow0 == null)
			myrow0 = mysheet.createRow(0);
		int rownum = myrow0.getLastCellNum();
		if(rownum == -1)
			rownum += 1;
		
		for (int i = 0; i < GB_TestData.size(); i++) {
			//第0行
			XSSFRow myrow4 = mysheet.getRow(0);
			if(myrow4 == null)
				myrow4 = mysheet.createRow(0);
			
			XSSFCell Cell0 = myrow4.createCell(i+rownum);
			Cell0.setCellType(HSSFCell.CELL_TYPE_STRING);
			Cell0.setCellValue(GB_TestData.get(i).my_descProperty().get());
			
			//第一行
			XSSFRow myrow1 = mysheet.getRow(1);
			if(myrow1 == null)
				myrow1 = mysheet.createRow(1);
			
			XSSFCell Cell1 = myrow1.createCell(i+rownum);
			Cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
			Cell1.setCellValue(GB_TestData.get(i).getMy_seires().getName());

			//第二行
			XSSFRow myrow2 = mysheet.getRow(2);
			if(myrow2 == null)
				myrow2 = mysheet.createRow(2);
			
			XSSFCell Cell2 = myrow2.createCell(i+rownum);
			Cell2.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			Cell2.setCellValue(GB_TestData.get(i).my_T_C_biliProperty().doubleValue());
			
			//第三行
			XSSFRow myrow3 = mysheet.getRow(3);
			if(myrow3 == null)
				myrow3 = mysheet.createRow(3);
			
			XSSFCell Cell3 = myrow3.createCell(i+rownum);
			Cell3.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			Cell3.setCellValue(GB_TestData.get(i).my_T_TC_biliProperty().doubleValue());
			
			for (int j = 0; j < GB_TestData.get(i).getMy_seires().getData().size(); j++) {
				XSSFRow temprow = mysheet.getRow(j+4);
				if(temprow == null)
					temprow = mysheet.createRow(j+4);
				
				XSSFCell tempcell = temprow.createCell(i+rownum);
				tempcell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				tempcell.setCellValue(GB_TestData.get(i).getMy_seires().getData().get(j).getYValue().doubleValue());
			}
		}

		FileOutputStream out;
		try {
			out = new FileOutputStream(filepath);
			try {
				wb.write(out);
				out.flush();
				out.close();
				LastSaveDirectory = filepath;
				
				dialog.getDialogPane().setContentText("保存成功！");
				dialog.showAndWait();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				dialog.getDialogPane().setContentText("保存失败！");
				dialog.showAndWait();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			dialog.getDialogPane().setContentText("保存失败！");
			dialog.showAndWait();
		}finally {
			try {
				wb.close();
				myfileinput.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	class TestThread implements Runnable{

		@Override
		public void run() {
			String str;
			int retrycount = 0;
			JSONObject  jsonarray;
			Map typeMap = new HashMap();
			typeMap.put("data", int[].class);
        	typeMap.put("status", int.class);
        	ArrayList<Integer> tempdata = new ArrayList<>();
			
			while(true){
					
				//启动测试
				if(GB_TestStatus.getValue().intValue() == 1){
					str = ClientSocket.ComWithServer(S_DeviceInfo.getIp(), "Start Test99");
					if(str != null){

						if(str.equals("OK")){
							GB_TestData.get(GB_CurrentCount).UpDateTestDate();
							GB_LogTextArea.appendText("启动测试成功\n");
							retrycount = 0;
							GB_TestStatus.set(2);
							tempdata.clear();
						}
						else if(str.equals("Startted")){
							GB_LogTextArea.appendText("设备忙，测试取消\n");
							Platform.runLater(() -> {
								GB_TestStatus.set(0);
								GB_TestCountFiled.setText(null);
							});
						}
					}
					else{
						GB_LogTextArea.appendText("无响应-----"+retrycount+"\n");
						retrycount++;
						if(retrycount >= 10){
							Platform.runLater(() -> {
								GB_TestStatus.set(0);
								GB_TestCountFiled.setText(null);
							});
						}
					}
				}
				//读取测试曲线数据
				else if(GB_TestStatus.getValue().intValue() == 2){
					str = ClientSocket.ComWithServer(S_DeviceInfo.getIp(), "Read Test Data99");
					if(str != null){
						jsonarray = JSONObject.fromObject(str);
						Map output1 = (Map)JSONObject.toBean(jsonarray, Map.class, typeMap);

						List<Integer> data = (List<Integer>) output1.get("data");
						int status = (int) output1.get("status");

						tempdata.addAll(data);
						Platform.runLater(() -> {
							int serieldatasize;
							Series<Number, Number> mySeries = GB_TestData.get(GB_CurrentCount).getMy_seires();
							for (int i=0; i<data.size(); i++) {
								if(data.get(i) == 0xffff){
									mySeries.getData().clear();
									tempdata.clear();
								}
								else{
									serieldatasize = mySeries.getData().size();
									Data datap = new Data<Number, Number>((serieldatasize), data.get(i));
									datap.setNode(new DataPointUI());
									mySeries.getData().add(datap);
								}
							}
						});
						if(status == 0){
							JSONArray jsonList = JSONArray.fromObject( tempdata );  
							GB_TestData.get(GB_CurrentCount).getMy_TestDataBean().setTestdata(jsonList.toString());
							TestDataDao.SaveTestData(GB_TestData.get(GB_CurrentCount).getMy_TestDataBean());
							
							GB_CurrentCount++;

							if(GB_TestCount > GB_CurrentCount){

								GB_TestStatus.set(1);
								try {
									Thread.sleep(GB_TestRelay*1000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							else{
								Platform.runLater(() -> {
									GB_TestStatus.set(0);
								});
							}
									
						}
					}
					else{
						GB_LogTextArea.appendText("无响应-----"+retrycount+"\n");
						retrycount++;
						if(retrycount >= 10){
							Platform.runLater(() -> {
								GB_TestStatus.set(0);
								GB_TestCountFiled.setText(null);
							});
						}
					}
				}
					
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		
	}
}
