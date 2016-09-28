package com.org.xsx.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class DataPointUI extends StackPane{

	private Text Datapointinfo;
	
	private Circle stylecircle;
	
	public DataPointUI(){
		this.UI_Init();
	}
	
	private void UI_Init(){
        
		Datapointinfo = new Text();
		Datapointinfo.setFont(new Font(20));
		
		stylecircle = new Circle(3, 3, 3);
		stylecircle.setStrokeWidth(1);
		
		setPrefSize(6, 6);
		SetMyStyle(DataPointStyleEnum.Normol);
		getStyleClass().add("DataPointPane");
	}
	public void SetColor(Paint value){
		stylecircle.setFill(value);
	}
	public void SetMyStyle(DataPointStyleEnum style){
		
		getChildren().clear();

		switch(style){
		case B_Line:
			{
				//Datapointinfo.setText("B");
				//setStyle("-fx-background-color:#0CF2F0");	//À¶É«
				//setEffect(new DropShadow(5,Color.web("rgba(111,103,236,1.0)")));
				//getChildren().addAll(Datapointinfo);
				Image image = null;
				ImageView imageView = new ImageView();
				try {
					image = new Image(new FileInputStream(new File("RES/sun.png")));
					imageView.setImage(image);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				getStyleClass().remove("DataPointPane");
				getChildren().add(imageView);
			}
			break;
		case C_Line:
		{
			Image image = null;
			ImageView imageView = new ImageView();
			try {
				image = new Image(new FileInputStream(new File("RES/moon.png")));
				imageView.setImage(image);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			getStyleClass().remove("DataPointPane");
			getChildren().add(imageView);
		}
			break;
		case Normol:
		{
			setEffect(null);
		}
			break;
		case T_Line:
		{
			Image image = null;
			ImageView imageView = new ImageView();
			try {
				image = new Image(new FileInputStream(new File("RES/star.png")));
				imageView.setImage(image);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			getStyleClass().remove("DataPointPane");
			getChildren().add(imageView);
		}
			break;

		default:
		{
			setEffect(null);
		}
			break;
		
		}
	}
}
