package com.tigon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tigon.dao.DanhGiaDAO;
import com.tigon.model.DanhGia;
import com.tigon.service.DanhGiaService2;

@Service
public class DanhGiaServiceImlp2 implements DanhGiaService2{

	@Autowired
	DanhGiaDAO dao;
	
	@Override
	public List<DanhGia> finddanhgiatk(Integer IDTAIKHOAN) {
		// TODO Auto-generated method stub
		return dao.finddanhgiatk(IDTAIKHOAN);
	}

	@Override
	public List<DanhGia> findListById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DanhGia findById(Integer id) {
		// TODO Auto-generated method stub
		return dao.findById(id).get();
	}

}
