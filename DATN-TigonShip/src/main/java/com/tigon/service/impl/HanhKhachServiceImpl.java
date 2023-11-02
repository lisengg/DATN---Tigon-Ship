package com.tigon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tigon.dao.HanhKhachDAO;
import com.tigon.model.DatVe;
import com.tigon.model.HanhKhach;
import com.tigon.service.HanhKhachService;

@Service
public class HanhKhachServiceImpl implements HanhKhachService {
	@Autowired
	HanhKhachDAO dao;

	@Override
	public List<HanhKhach> findAll() {
		return dao.findAll();
	}

	@Override
	public List<HanhKhach> getAdminstrators() {
		return dao.getAdministrators();
	}

	@Override
	public HanhKhach findIdByEmailOrPhone(String email) {
		// TODO Auto-generated method stub
		return dao.findIdByEmailOrPhone(email);
	}

	@Override
	public HanhKhach findById(Integer username) {
		// TODO Auto-generated method stub
		return dao.findById(username).get();
	}

	@Override
	public HanhKhach getAllEmail(String email) {
		// TODO Auto-generated method stub
		return dao.getAllEmail(email);
	}

	@Override
	public HanhKhach updateHanhKhach(String hovaten, String sdt, String cccd, String diachi, Integer id) {
		// TODO Auto-generated method stub
		return dao.updateHanhKhach(hovaten, sdt, cccd, diachi, id);
	}

}
