package com.tigon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tigon.dao.LoaiHanhKhachDAO;
import com.tigon.model.LoaiHanhKhach;
import com.tigon.service.LoaiHanhKhachService;

@Service
public class LoaiHanhKhachServiceImpl implements LoaiHanhKhachService{

	@Autowired
	LoaiHanhKhachDAO dao;
	
	@Override
	public LoaiHanhKhach getIdNguoiLon() {
		// TODO Auto-generated method stub
		return dao.getIdNguoiLon();
	}

}
