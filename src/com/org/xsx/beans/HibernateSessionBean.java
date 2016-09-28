package com.org.xsx.beans;

import org.hibernate.*;
import org.hibernate.cfg.*;


public class HibernateSessionBean
{
	private static HibernateSessionBean S_HibernateSessionBean = null;
	
	private static Configuration conf = null;  
	private static SessionFactory factory = null;  
	private static Session session = null;  

	
	private HibernateSessionBean(){
		
	}
	
    public static HibernateSessionBean GetInstance(){
    	if(S_HibernateSessionBean == null){
    		S_HibernateSessionBean = new HibernateSessionBean();
    	}
    	
    	return S_HibernateSessionBean;
    }
    
	public void Hibernate_Init()
	{
		//����Ĭ�ϵ�hibernate.cfg.xml������һ��Configuration��ʵ��
		conf = new Configuration().configure();

		//��Configuration��ʵ��������һ��SessionFactoryʵ��
		factory = conf.buildSessionFactory();
	}

		/** 
	     * ���Session���� 
	     * */  
	    public Session getSession(){  
	        session = factory.openSession();  
	        return session;  
	    }  
	    /** 
	     * �ر�session���� 
	     * */  
	    public void closeSession(){  
	        if(session!=null)  
	            session.close();  
	    }  
}