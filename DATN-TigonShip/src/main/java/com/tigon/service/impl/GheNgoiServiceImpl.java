package com.tigon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tigon.dao.GheNgoiDAO;
import com.tigon.model.GheNgoi;
import com.tigon.service.GheNgoiService;

@Service
public class GheNgoiServiceImpl implements GheNgoiService{

	@Autowired
	GheNgoiDAO ghdao;
	
	@Override
	public GheNgoi findByid(Integer IDGHE) {
		// TODO Auto-generated method stub
		return ghdao.findById(IDGHE).get();
	}
	
}
