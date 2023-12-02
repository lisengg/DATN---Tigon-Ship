package com.tigon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tigon.dao.BangtinDAO;
import com.tigon.model.Bangtin;
import com.tigon.service.BangtinService;

@Service
public class BangtinServiceImpl  implements BangtinService{

	@Autowired
	BangtinDAO dao;
	@Override
	public List<Bangtin> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public Bangtin findById(Integer id) {
		// TODO Auto-generated method stub
		return dao.findById(id).get();
	}

}
