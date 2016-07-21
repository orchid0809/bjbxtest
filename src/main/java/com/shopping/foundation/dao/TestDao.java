package com.shopping.foundation.dao;

import org.springframework.stereotype.Repository;

import com.shopping.core.base.GenericDAO;
import com.shopping.foundation.domain.Test;

@Repository("testDAO")
public class TestDao extends GenericDAO<Test>{

}
