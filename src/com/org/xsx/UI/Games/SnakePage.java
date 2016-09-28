package com.org.xsx.UI.Games;

import java.io.IOException;
import java.io.InputStream;

import com.org.xsx.beans.TesterBean;
import com.org.xsx.dao.TesterDao;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class SnakePage {
	
	private Tab S_Tab = null;
	
	private SimpleObjectProperty<TesterBean> S_TesterBean = null;
	
	@FXML
	VBox GB_LoginPane;
	
	@FXML
	TextField GB_UserNameTextField;
	
	@FXML
	TextField GB_UserPassTextField;
	
	public SnakePage(){
		this.UI_Init();
		Data_Init();
	}
	
	private void UI_Init(){
		AnchorPane root = null;
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("SnakePage.fxml"));
        InputStream in = this.getClass().getResourceAsStream("SnakePage.fxml");
        loader.setController(this);
        try {
        	root = loader.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
         S_Tab = new Tab("Ì°³ÔÉß");
        S_Tab.setContent(root);
        S_Tab.setClosable(true);
	}
	
	private void Data_Init() {
		
		S_TesterBean = new SimpleObjectProperty<>();
		S_TesterBean.addListener(new ChangeListener<TesterBean>() {

			@Override
			public void changed(ObservableValue<? extends TesterBean> observable, TesterBean oldValue,
					TesterBean newValue) {
				
				if(newValue != null){
					GB_LoginPane.setVisible(false);
				}
				else{
					GB_LoginPane.setVisible(true);
				}	
			}
		});
	}
	
	public Tab GetSnakePage(){
		return S_Tab;
	}
	
	
	@FXML
	public void GB_LoginAction(ActionEvent e){
		TesterBean tester =  TesterDao.GetTester(GB_UserNameTextField.getText(), GB_UserPassTextField.getText());

		if(tester != null){
			S_TesterBean.set(tester);
			GB_UserNameTextField.setText(null);
			GB_UserPassTextField.setText(null);
		}
		else {
			ButtonType loginButtonType = new ButtonType("È·¶¨", ButtonData.OK_DONE);
			Dialog<String> dialog = new Dialog<>();
			dialog.getDialogPane().setContentText("µÇÂ½Ê§°Ü£¡");
			dialog.getDialogPane().getButtonTypes().add(loginButtonType);
			dialog.showAndWait();
		}
	}
	
	@FXML
	public void GB_LoginKeyEvent(KeyEvent e){
		if(e.getCode() == KeyCode.ENTER){
			TesterBean tester =  TesterDao.GetTester(GB_UserNameTextField.getText(), GB_UserPassTextField.getText());

			if(tester != null){
				S_TesterBean.set(tester);
				GB_UserNameTextField.setText(null);
				GB_UserPassTextField.setText(null);
			}
			else {
				ButtonType loginButtonType = new ButtonType("È·¶¨", ButtonData.OK_DONE);
				Dialog<String> dialog = new Dialog<>();
				dialog.getDialogPane().setContentText("µÇÂ½Ê§°Ü£¡");
				dialog.getDialogPane().getButtonTypes().add(loginButtonType);
				dialog.showAndWait();
			}
        }
	}
}
