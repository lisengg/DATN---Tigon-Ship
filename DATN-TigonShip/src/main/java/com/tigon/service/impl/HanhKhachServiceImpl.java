package com.tigon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public HanhKhach findById(String username) {
		return dao.findById(username).get();
	}

	@Override
	public List<HanhKhach> getAdinstrators() {
		return dao.getAdministrators();
	}

}
