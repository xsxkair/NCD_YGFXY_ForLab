package com.org.xsx.UI.Erweima;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.org.xsx.beans.QRCodeBean;
import com.org.xsx.services.MakeQRCodeService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MakeQRCodePage {
	
	private Tab S_Tab = null;
	
	@FXML
	TextField GB_ItemNameField;
	
	@FXML
	ToggleGroup CalType;
	
	@FXML
	RadioButton GB_CalType1;
	@FXML
	RadioButton GB_CalType2;
	
	@FXML
	TextField GB_NormalField;
	
	@FXML
	TextField GB_LowField;
	
	@FXML
	TextField GB_HighField;
	
	@FXML
	TextField GB_DanweiField;
	
	@FXML
	TextField GB_TLocationField;
	
	@FXML
	TextField GB_WaitTimeField;
	
	@FXML
	TextField GB_CLocation;
	
	@FXML
	TextField GB_OutTimeField;
	
	@FXML
	TextField GB_FenDuanField;
	
	@FXML
	ToggleGroup BQNUM;
	
	@FXML
	HBox GB_BQ1Hbox;
	
	@FXML
	RadioButton GB_BQNum1;
	@FXML
	RadioButton GB_BQNum2;
	
	@FXML
	TextField GB_BQ1AField;
	
	@FXML
	TextField GB_BQ1BField;
	
	@FXML
	TextField GB_BQ1CField;
	
	@FXML
	HBox GB_BQ2Hbox;
	
	@FXML
	TextField GB_BQ2AField;
	
	@FXML
	TextField GB_BQ2BField;
	
	@FXML
	TextField GB_BQ2CField;
	
	@FXML
	TextField GB_QRNumField;
	
	@FXML
	Button GB_MakeQRCodeButton;
	
	SimpleBooleanProperty ischange;
	
	QRCodeBean myQrCodeBean;
	
	MakeQRCodeService S_MakeQRCodeService;
	
	@FXML
	Region GB_Viel;
	
	@FXML
	ProgressIndicator GB_RefreshBar;
	
	public MakeQRCodePage(){
		this.UI_Init();
		Data_Init();
	}
	
	private void UI_Init(){
		AnchorPane root = null;
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("MakeQRCodePage.fxml"));
        InputStream in = this.getClass().getResourceAsStream("MakeQRCodePage.fxml");
        loader.setController(this);
        try {
        	root = loader.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
         S_Tab = new Tab("二维码生成工具");
        S_Tab.setContent(root);
        S_Tab.setClosable(true);
	}
	
	private void Data_Init() {
		
		S_MakeQRCodeService = new MakeQRCodeService();
		
		GB_RefreshBar.progressProperty().bind(S_MakeQRCodeService.progressProperty());
		GB_RefreshBar.visibleProperty().bind(S_MakeQRCodeService.runningProperty());
		GB_Viel.visibleProperty().bind(S_MakeQRCodeService.runningProperty());
		
		GB_CalType1.setUserData("1");
		GB_CalType2.setUserData("2");
		GB_BQNum1.setUserData("1");
		GB_BQNum2.setUserData("2");
		
		myQrCodeBean = QRCodeBean.GetInstance();

		GB_BQ2Hbox.disableProperty().bind(new BooleanBinding() {
			{
				bind(GB_BQNum2.selectedProperty());
			}
			@Override
			protected boolean computeValue() {
				// TODO Auto-generated method stub
				if(GB_BQNum2.isSelected())
					return false;
				else
					return true;
			}
		});
		
		GB_MakeQRCodeButton.disableProperty().bind(new BooleanBinding() {
			{
				bind(GB_QRNumField.textProperty());
			}
			@Override
			protected boolean computeValue() {
				// TODO Auto-generated method stub
				Integer temp = null;
				
				try {
					temp = Integer.valueOf(GB_QRNumField.getText());
				} catch (NumberFormatException e) {
					// TODO: handle exception
					temp = null;
				}
				
				if(temp != null)
				{
					myQrCodeBean.setMakenum(temp.intValue());
					return false;
				}
				else
					return true;
			}
		});
	}
	
	public Tab GetPage(){
		return S_Tab;
	}
	
	
	@FXML
	public void GB_MakeQRCodeAction(){
		DirectoryChooser dirchoose = new DirectoryChooser();
		dirchoose.setTitle("二维码生成文件夹");
		 
		File selectedFile = dirchoose.showDialog(null);
		 
		if(selectedFile != null){
			 SimpleDateFormat     matter1     =     new     SimpleDateFormat( "yyyyMMddHHmmss");    
			 matter1.setTimeZone(TimeZone.getTimeZone( "GMT ")); 
			 myQrCodeBean.setItemname(GB_ItemNameField.getText());
			 myQrCodeBean.setTctype(CalType.getSelectedToggle().getUserData().toString());
			 myQrCodeBean.setNormal(GB_NormalField.getText());
			 myQrCodeBean.setLow(GB_LowField.getText());
				myQrCodeBean.setHigh(GB_HighField.getText());
				myQrCodeBean.setDanwei(GB_DanweiField.getText());
				myQrCodeBean.setT_location(GB_TLocationField.getText());
				myQrCodeBean.setWaittime(GB_WaitTimeField.getText());
				myQrCodeBean.setOuttime(GB_OutTimeField.getText());
				myQrCodeBean.setFenduan(GB_FenDuanField.getText());
				myQrCodeBean.setBiaoqunum(BQNUM.getSelectedToggle().getUserData().toString());
				myQrCodeBean.setQu1_a(GB_BQ1AField.getText());
				myQrCodeBean.setQu1_b(GB_BQ1BField.getText());
				myQrCodeBean.setQu1_c(GB_BQ1CField.getText());
				myQrCodeBean.setQu2_a(GB_BQ2AField.getText());
				myQrCodeBean.setQu2_b(GB_BQ2BField.getText());
				myQrCodeBean.setQu2_c(GB_BQ2CField.getText());
				myQrCodeBean.setC_location(GB_CLocation.getText());
			 myQrCodeBean.setPihaohead(matter1.format(new Date()));
			 myQrCodeBean.setCurrentnum(0);
			 myQrCodeBean.setSavedir(selectedFile.getPath());

			 S_MakeQRCodeService.restart();
		 }
	}
}
