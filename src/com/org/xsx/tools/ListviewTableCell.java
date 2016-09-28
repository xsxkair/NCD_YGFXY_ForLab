package com.org.xsx.tools;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.util.Callback;

public class ListviewTableCell<S, T> extends TableCell<S, T> {
	
    private ListView<String> radio;
    private ObservableValue<T> ov;

    public ListviewTableCell() {
        this.radio = new ListView();
        radio.getStyleClass().add("test");
        setGraphic(radio);
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        //super.updateItem(item, empty);
        if(item != null){
        	setAlignment(Pos.CENTER);
        	radio.setItems((ObservableList)item);
        	radio.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {

                @Override public ListCell<String> call(ListView<String> list) {

                    return new MoneyFormatCell();

                }

            });
        	radio.setPrefWidth(20);
        	radio.setPrefHeight(((ObservableList<String>)item).size()*23);
        	setGraphic(radio);
        	setPadding(new Insets(0,0,0,0));
        	setBorder(null);
        	
        }
        else{
        	setGraphic(null);
        }
    }
}

class MoneyFormatCell extends ListCell<String> {

    @Override

    public void updateItem(String item, boolean empty) {

        super.updateItem(item, empty);
        
        setAlignment(Pos.CENTER);
        setPrefSize(18, 22);
        setText(item == null ? "" : item);

    }

} 
