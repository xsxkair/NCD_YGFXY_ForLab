package com.org.xsx.dao;

import java.util.List;

import com.org.xsx.beans.HibernateDao;
import com.org.xsx.beans.TesterBean;

public class TesterDao {
	
	public static TesterBean GetTester(String name, String pass){
		String hql = "select t from TesterBean as t where t.username=:parm0 and t.passowrd=:parm1";
				
		return (TesterBean) HibernateDao.GetInstance().queryOne(hql, new String[]{ name, pass});
	}
	
	public static List<TesterBean> GetAllTester(){
		String hql = "select t from TesterBean as t";
		
		return HibernateDao.GetInstance().query(hql, null, null, null);
	}
}
