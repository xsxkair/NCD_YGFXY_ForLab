package com.org.xsx.beans;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;

public class ScenceBean {
	
	private static ScenceBean GB_ScenesData = null; 
	
	private ObjectProperty<Scene>	GB_Scene = null;
	
	private ScenceBean(){
		
	}
	
	public static ScenceBean GetInstance() {
		if(GB_ScenesData == null)
			GB_ScenesData = new ScenceBean();
		
		if(GB_ScenesData.GB_Scene == null)
			GB_ScenesData.GB_Scene = new SimpleObjectProperty<>();
		
		return GB_ScenesData;
	}

	public ObjectProperty<Scene> getGB_Scene() {
		return GB_Scene;
	}

	public void setGB_Scene(Scene scene) {
		GB_Scene.set(scene);
	}
}
