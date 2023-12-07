package com.tigon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tigon.dao.TuyenDAO;
import com.tigon.model.Tuyen;
import com.tigon.service.TuyenTauService;

@Service
public class TuyenTauServiceImpl implements TuyenTauService{
	@Autowired
	TuyenDAO tdao;

	@Override
	public List<Tuyen> findAll() {
		// TODO Auto-generated method stub
		return tdao.findAll();
	}

	@Override
	public Tuyen findByTuyen(String fullname) {
		// TODO Auto-generated method stub
		return tdao.findByTuyen(fullname);
	}
}
