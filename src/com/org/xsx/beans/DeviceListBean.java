package com.org.xsx.beans;

import java.util.ArrayList;

import com.org.xsx.UI.DeviceTestPage.DeviceTestPage;


public class DeviceListBean {
	
	private static DeviceListBean GB_DeviceListBean = null;
	
	private ArrayList<DeviceTestPage> GB_DeviceList = new ArrayList<DeviceTestPage>();

	private DeviceListBean(){
		
	}
	
	public static DeviceListBean GetInstance(){
		if(GB_DeviceListBean == null)
			GB_DeviceListBean = new DeviceListBean();
		
		return GB_DeviceListBean;
	}

	public ArrayList<DeviceTestPage> getGB_DeviceList() {
		return GB_DeviceList;
	}

}
