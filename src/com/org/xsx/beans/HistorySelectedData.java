package com.org.xsx.beans;

import java.util.HashMap;
import java.util.Map;

public class HistorySelectedData {
	
	private static HistorySelectedData S_HistorySelectedData = null;
	
	private Map<Integer, TestDataBean> SelectedDataMap = new HashMap<>();
	
	private HistorySelectedData() {
		
	}
	
	public static HistorySelectedData GetInstance(){
		if(S_HistorySelectedData == null)
			S_HistorySelectedData = new HistorySelectedData();
		
		return S_HistorySelectedData;
	}

	public Map<Integer, TestDataBean> getSelectedDataMap() {
		return SelectedDataMap;
	}
	
}
