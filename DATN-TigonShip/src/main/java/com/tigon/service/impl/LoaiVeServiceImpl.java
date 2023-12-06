package com.tigon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tigon.dao.LoaiVeDAO;
import com.tigon.model.LoaiVe;
import com.tigon.service.LoaiVeService;

@Service
public class LoaiVeServiceImpl implements LoaiVeService {
	@Autowired
	LoaiVeDAO dao;

	@Override
	public LoaiVe findById(Integer id) {
		// TODO Auto-generated method stub
		return dao.findById(id).get();
	}

}
