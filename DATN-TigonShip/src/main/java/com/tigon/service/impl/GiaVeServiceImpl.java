package com.tigon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tigon.dao.GiaVeDAO;
import com.tigon.model.GiaVe;
import com.tigon.service.GiaVeService;

@Service
public class GiaVeServiceImpl implements GiaVeService {
	@Autowired
	GiaVeDAO dao;

	@Override
	public GiaVe findByIdTuyenIdLoaiVe(Integer tuyen, Integer loaive) {
		// TODO Auto-generated method stub
		return dao.findByIdTuyenIdLoaiVe(tuyen, loaive);
	}

	@Override
	public GiaVe findByIdTuyenIdLoaiVeTreEm(Integer tuyen, Integer loaive) {
		// TODO Auto-generated method stub
		return dao.findByIdTuyenIdLoaiVeTreEm(tuyen, loaive);
	}

	@Override
	public GiaVe findByIdTuyenIdLoaiVeTongTien(Integer tuyen, Integer loaihk, Integer loaive) {
		// TODO Auto-generated method stub
		return dao.findByIdTuyenIdLoaiVeTongTien(tuyen, loaihk, loaive);
	}

}
