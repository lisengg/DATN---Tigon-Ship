package com.tigon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tigon.dao.NguoiDiCungDAO;
import com.tigon.model.NguoiDiCung;
import com.tigon.service.NguoiDiCungService;
@Service
public class NguoiDiCungServiceImpl implements NguoiDiCungService{

	@Autowired
	NguoiDiCungDAO dao;
	
	@Override
	public List<NguoiDiCung> ListNguoiDiCungByiddatve(Integer id) {
		// TODO Auto-generated method stub
		return dao.ListNguoiDiCungByiddatve(id);
	}

}
