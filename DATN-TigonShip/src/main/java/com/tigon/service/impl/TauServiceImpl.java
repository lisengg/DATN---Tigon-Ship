package com.tigon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tigon.dao.TauDAO;
import com.tigon.model.Tau;
import com.tigon.service.TauService;

@Service
public class TauServiceImpl implements TauService {
	
	@Autowired
	TauDAO tdao;
	
	@Override
	public Tau findById(Integer id) {
		// TODO Auto-generated method stub
		return tdao.findById(id).get();
	}
	
}
