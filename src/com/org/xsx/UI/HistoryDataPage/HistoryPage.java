package com.org.xsx.UI.HistoryDataPage;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.org.xsx.beans.HistorySelectedData;
import com.org.xsx.beans.HistoryTableItem;
import com.org.xsx.beans.TestDataBean;
import com.org.xsx.beans.TesterBean;
import com.org.xsx.dao.TestDataDao;
import com.org.xsx.dao.TesterDao;
import com.org.xsx.services.ReadHistoryDataService;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Pagination;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

public class HistoryPage {
	
	
	private Tab S_Tab = null;
	
	@FXML
	ComboBox<String> GB_UserListBox;
	
	@FXML
	ComboBox<String> GB_DeviceListBox;
	
	@FXML
	DatePicker GB_TestTimeFilterBox;
	
	@FXML
	StackPane GB_FreshPane;
	
	@FXML
	Region GB_Viel;
	
	@FXML
	ProgressIndicator GB_RefreshBar;
	
	@FXML
	Pagination GB_Pagination;
	
	@FXML
	TableView<HistoryTableItem> GB_Tableview;
	@FXML
	TableColumn<HistoryTableItem, Boolean> GB_TableColumn0;
	@FXML
	TableColumn<HistoryTableItem, Integer> GB_TableColumn1;
	@FXML
	TableColumn<HistoryTableItem, String> GB_TableColumn2;
	@FXML
	TableColumn<HistoryTableItem, String> GB_TableColumn3;
	@FXML
	TableColumn<HistoryTableItem, java.sql.Date> GB_TableColumn4;
	@FXML
	TableColumn<HistoryTableItem, java.sql.Time> GB_TableColumn5;
	@FXML
	TableColumn<HistoryTableItem, String> GB_TableColumn6;
	@FXML
	TableColumn<HistoryTableItem, String> GB_TableColumn7;
	@FXML
	TableColumn<HistoryTableItem, String> GB_TableColumn8;
	@FXML
	TableColumn<HistoryTableItem, Float> GB_TableColumn9;
	@FXML
	TableColumn<HistoryTableItem, Float> GB_TableColumn10;
	
	//更新数据任务
	private ReadHistoryDataService GB_ReadHistoryDataService = new ReadHistoryDataService();
	
	String LastSaveDirectory = null;

	public void Init(){
		this.UI_Init();
		this.Data_Init();
		this.Fun_Init();
		GetAllNum();
		InitFilterUI();
		
		StartReflushData();
	}
	
	private void UI_Init(){
		AnchorPane root = null;
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("HistoryPage_FXML.fxml"));
        InputStream in = this.getClass().getResourceAsStream("HistoryPage_FXML.fxml");
        loader.setController(this);
        try {
        	root = loader.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        GB_TableColumn0.setCellValueFactory(new PropertyValueFactory<HistoryTableItem, Boolean>("isselected"));
        GB_TableColumn0.setCellFactory(CheckBoxTableCell.forTableColumn(GB_TableColumn0));
        
        GB_TableColumn1.setCellValueFactory(new PropertyValueFactory<HistoryTableItem, Integer>("index"));
        GB_TableColumn1.setCellFactory(new TableColumnModel<HistoryTableItem, Integer>());
        
        GB_TableColumn2.setCellValueFactory(new PropertyValueFactory<HistoryTableItem, String>("deviceid"));
        GB_TableColumn2.setCellFactory(new TableColumnModel<HistoryTableItem, String>());
        
        GB_TableColumn3.setCellValueFactory(new PropertyValueFactory<HistoryTableItem, String>("deviceip"));
        GB_TableColumn3.setCellFactory(new TableColumnModel<HistoryTableItem, String>());
        
        GB_TableColumn4.setCellValueFactory(new PropertyValueFactory<HistoryTableItem, java.sql.Date>("testdate"));
        GB_TableColumn4.setCellFactory(new TableColumnModel<HistoryTableItem, java.sql.Date>());
        
        GB_TableColumn5.setCellValueFactory(new PropertyValueFactory<HistoryTableItem, java.sql.Time>("testtime"));
        GB_TableColumn5.setCellFactory(new TableColumnModel<HistoryTableItem, java.sql.Time>());
        
        GB_TableColumn6.setCellValueFactory(new PropertyValueFactory<HistoryTableItem, String>("testername"));
        GB_TableColumn6.setCellFactory(new TableColumnModel<HistoryTableItem, String>());
        
        GB_TableColumn7.setCellValueFactory(new PropertyValueFactory<HistoryTableItem, String>("testcount"));
        GB_TableColumn7.setCellFactory(new TableColumnModel<HistoryTableItem, String>());
        
        GB_TableColumn8.setCellValueFactory(new PropertyValueFactory<HistoryTableItem, String>("testdesc"));
        GB_TableColumn8.setCellFactory(new TableColumnModel<HistoryTableItem, String>());
        
        GB_TableColumn9.setCellValueFactory(new PropertyValueFactory<HistoryTableItem, Float>("t_c"));
        GB_TableColumn9.setCellFactory(new TableColumnModel<HistoryTableItem, Float>());
        
        GB_TableColumn10.setCellValueFactory(new PropertyValueFactory<HistoryTableItem, Float>("t_tc"));
        GB_TableColumn10.setCellFactory(new TableColumnModel<HistoryTableItem, Float>());
        
        GB_Tableview.itemsProperty().bind(GB_ReadHistoryDataService.valueProperty());
        
        GB_RefreshBar.progressProperty().bind(GB_ReadHistoryDataService.progressProperty());
        GB_FreshPane.visibleProperty().bind(GB_ReadHistoryDataService.runningProperty());
		
        S_Tab = new Tab("历史数据");
        S_Tab.setContent(root);
        S_Tab.setClosable(false);
	}
	
	private void Data_Init(){

	}
	
	private void Fun_Init(){
		
		GB_UserListBox.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				HistoryFilterData.GetInstance().setTestername(newValue);
				GetAllNum();
				
				StartReflushData();
			}
		});
		
		GB_DeviceListBox.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				HistoryFilterData.GetInstance().setDeviceid(newValue);
				
				GetAllNum();
				
				StartReflushData();
			}
		});
		
		GB_TestTimeFilterBox.valueProperty().addListener(new ChangeListener<LocalDate>() {

			@Override
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
					LocalDate newValue) {
				// TODO Auto-generated method stub
				java.sql.Date tempdate = null;
				try {
					tempdate = java.sql.Date.valueOf(GB_TestTimeFilterBox.getValue());
				} catch (Exception e2) {
					// TODO: handle exception
					tempdate = null;
				}
				
				
				HistoryFilterData.GetInstance().setTesttime(tempdate);
				
				GetAllNum();
				
				StartReflushData();
			}
		});
		
		GB_Pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				System.out.println(newValue);
				HistoryFilterData.GetInstance().setPageindex(newValue.intValue());
				StartReflushData();
			}
		});

		
		GB_ReadHistoryDataService.runningProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if(newValue){

				}
				else {
					ObservableList<HistoryTableItem> list = GB_Tableview.getItems();
					for (int i=0; i<list.size(); i++) {
						HistoryTableItem temp = list.get(i);
						
						temp.isselectedProperty().addListener(new ChangeListener<Boolean>() {

							@Override
							public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
									Boolean newValue) {
								// TODO Auto-generated method stub
								if(newValue)
									HistorySelectedData.GetInstance().getSelectedDataMap().put(temp.getTestdatabean().getId(), temp.getTestdatabean());
								else
									HistorySelectedData.GetInstance().getSelectedDataMap().remove(temp.getTestdatabean().getId());
							}
							
						});
					}
				}
			}
		});
	}
	
	public Tab GetDeviceTabPane(){
		return S_Tab;
	}
	
	private void InitFilterUI(){
		//读取已存在测试人
		List<TesterBean> testerlist = TesterDao.GetAllTester();
		
		GB_UserListBox.getItems().clear();
		GB_UserListBox.getItems().add("所有人");
		
		for (TesterBean str : testerlist) {
			GB_UserListBox.getItems().add(str.getUsername());
		}

		GB_UserListBox.setValue(null);
		
		//读取已存在设备
		List<String> devicelist = TestDataDao.GetAllDeviceId();
		
		GB_DeviceListBox.getItems().clear();
		GB_DeviceListBox.getItems().add("All");
		
		for (String str : devicelist) {
			GB_DeviceListBox.getItems().add(str);
		}

		GB_DeviceListBox.setValue(null);
		
		//测试时间
		GB_TestTimeFilterBox.setValue(null);
	}
	
	private void StartReflushData(){
		GB_ReadHistoryDataService.restart();
	}
	
	@FXML
	public void GB_ResetAction(ActionEvent e){
		
		InitFilterUI();
		GetAllNum();
		if(GB_Pagination.getCurrentPageIndex() != 0)
			GB_Pagination.setCurrentPageIndex(0);
		else
			StartReflushData();
	}
	
	@FXML
	public void GB_ReflushAction(ActionEvent e){
		HistorySelectedData.GetInstance().getSelectedDataMap().clear();
		GetAllNum();
		if(GB_Pagination.getCurrentPageIndex() != 0)
			GB_Pagination.setCurrentPageIndex(0);
		else
			StartReflushData();
	}
	
	@FXML
	public void GB_ExportReportAction(ActionEvent e){

		if(HistorySelectedData.GetInstance().getSelectedDataMap().size() > 0){
/*			FileChooser fileChooser = new FileChooser();
			 fileChooser.setTitle("保存文件");
			 
			 if(LastSaveDirectory != null){
				 fileChooser.setInitialFileName(LastSaveDirectory);
			 	fileChooser.setInitialDirectory(new File(LastSaveDirectory.substring(0, LastSaveDirectory.lastIndexOf("\\"))));
			 }
			 fileChooser.getExtensionFilters().addAll(
			         new ExtensionFilter("Excel Files", "*.pdf"));
			 File selectedFile = fileChooser.showSaveDialog(null);
			 
			 if(selectedFile != null){ 
				 ExportToPDF(selectedFile.getPath());
			 }*/
			ButtonType loginButtonType = new ButtonType("确定", ButtonData.OK_DONE);
			Dialog<String> dialog = new Dialog<>();
			
			dialog.getDialogPane().getButtonTypes().add(loginButtonType);
			
			dialog.getDialogPane().setContentText("研发中！");
			dialog.showAndWait();
		}
		else{
			ButtonType loginButtonType = new ButtonType("确定", ButtonData.OK_DONE);
			Dialog<String> dialog = new Dialog<>();
			
			dialog.getDialogPane().getButtonTypes().add(loginButtonType);
			
			dialog.getDialogPane().setContentText("请选择后再执行导出指令！");
			dialog.showAndWait();
		}
	}
	
	class TableColumnModel<T,S> implements Callback<TableColumn<T, S>, TableCell<T, S>> {
		
	    @Override
	    public TableCell<T, S> call(TableColumn<T, S> param) {
	    	
	    	TextFieldTableCell<T, S> cell = new TextFieldTableCell<>();
	    	
	    	
	    	Tooltip tooltip = new Tooltip();
	    	
	    	cell.setAlignment(Pos.CENTER);
	    	
	    	cell.setOnMouseEntered(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					TableRow<T> row = cell.getTableRow();
					
					if(row.getIndex() < GB_Tableview.getItems().size()){
						NumberAxis xAxis = new NumberAxis();
				    	NumberAxis yAxis = new NumberAxis();
				    	LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
				    	chart.setStyle("-fx-legend-visible:false");
				    	
				    	Series<Number, Number> series = new Series<>();

				        chart.getData().add(series);
				        
						JSONArray jsonArray1 = (JSONArray) JSONSerializer.toJSON(GB_Tableview.getItems().get(row.getIndex()).getTestdata());
				        if(jsonArray1 != null){
				        	
				        	List<Integer> output1 = (List<Integer>) JSONSerializer.toJava(jsonArray1);
				        	Integer t = GB_Tableview.getItems().get(row.getIndex()).getT_location();
					        Integer b = GB_Tableview.getItems().get(row.getIndex()).getB_location();
					        Integer c = GB_Tableview.getItems().get(row.getIndex()).getC_location();
					        
					        for(int i=0; i<output1.size(); i++){
					        	Data<Number, Number> data = new Data<Number, Number>(i, output1.get(i));
					        	StackPane stackPane = new StackPane();
					        	
					        	stackPane.setPrefSize(0, 0);
					        	
					        	if((t != null) && (i == t.intValue())){
					        		stackPane.setStyle("-fx-background-color:red");
					        		stackPane.setPrefSize(10, 10);
					        	}
					        	else if((b != null) && (i == b.intValue())){
					        		stackPane.setStyle("-fx-background-color:green");
					        		stackPane.setPrefSize(10, 10);
					        	}
					        	else if((c != null) && (i == c.intValue())){
					        		stackPane.setStyle("-fx-background-color:blue");
					        		stackPane.setPrefSize(10, 10);
					        	}
					        	
					        	data.setNode(stackPane);
					        	series.getData().add(data);
							}
				        }
				        
				        tooltip.setGraphic(chart);
				        Tooltip.install(cell, tooltip);
					}
					else
						Tooltip.uninstall(cell, tooltip);
				}
			});

	        return cell;
	    }
	}
	
	private void GetAllNum(){
		StringBuffer hql = new StringBuffer("select count(*) from TestDataBean as t");
		ArrayList<Object> parms = new ArrayList<>();
		
		String user = GB_UserListBox.getValue();
		if((user != "所有人") && (user != null)){
			hql.append(" AND t.testername=:parm"+parms.size());
			parms.add(user);
		}
		
		String deviceid = GB_DeviceListBox.getValue();
		if((deviceid != "All") && (deviceid != null)){
			hql.append(" AND t.deviceid=:parm"+parms.size());
			parms.add(deviceid);
		}
		
		java.sql.Date tempdate = null;
		try {
			tempdate = java.sql.Date.valueOf(GB_TestTimeFilterBox.getValue());
		} catch (Exception e2) {
			// TODO: handle exception
			tempdate = null;
		}
		if(tempdate != null){
			hql.append(" AND t.testdate=:parm"+parms.size());
			parms.add(tempdate);
		}
		
		String string = hql.toString().replaceFirst("AND", "WHERE");
		
		
		Long sLong = TestDataDao.GetDataNum(string, parms.toArray());
		
		HistoryFilterData.GetInstance().setDatatotalnum(sLong);

		if(sLong == 0)
			GB_Pagination.setPageCount(1);
		else{
			long num = sLong;
			int pagesize = HistoryFilterData.GetInstance().getPagesize();
			GB_Pagination.setPageCount((int) ((num%pagesize == 0)?(num/pagesize):(num/pagesize+1)));
		}
	}
	
	
	private void ExportToPDF(String path){
		System.out.println(path);
/*		
		Document document = new Document(); 
		BaseFont baseFont = null;

		try {
			PdfWriter.getInstance(document, new FileOutputStream(path));
			try {
				baseFont = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Font font = new Font(baseFont); 
			document.addTitle("张雄的测试");
			document.addAuthor("张雄");
			
			document.open();
			
			document.add(new Paragraph("第一页1111", font));
			document.newPage();
			
			
			Table table = new Table(2, 2);
			table.addCell("1");
			table.addCell("2");
			table.addCell("3");
			table.addCell("4");
			document.add(table);
			document.newPage();
			
			document.close();
			
		} catch (FileNotFoundException | DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
	}
}
