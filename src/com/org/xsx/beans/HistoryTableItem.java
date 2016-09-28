package com.org.xsx.beans;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class HistoryTableItem {
	
	private BooleanProperty isselected;
	private Integer index;
	private String deviceid;
	private String deviceip;
	private java.sql.Date testdate;
	private java.sql.Time testtime;
	private String testername;
	private String testcount;
	private String testdesc;
	private Integer t_location;
	private Integer b_location;
	private Integer c_location;
	private Float t_c;
	private Float t_tc;
	private String testdata;
	
	private TestDataBean testdatabean;
	
	public HistoryTableItem(){
		this.isselected = new SimpleBooleanProperty();
	}
	public void setIsselected(Boolean value){
		isselected.set(value);
	}
	public BooleanProperty isselectedProperty() { 
		return isselected; 
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getDeviceip() {
		return deviceip;
	}
	public void setDeviceip(String deviceip) {
		this.deviceip = deviceip;
	}
	public java.sql.Date getTestdate() {
		return testdate;
	}
	public void setTestdate(java.sql.Date testdate) {
		this.testdate = testdate;
	}
	public java.sql.Time getTesttime() {
		return testtime;
	}
	public void setTesttime(java.sql.Time testtime) {
		this.testtime = testtime;
	}
	public String getTestername() {
		return testername;
	}
	public void setTestername(String testername) {
		this.testername = testername;
	}
	public String getTestcount() {
		return testcount;
	}
	public void setTestcount(String testcount) {
		this.testcount = testcount;
	}
	public String getTestdesc() {
		return testdesc;
	}
	public void setTestdesc(String testdesc) {
		this.testdesc = testdesc;
	}
	public Integer getT_location() {
		return t_location;
	}
	public void setT_location(Integer t_location) {
		this.t_location = t_location;
	}
	public Integer getB_location() {
		return b_location;
	}
	public void setB_location(Integer b_location) {
		this.b_location = b_location;
	}
	public Integer getC_location() {
		return c_location;
	}
	public void setC_location(Integer c_location) {
		this.c_location = c_location;
	}
	public Float getT_c() {
		return t_c;
	}
	public void setT_c(Float t_c) {
		this.t_c = t_c;
	}
	public Float getT_tc() {
		return t_tc;
	}
	public void setT_tc(Float t_tc) {
		this.t_tc = t_tc;
	}
	public String getTestdata() {
		return testdata;
	}
	public void setTestdata(String testdata) {
		this.testdata = testdata;
	}
	public TestDataBean getTestdatabean() {
		return testdatabean;
	}
	public void setTestdatabean(TestDataBean testdatabean) {
		this.testdatabean = testdatabean;

		this.deviceid = testdatabean.getDeviceid();
		this.deviceip = testdatabean.getDeviceip();
		this.testdate = testdatabean.getTestdate();
		this.testtime = testdatabean.getTesttime();
		this.testername = testdatabean.getTestername();
		this.testcount = testdatabean.getTestcount();
		this.testdesc = testdatabean.getTestdesc();
		this.t_location = testdatabean.getT_location();
		this.b_location = testdatabean.getB_location();
		this.c_location = testdatabean.getC_location();
		this.t_c = testdatabean.getT_c();
		this.t_tc = testdatabean.getT_tc();
		this.testdata = testdatabean.getTestdata();
	}


}
