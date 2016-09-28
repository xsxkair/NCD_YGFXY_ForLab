package com.org.xsx.UI;

import java.io.File;
import java.io.FileInputStream;

import com.org.xsx.UI.Lunch.UI_SystemInit;
import com.org.xsx.beans.ScenceBean;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application{
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		this.FunctionInit(primaryStage);
		
		ScenceBean.GetInstance().setGB_Scene(UI_SystemInit.GetInstance().getScene());
		
		primaryStage.setTitle("Ó«¹â·ÖÎöÒÇ--FOR BIO LAB OF NCD ONLY  V2.0");
		primaryStage.getIcons().add(new Image(new FileInputStream(new File("RES/logo.png"))));
		primaryStage.setResizable(true);
		primaryStage.show();
	}

	private void FunctionInit(Stage primaryStage) {
		ScenceBean.GetInstance().getGB_Scene().addListener(new ChangeListener<Scene>() {
			@Override
			public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
				// TODO Auto-generated method stub
				if(newValue != null){
					primaryStage.setScene(newValue);
					primaryStage.centerOnScreen();
				}
			}
		});
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				// TODO Auto-generated method stub
				primaryStage.close();
				System.exit(0);
			}
		});
	}
}
