package com.org.xsx.UI.HistoryDataPage;


public class HistoryFilterData {
	private String deviceid = null;
	private String testername = null;
	private java.sql.Date testtime = null;
	
	private long datatotalnum;												//数据总数
	private int pageindex;								//当前页数
	private final int pagesize = 50;
	
	private static HistoryFilterData S_HistoryFilterData = null;
	
	private HistoryFilterData(){
		
	}
	
	public static HistoryFilterData GetInstance(){
		if(S_HistoryFilterData == null)
			S_HistoryFilterData = new HistoryFilterData();
		
		return S_HistoryFilterData;
	}
	
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getTestername() {
		return testername;
	}
	public void setTestername(String testername) {
		this.testername = testername;
	}
	public java.sql.Date getTesttime() {
		return testtime;
	}
	public void setTesttime(java.sql.Date testtime) {
		this.testtime = testtime;
	}

	public long getDatatotalnum() {
		return datatotalnum;
	}

	public void setDatatotalnum(long datatotalnum) {
		this.datatotalnum = datatotalnum;
	}

	public int getPageindex() {
		return pageindex;
	}

	public void setPageindex(int pageindex) {
		this.pageindex = pageindex;
	}

	public int getPagesize() {
		return pagesize;
	}
	
	public int getFirstindex(){
		return pagesize*pageindex;
	}
}
