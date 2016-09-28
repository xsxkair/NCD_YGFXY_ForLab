package com.org.xsx.beans;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Optional;

import org.controlsfx.dialog.CommandLinksDialog;
import org.controlsfx.dialog.CommandLinksDialog.CommandLinksButtonType;

import com.org.xsx.dao.TestDataDao;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Path;

public class TestDataItem {

	private XYChart.Series<Number,Number> my_seires;
	
	private TestDataBean my_TestDataBean;
	
	private SimpleIntegerProperty my_T;
	private SimpleIntegerProperty my_B;
	private SimpleIntegerProperty my_C;
	private SimpleDoubleProperty my_T_C_bili;
	private SimpleDoubleProperty my_T_TC_bili;
	private SimpleStringProperty my_desc;
	
	public TestDataItem(String name, DeviceInfoBean deviceinfo, TesterBean tester){
		my_seires = new Series<Number, Number>();
		my_seires.setName(name);
		
		my_TestDataBean = new TestDataBean();
		my_TestDataBean.setTestcount(name);
		my_TestDataBean.setDeviceid(deviceinfo.getDeviceid());
		my_TestDataBean.setDeviceip(deviceinfo.getIp());
		my_TestDataBean.setTestername(tester.getUsername());
		
		my_T = new SimpleIntegerProperty();
		my_B = new SimpleIntegerProperty();
		my_C = new SimpleIntegerProperty();
		
		my_T_C_bili = new SimpleDoubleProperty(0);
		
		my_T_TC_bili = new SimpleDoubleProperty(0);
		
		my_desc = new SimpleStringProperty();
		
		Init();
	}
	
	private void Init(){
		ObservableList<Data<Number, Number>> tempData = FXCollections.observableArrayList();
		my_seires.setData(tempData);
		
		tempData.addListener(new ListChangeListener<Data<Number, Number>>() {

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Data<Number, Number>> c) {
				// TODO Auto-generated method stub
				while(c.next()){
					for (Data<Number, Number> data : c.getAddedSubList()) {

						  Tooltip.install(data.getNode(), new Tooltip("ÇúÏß: "+my_seires.getName()+"  \n( "+data.getXValue().intValue()+
								  " , "+data.getYValue().intValue()+" )"));
						  
						((DataPointUI)(data.getNode())).setOnMouseClicked(new EventHandler<MouseEvent>() {
							
							@Override
							public void handle(MouseEvent event) {
								// TODO Auto-generated method stub
								
								String comname = null;
								ArrayList<String> names = new ArrayList<>();
								ArrayList<CommandLinksButtonType> links = new ArrayList<>();
								
								names.add("B");
								names.add("C");
								names.add("T");
											
								for (int y=0; y < names.size(); y++ ) {
									links.add(new CommandLinksButtonType(names.get(y), true));
								}
										
								CommandLinksDialog dlg = new CommandLinksDialog(links);

								dlg.setTitle("ÇëÑ¡Ôñ²Ù×÷£º");    
								        
								Optional<ButtonType> result = dlg.showAndWait();
								if(result.isPresent()){	
									if(result.get().getButtonData() == ButtonData.OK_DONE)
										comname = result.get().getText();
									}
									if(comname != null){
										DataPointUI dataPointUI = null;
										if(comname.equals("B")){
											dataPointUI =  (DataPointUI) my_seires.getData().get(my_B.get()).getNode();//(DataPointUI) B_Point.get().getNode();
											if(dataPointUI != null)
												dataPointUI.SetMyStyle(DataPointStyleEnum.Normol);
											((DataPointUI)(data.getNode())).SetMyStyle(DataPointStyleEnum.B_Line);
											my_B.set(data.getXValue().intValue());
										}
										else if(comname.equals("C")){
											dataPointUI = (DataPointUI) my_seires.getData().get(my_C.get()).getNode();//(DataPointUI) C_Point.get().getNode();
											if(dataPointUI != null)
												dataPointUI.SetMyStyle(DataPointStyleEnum.Normol);
											((DataPointUI)(data.getNode())).SetMyStyle(DataPointStyleEnum.C_Line);
											my_C.set(data.getXValue().intValue());
										}
										else if(comname.equals("T")){
											dataPointUI = (DataPointUI) my_seires.getData().get(my_T.get()).getNode();//(DataPointUI) T_Point.get().getNode();
											if(dataPointUI != null)
												dataPointUI.SetMyStyle(DataPointStyleEnum.Normol);
											((DataPointUI)(data.getNode())).SetMyStyle(DataPointStyleEnum.T_Line);
											my_T.set(data.getXValue().intValue());
										}
									}
								}
							});
						}
					}
			}
		});
		
		my_B.addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				CalResult();
			}

		});
		
		my_C.addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				CalResult();
			}
		});
		
		my_T.addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				CalResult();
			}

		});
		
		my_desc.addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				my_desc.set(newValue);
				my_TestDataBean.setTestdesc(newValue);
				
				TestDataDao.SaveTestData(my_TestDataBean);
			}
		});
	}
	
	public XYChart.Series<Number, Number> getMy_seires() {
		return my_seires;
	}
	
	public void UpDateTestDate(){
		my_TestDataBean.setTestdate(new java.sql.Date(System.currentTimeMillis()));
		my_TestDataBean.setTesttime(new Time(System.currentTimeMillis()));
	}
	
	private void CalResult(){
		double b = 0;
		double c = 0;
		double t = 0;
		
		b = my_seires.getData().get(my_B.get()).getYValue().doubleValue();
		c = my_seires.getData().get(my_C.get()).getYValue().doubleValue();
		
		t = my_seires.getData().get(my_T.get()).getYValue().doubleValue();
		
		if((t<b)||(c<=b)){
			my_T_C_bili.setValue(0);
			my_T_TC_bili.setValue(0);
		}
		else{
			my_T_C_bili.setValue((t-b)/(c-b));
			my_T_TC_bili.setValue((t-b)/((c-b)+(t-b)));
		}
		
		my_TestDataBean.setT_location(my_T.get());
		my_TestDataBean.setB_location(my_B.get());
		my_TestDataBean.setC_location(my_C.get());
		my_TestDataBean.setT_c(my_T_C_bili.floatValue());
		my_TestDataBean.setT_tc(my_T_TC_bili.floatValue());
		
		TestDataDao.SaveTestData(my_TestDataBean);
	}

	public TestDataBean getMy_TestDataBean() {
		return my_TestDataBean;
	}
	
	public SimpleDoubleProperty my_T_C_biliProperty() {
		return my_T_C_bili;
	}

	public SimpleDoubleProperty my_T_TC_biliProperty() {
		return my_T_TC_bili;
	}
	
	public SimpleStringProperty my_descProperty(){
		return my_desc;
	}
	
	public void HideSeries(){
		Path aa = (Path) my_seires.getNode();//charts.lookup(".chart-series-line.series"+index);
		aa.setVisible(false);
		 
		 for (int i=0; i<my_seires.getData().size(); i++ ) {
			 StackPane bb = (StackPane) my_seires.getData().get(i).getNode();
			 bb.setVisible(false);
		}
	}
	
	public void ShowSeries(){
		Path aa = (Path) my_seires.getNode();//charts.lookup(".chart-series-line.series"+index);
		aa.setVisible(true);
		 
		 for (int i=0; i<my_seires.getData().size(); i++ ) {
			 StackPane bb = (StackPane) my_seires.getData().get(i).getNode();
			 bb.setVisible(true);
		}
	}
}
