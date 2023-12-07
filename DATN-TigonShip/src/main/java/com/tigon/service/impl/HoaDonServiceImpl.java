package com.tigon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tigon.dao.HoaDonDAO;
import com.tigon.model.HoaDon;
import com.tigon.service.HoaDonService;

@Service
public class HoaDonServiceImpl implements HoaDonService {
	@Autowired
	HoaDonDAO dao;

	@Override
	public HoaDon findByMaDatVe(Integer madatve) {
		// TODO Auto-generated method stub
		return dao.findByMaDateVe(madatve);
	}
	
}
