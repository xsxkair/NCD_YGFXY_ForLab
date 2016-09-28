package com.org.xsx.UI.Pages;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.controlsfx.dialog.LoginDialog;

import com.org.xsx.UI.DeviceTestPage.DeviceTestPage;
import com.org.xsx.UI.Erweima.MakeQRCodePage;
import com.org.xsx.UI.Games.SnakePage;
import com.org.xsx.UI.HistoryDataPage.HistoryPage;
import com.org.xsx.beans.DeviceInfoBean;
import com.org.xsx.beans.DeviceListBean;
import com.org.xsx.dao.TesterDao;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TabPane;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;
import javafx.util.Pair;

public class UI_MainPage {
	
	private static UI_MainPage GB_UI_MainPage = null;
	
	private Scene S_Scene = null;
	
	@FXML
	TabPane GB_TabPanes;
	
	private UI_MainPage(){
		
	}
	
	public static UI_MainPage GetInstance(){
		if(GB_UI_MainPage == null)
			GB_UI_MainPage = new UI_MainPage();
		
		return GB_UI_MainPage;
	}
	
	private void UI_Init(){
		AnchorPane root = null;
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("UI_MainPage.fxml"));
        InputStream in = this.getClass().getResourceAsStream("UI_MainPage.fxml");
        loader.setController(this);
        try {
        	root = loader.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        root.getStylesheets().add(this.getClass().getResource("MyCSS.css").toExternalForm());
        
        S_Scene = new Scene(root);
	}
	
	private void Fun_Init(){
		
		for (DeviceTestPage deviceTestPage : DeviceListBean.GetInstance().getGB_DeviceList()) {
			deviceTestPage.Init();
			GB_TabPanes.getTabs().add(deviceTestPage.GetDeviceTabPane());
		}
		
		HistoryPage historyPage = new HistoryPage();
		historyPage.Init();
		GB_TabPanes.getTabs().add(historyPage.GetDeviceTabPane());
	}
	
	public Scene GetScene(){
		this.UI_Init();
		this.Fun_Init(); 
        
		return S_Scene;
	}

	@FXML
	public void GB_StartGameAction(){
		GB_TabPanes.getTabs().add(new SnakePage().GetSnakePage());
		GB_TabPanes.getSelectionModel().selectLast();
	}
	
	@FXML
	public void GB_MakeQRCodeAction(){
		GB_TabPanes.getTabs().add(new MakeQRCodePage().GetPage());
		GB_TabPanes.getSelectionModel().selectLast();
	}
}
