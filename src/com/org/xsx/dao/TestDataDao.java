package com.org.xsx.dao;

import java.util.List;

import com.org.xsx.beans.HibernateDao;
import com.org.xsx.beans.TestDataBean;

public class TestDataDao {
	
	public static void SaveTestData(TestDataBean testdata){
		HibernateDao.GetInstance().save(testdata);
	}
	
	public static void UpDateTestData(TestDataBean testdata){
		HibernateDao.GetInstance().update(testdata);
	}
	
	public static Long GetDataNum(String hql, Object[] parm){
		return (Long) HibernateDao.GetInstance().queryOne(hql.toString(), parm);
	}
	

	public static List<TestDataBean> GetTestDataList(String hql, Object[] parm, Integer firstnum, Integer pagesize){
		return HibernateDao.GetInstance().query(hql.toString(), parm, firstnum, pagesize);
	}
	
	public static List<String> GetAllDeviceId(){
		String hql = "select distinct da.deviceid from TestDataBean as da";
		
		return HibernateDao.GetInstance().query(hql, null, null, null);
	}
}
