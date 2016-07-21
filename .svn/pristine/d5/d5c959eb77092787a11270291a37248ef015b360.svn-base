package com.shopping.foundation.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.core.dao.IGenericDAO;
import com.shopping.core.query.GenericPageList;
import com.shopping.core.query.PageObject;
import com.shopping.core.query.support.IPageList;
import com.shopping.core.query.support.IQueryObject;
import com.shopping.foundation.domain.MessageBoard;

@Service
@Transactional
public class MessageBoardService {
	@Resource(name = "MessageBoardDAO")
	private IGenericDAO<MessageBoard> MessageBoardDao;

	public boolean save(MessageBoard MessageBoard) {
		try {
			this.MessageBoardDao.save(MessageBoard);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public MessageBoard getObjById(Long id) {
		MessageBoard MessageBoard = (MessageBoard) this.MessageBoardDao.get(id);
		if (MessageBoard != null) {
			return MessageBoard;
		}
		return null;
	}

	public boolean delete(Long id) {
		try {
			this.MessageBoardDao.remove(id);
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
		GenericPageList pList = new GenericPageList(MessageBoard.class, query,
				params, this.MessageBoardDao);
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

	public boolean update(MessageBoard MessageBoard) {
		try {
			this.MessageBoardDao.update(MessageBoard);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<MessageBoard> query(String query, Map params, int begin, int max) {
		return this.MessageBoardDao.query(query, params, begin, max);
	}

	public MessageBoard getObjByProperty(String propertyName, Object value) {
		return (MessageBoard) this.MessageBoardDao.getBy(propertyName, value);
	}
	
	public int executeNativeSQL(String sql){
		return this.MessageBoardDao.executeNativeSQL(sql);
	}
	
	public List executeNativeNamedQuery(String sql){
		return this.MessageBoardDao.executeNativeNamedQuery(sql);
	}
}
