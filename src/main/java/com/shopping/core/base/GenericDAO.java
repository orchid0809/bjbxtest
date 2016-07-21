package com.shopping.core.base;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.shopping.core.dao.IGenericDAO;

public class GenericDAO<T>
  implements IGenericDAO<T>
{
  protected  Class<T> entityClass;

  @Autowired
  @Qualifier("genericEntityDao")
  private GenericEntityDao geDao;

  public Class<T> getEntityClass()
  {
    return this.entityClass;
  }

  public void setEntityClass(Class<T> entityClass) {
    this.entityClass = entityClass;
  }

  public GenericEntityDao getGeDao() {
    return this.geDao;
  }

  public void setGeDao(GenericEntityDao geDao) {
    this.geDao = geDao;
  }

  public GenericDAO() {
    this.entityClass = 
      ((Class)((java.lang.reflect.ParameterizedType)getClass()
      .getGenericSuperclass()).getActualTypeArguments()[0]);
  }

  public GenericDAO(Class<T> type)
  {
    this.entityClass = type;
  }

  public int batchUpdate(String jpql, Object[] params)
  {
    return this.geDao.batchUpdate(jpql, params);
  }

  public List executeNamedQuery(String queryName, Object[] params, int begin, int max)
  {
    return this.geDao.executeNamedQuery(queryName, params, begin, max);
  }

  public List executeNativeNamedQuery(String nnq)
  {
    return this.geDao.executeNativeNamedQuery(nnq);
  }

  public List executeNativeQuery(String nnq, Object[] params, int begin, int max)
  {
    return this.geDao.executeNativeQuery(nnq, params, begin, max);
  }

  public int executeNativeSQL(String nnq)
  {
    return this.geDao.executeNativeSQL(nnq);
  }

  public List find(String query, Map params, int begin, int max)
  {
    return getGeDao()
      .find(this.entityClass, query, params, begin, max);
  }

  public void flush()
  {
    this.geDao.flush();
  }

  public T get(Serializable id)
  {
    return (T) getGeDao().get(this.entityClass, id);//.get(this.entityClass, id);
  }

  public T getBy(String propertyName, Object value)
  {
    return (T) getGeDao().getBy(this.entityClass, propertyName, value);
  }

  public List query(String query, Map params, int begin, int max)
  {
    return getGeDao().query(query, params, begin, max);
  }

  public void remove(Serializable id)
  {
    getGeDao().remove(this.entityClass, id);
  }

  public void save(Object newInstance)
  {
    getGeDao().save(newInstance);
  }

  public void update(Object transientObject)
  {
    getGeDao().update(transientObject);
  }
  
//	public List<T> selectUsersByPage(int pageNum, int pageSize,String hql) {
////		Session session = HibernateUtil.getSession();
////		Query q = session.createQuery(hql);
////		q.setFirstResult((pageNum-1)*pageSize); // 每页第一条记录的下标值
////		q.setMaxResults(pageSize);	  			// 每页的记录数		
////		return q.list();
//	}
//
//	public int selectTotalCount(String hql) {
////		Session session = HibernateUtil.getSession();
////		Long count = (Long) session.createQuery(hql).uniqueResult();
////		return count.intValue();
//	}
}