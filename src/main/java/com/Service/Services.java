package com.Service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.Dao.DaoImpl.BaseDaoImpl;

@Service
public class  Services{

	@Resource
	protected BaseDaoImpl bd;
}
