package com.org.xsx.services;

import java.util.HashMap;
import java.util.Map;

import com.org.xsx.UI.DeviceTestPage.DeviceTestPage;
import com.org.xsx.beans.DeviceInfoBean;
import com.org.xsx.beans.DeviceListBean;
import com.org.xsx.beans.HibernateSessionBean;
import com.org.xsx.tools.ClientSocket;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import net.sf.json.JSONObject;

public class SystemInitService extends Service<Boolean>{
	
	private static SystemInitService GB_SystemInitService = null;
	
	private SystemInitService() {
		// TODO Auto-generated constructor stub
	}
	
	public static SystemInitService GetInstance() {
		if(GB_SystemInitService == null)
			GB_SystemInitService = new SystemInitService();
		
		return GB_SystemInitService;
	}
	
	@Override
	protected Task<Boolean> createTask() {
		// TODO Auto-generated method stub
		return new systeminittask();
	}
	
	class systeminittask extends Task<Boolean>{

		@Override
		protected Boolean call() throws Exception {
			// TODO Auto-generated method stub
			return systeminit_fun();
		}
		
		private Boolean systeminit_fun() throws InterruptedException{
			int i;
        	String iphost;
        	String devicestr;
        	JSONObject jsonObject;
        	DeviceInfoBean deviceinfobean;
        	Map typeMap = new HashMap();
        	
			updateMessage("连接数据库，请稍等。。。"); 
    		updateProgress(1, 100);
    		
    		try {
    			HibernateSessionBean.GetInstance().Hibernate_Init();
			} catch (Exception e) {
				// TODO: handle exception
				updateMessage("数据库链接失败"); 
	    		updateProgress(10, 100);
	    		Thread.sleep(10000); 
			}
    		
    		
    		updateMessage("扫描设备，请稍等。。。"); 
    		updateProgress(10, 100);
    		
        	typeMap.put("deviceid", String.class);
        	typeMap.put("devicestatus", int.class);
        	
        	for(i=150; i<200; i++){
        		iphost = String .format("192.168.0.%d",i);
        		devicestr = ClientSocket.ComWithServer(iphost, "Read Device Info99");
        		
        		if(devicestr != null){
        			
        			jsonObject = JSONObject.fromObject(devicestr);
        			Map output1 = (Map)JSONObject.toBean(jsonObject, Map.class,typeMap);
        			deviceinfobean = new DeviceInfoBean(iphost, (String)output1.get("deviceid"), (int)output1.get("devicestatus"));
        			DeviceListBean.GetInstance().getGB_DeviceList().add(new DeviceTestPage(deviceinfobean));
        		}
        		updateProgress((i-100)/2+10, 100);
        		Thread.sleep(10); 
        	}
        	
        	updateMessage("已找到"+DeviceListBean.GetInstance().getGB_DeviceList().size()+"个设备"); 
        	updateProgress(100, 100);
    		Thread.sleep(1000); 

			return true;
		}
	}
}
