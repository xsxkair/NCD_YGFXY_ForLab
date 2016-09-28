package com.org.xsx.beans;

import java.io.File;

public class QRCodeBean {
	
	private static QRCodeBean S_QRCodeBean = null;
	
	private String itemname;
	private String tctype;
	private String normal;
	private String low;
	private String high;
	private String danwei;
	private String t_location;
	private String waittime;
	private String c_location;
	private String outtime;
	private String fenduan;
	private String biaoqunum;
	private String qu1_a;
	private String qu1_b;
	private String qu1_c;
	private String qu2_a;
	private String qu2_b;
	private String qu2_c;
	
	private String pihaohead;
	private int makenum;
	private int currentnum;
	private String savedir;
	
	private QRCodeBean(){
		
	}
	
	public static QRCodeBean GetInstance(){
		if(null == S_QRCodeBean)
			S_QRCodeBean = new QRCodeBean();
		
		return S_QRCodeBean;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public String getTctype() {
		return tctype;
	}

	public void setTctype(String tctype) {
		this.tctype = tctype;
	}

	public String getNormal() {
		return normal;
	}

	public void setNormal(String normal) {
		this.normal = normal;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getDanwei() {
		return danwei;
	}

	public void setDanwei(String danwei) {
		this.danwei = danwei;
	}

	public String getT_location() {
		return t_location;
	}

	public void setT_location(String t_location) {
		this.t_location = t_location;
	}

	public String getWaittime() {
		return waittime;
	}

	public void setWaittime(String waittime) {
		this.waittime = waittime;
	}

	public String getC_location() {
		return c_location;
	}

	public void setC_location(String c_location) {
		this.c_location = c_location;
	}

	public String getOuttime() {
		return outtime;
	}

	public void setOuttime(String outtime) {
		this.outtime = outtime;
	}

	public String getFenduan() {
		return fenduan;
	}

	public void setFenduan(String fenduan) {
		this.fenduan = fenduan;
	}

	public String getBiaoqunum() {
		return biaoqunum;
	}

	public void setBiaoqunum(String biaoqunum) {
		this.biaoqunum = biaoqunum;
	}

	public String getQu1_a() {
		return qu1_a;
	}

	public void setQu1_a(String qu1_a) {
		this.qu1_a = qu1_a;
	}

	public String getQu1_b() {
		return qu1_b;
	}

	public void setQu1_b(String qu1_b) {
		this.qu1_b = qu1_b;
	}

	public String getQu1_c() {
		return qu1_c;
	}

	public void setQu1_c(String qu1_c) {
		this.qu1_c = qu1_c;
	}

	public String getQu2_a() {
		return qu2_a;
	}

	public void setQu2_a(String qu2_a) {
		this.qu2_a = qu2_a;
	}

	public String getQu2_b() {
		return qu2_b;
	}

	public void setQu2_b(String qu2_b) {
		this.qu2_b = qu2_b;
	}

	public String getQu2_c() {
		return qu2_c;
	}

	public void setQu2_c(String qu2_c) {
		this.qu2_c = qu2_c;
	}

	public String getPihaohead() {
		return pihaohead;
	}

	public void setPihaohead(String pihaohead) {
		this.pihaohead = pihaohead;
	}

	public int getMakenum() {
		return makenum;
	}

	public void setMakenum(int makenum) {
		this.makenum = makenum;
	}

	public int getCurrentnum() {
		return currentnum;
	}

	public void setCurrentnum(int currentnum) {
		this.currentnum = currentnum;
	}

	public String getSavedir() {
		return savedir;
	}

	public void setSavedir(String savedir) {
		this.savedir = savedir;
	}
}
