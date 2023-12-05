package com.tigon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tigon.dao.NguoiDiCungTamDAO;
import com.tigon.model.NguoiDiCungTam;
import com.tigon.service.NguoiDiCungTamService;

@Service
public class NguoiDiCungTamServiceImpl implements NguoiDiCungTamService{

	@Autowired
	NguoiDiCungTamDAO hktdao;

	@Override
	public List<NguoiDiCungTam> findAll() {
		// TODO Auto-generated method stub
		return hktdao.findAll();
	}
	


}
