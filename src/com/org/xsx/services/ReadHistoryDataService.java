package com.org.xsx.services;

import java.util.ArrayList;
import java.util.List;

import com.org.xsx.UI.HistoryDataPage.HistoryFilterData;
import com.org.xsx.beans.HistorySelectedData;
import com.org.xsx.beans.HistoryTableItem;
import com.org.xsx.beans.TestDataBean;
import com.org.xsx.dao.TestDataDao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class ReadHistoryDataService extends Service<ObservableList<HistoryTableItem>>{

	@Override
	protected Task<ObservableList<HistoryTableItem>> createTask() {
		// TODO Auto-generated method stub
		return new RefreshDataTask();
	}
	
	class RefreshDataTask extends Task<ObservableList<HistoryTableItem>> {       

        @Override 
        protected ObservableList<HistoryTableItem> call() throws InterruptedException{

        	return RefreshReportWithNoCheck();
        }
        
        
        private ObservableList<HistoryTableItem> RefreshReportWithNoCheck() throws InterruptedException{

        	ObservableList<HistoryTableItem> tabledatalist = FXCollections.observableArrayList();
        	
        	StringBuffer hql = new StringBuffer("select t from TestDataBean as t");
    		ArrayList<Object> parms = new ArrayList<>();
    		
    		String user = HistoryFilterData.GetInstance().getTestername();
    		if((user != "À˘”–»À") && (user != null)){
    			hql.append(" AND t.testername=:parm"+parms.size());
    			parms.add(user);
    		}
    		
    		String deviceid = HistoryFilterData.GetInstance().getDeviceid();
    		if((deviceid != "All") && (deviceid != null)){
    			hql.append(" AND t.deviceid=:parm"+parms.size());
    			parms.add(deviceid);
    		}
    		
    		java.sql.Date tempdate = HistoryFilterData.GetInstance().getTesttime();
    		if(tempdate != null){
    			hql.append(" AND t.testdate=:parm"+parms.size());
    			parms.add(tempdate);
    		}
    		
    		String string = hql.toString().replaceFirst("AND", "WHERE");
    		
    		List<TestDataBean> list = TestDataDao.GetTestDataList(string, parms.toArray(), HistoryFilterData.GetInstance().getFirstindex(), HistoryFilterData.GetInstance().getPagesize());

    		for (TestDataBean testDataBean : list) {
    			HistoryTableItem temp = new HistoryTableItem();
    			
    			temp.setTestdatabean(testDataBean);
    			temp.setIndex(HistoryFilterData.GetInstance().getFirstindex()+1+list.indexOf(testDataBean));
    			
    			if(HistorySelectedData.GetInstance().getSelectedDataMap().get(testDataBean.getId()) == null)
    				temp.setIsselected(false);
    			else
    				temp.setIsselected(true);
    			tabledatalist.add(temp);
    			
    			updateProgress(list.indexOf(testDataBean), list.size());
    			Thread.sleep(10);
			}
    		
        	return tabledatalist;
        }
        
    }
}
