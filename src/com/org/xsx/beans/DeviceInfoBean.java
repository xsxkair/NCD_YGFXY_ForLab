package com.org.xsx.beans;

public class DeviceInfoBean {
	private String ip;
	private String deviceid;
	private int devicestatus;
	
	public DeviceInfoBean(String ip, String deviceid, int stauts){
		this.ip = ip;
		this.deviceid = deviceid;
		this.devicestatus = stauts;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public int getDevicestatus() {
		return devicestatus;
	}
	public void setDevicestatus(int devicestatus) {
		this.devicestatus = devicestatus;
	}
}
