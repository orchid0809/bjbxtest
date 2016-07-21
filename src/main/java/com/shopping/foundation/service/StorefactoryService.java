package com.shopping.foundation.service;

import com.shopping.core.dao.IGenericDAO;
import com.shopping.core.query.GenericPageList;
import com.shopping.core.query.PageObject;
import com.shopping.core.query.support.IPageList;
import com.shopping.core.query.support.IQueryObject;
import com.shopping.foundation.domain.Storefactory;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StorefactoryService {

	@Resource(name = "storefactoryDAO")
	private IGenericDAO<Storefactory> storefactoryDao;

	public boolean save(Storefactory storefactory) {
		try {
			this.storefactoryDao.save(storefactory);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Storefactory getObjById(Long id) {
		System.out.println("+++++");
		Storefactory storefactory = (Storefactory) this.storefactoryDao.get(id);
		System.out.println("+++++" + storefactory.getPostcode());
		if (storefactory != null) {
			return storefactory;
		}
		return null;
	}

	public boolean delete(Long id) {
		try {
			this.storefactoryDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean batchDelete(List<Serializable> storeIds) {
		for (Serializable id : storeIds) {
			delete((Long) id);
		}
		return true;
	}

	public IPageList list(IQueryObject properties) {
		if (properties == null) {
			return null;
		}
		String query = properties.getQuery();
		Map params = properties.getParameters();
		GenericPageList pList = new GenericPageList(Storefactory.class, query,
				params, this.storefactoryDao);
		if (properties != null) {
			PageObject pageObj = properties.getPageObj();
			if (pageObj != null)
				pList.doList(pageObj.getCurrentPage() == null ? 0 : pageObj
						.getCurrentPage().intValue(),
						pageObj.getPageSize() == null ? 0 : pageObj
								.getPageSize().intValue());
		} else {
			pList.doList(0, -1);
		}
		return pList;
	}

	public boolean update(Storefactory storefactory) {
		try {
			this.storefactoryDao.update(storefactory);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Storefactory> query(String query, Map params, int begin, int max) {
		return this.storefactoryDao.query(query, params, begin, max);
	}

	public Storefactory getObjByProperty(String propertyName, Object value) {
		return (Storefactory) this.storefactoryDao.getBy(propertyName, value);
	}
	
	public int executeNativeSQL(String sql){
		return this.storefactoryDao.executeNativeSQL(sql);
	}
}
