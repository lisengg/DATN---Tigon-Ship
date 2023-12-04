package com.tigon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tigon.dao.HanhKhachTamDAO;
import com.tigon.model.HanhKhachTam;
import com.tigon.service.HanhKhachTamService;

@Service
public class HanhKhachTamServiceImpl implements HanhKhachTamService{

	@Autowired
	HanhKhachTamDAO hktdao;

	@Override
	public List<HanhKhachTam> findAll() {
		// TODO Auto-generated method stub
		return hktdao.findAll();
	}
	


}
