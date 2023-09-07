package com.tigon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tigon.dao.TuyenDAO;
import com.tigon.model.Tuyen;
import com.tigon.service.TuyenService;
@Service
public class TuyenServiceimpl implements TuyenService{
	@Autowired
	TuyenDAO tuyenDAO;

	

	@Override
	public List<Tuyen> searchTuyen(String keyword) {
		// TODO Auto-generated method stub
		return this.tuyenDAO.searchTuyen(keyword);
	}
	
	@Override
	public Page<Tuyen> getAll(Integer pageNo) {
		Pageable pageable = PageRequest.of(pageNo-1,4);
		return this.tuyenDAO.findAll(pageable);
	}
}
