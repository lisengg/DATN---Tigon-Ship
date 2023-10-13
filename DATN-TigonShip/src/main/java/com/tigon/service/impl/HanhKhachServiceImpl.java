package com.tigon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.tigon.dao.HanhKhachDAO;
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
	public User findByUsername(String name) {
		// TODO Auto-generated method stub
		return null;
	}






}
