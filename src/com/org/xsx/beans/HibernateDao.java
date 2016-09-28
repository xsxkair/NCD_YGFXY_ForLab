package com.org.xsx.beans;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria; 
import org.hibernate.Query;
import org.hibernate.Session;  
import org.hibernate.Transaction;

public class HibernateDao{  
	
	private static HibernateDao S_HibernateDao = null;
	
    protected Session session = null;  
    protected Transaction ts = null;  
    protected Criteria criteria;  
    
    private HibernateDao(){
    	
    }
    
    public static HibernateDao GetInstance(){
    	if(S_HibernateDao == null)
    		S_HibernateDao = new HibernateDao();
    	
    	return S_HibernateDao;
    }
    
    /** 
     * session���淽�� 
     * */  
    public boolean save(Object obj){  
    	Session session=null;  
        Transaction tran=null;  
        boolean result=false;  
        try  
        {  
            session = HibernateSessionBean.GetInstance().getSession();
            tran = session.beginTransaction();  
            session.saveOrUpdate(obj);
            tran.commit();  
            result=true;  
        }  
        catch (Exception e)  
        {  
           if(tran!=null)  
           {  
               //����ع�  
               tran.rollback();  
           }  
        }  
        finally  
        {  
            if(session!=null)  
            {  
                //�ر�session  
                session.close();  
            }  
        }  
        return result;
    }  
    /** 
     * @param obj 
     * @return ɾ������ 
     */  
    public boolean delete(Object obj)  
      {  
            Session session=null;  
            Transaction tran=null;  
            boolean result=false;  
            try  
            {  
                session = HibernateSessionBean.GetInstance().getSession();  
                tran = session.beginTransaction();  
                session.delete(obj);  
                tran.commit();  
                result=true;  
            }  
            catch (Exception e)  
            {  
               if(tran!=null)  
               {  
                   //����ع�  
                   tran.rollback();  
               }  
            }  
            finally  
            {  
                if(session!=null)  
                {  
                    //�ر�session  
                    session.close();  
                }  
            }  
            return result;  
      }  
    
    /** 
     * @param c 
     * @param obj  ��ѯһ�����ݸ���������id�� 
     * @return 
     */  
      public Object get(Class c,int obj)  
      {  
            Session session=null;  
            Object object=null;  
            try  
            {  
                session = HibernateSessionBean.GetInstance().getSession();  
                object=session.get(c,obj);  
            }  
            catch (Exception e)  
            {  
            }  
            finally  
            {  
                if(session!=null)  
                {  
                    //�ر�session  
                    session.close();  
                }  
            }  
            return object;  
      }  
      
    /** 
     * session�޸ķ��� 
     * */  
    public boolean update(Object obj){  
    	Session session=null;  
        Transaction tran=null;  
        boolean result=false;  
        try  
        {  
            session = HibernateSessionBean.GetInstance().getSession();  
            tran=session.beginTransaction();  
            session.update(obj);  
            tran.commit();  
            result=true;  
        }  
        catch (Exception e)  
        {  
           if(tran!=null)  
           {  
               //����ع�  
               tran.rollback();  
           }  
        }  
        finally  
        {  
            if(session!=null)  
            {  
                //�ر�session  
                session.close();  
            }  
        }  
        return result; 
    }  
    
      /** 
     * @param sql 
     * @param param ��ѯ������¼ 
     * @return 
     */  
    public Object queryOne(String sql,Object[] param)  
      {  
          Object object=null;  
          Session session=null;  
           try  
            {  
                session = HibernateSessionBean.GetInstance().getSession();  
                Query query=session.createQuery(sql);  
                if(param!=null)  
                {  
                    for(int i=0;i<param.length;i++)  
                    {  
                    	query.setParameter("parm"+i, param[i]);       
                    }  
                    
                }  
                object=query.uniqueResult();  
            }  
            catch (Exception e)  
            {  
            }  
            finally  
            {  
                if(session!=null)  
                {  
                    session.close();  
                }  
            }  
          return object;  
      }
    
    public <T> List<T> query(String sql,Object[] param, Integer firstnum, Integer maxnum)  
    {  
        List<T> list=new ArrayList<T>();  
        Session session=null;  
         try  
          {  
              session = HibernateSessionBean.GetInstance().getSession();  
              Query query=session.createQuery(sql);  
              if(param!=null)  
              {  
                  for(int i=0;i<param.length;i++)  
                  {  
                      query.setParameter("parm"+i, param[i]);    
                  }  
              }
              if((firstnum != null)&&(maxnum != null)){
              	query.setFirstResult(firstnum.intValue());
              	query.setMaxResults(maxnum.intValue());
              }
              list=query.list();  
          }  
          catch (Exception e)  
          {  
          }  
          finally  
          {  
              if(session!=null)  
              {  
                  session.close();  
              }  
          }  
        return list;  
    }
} 