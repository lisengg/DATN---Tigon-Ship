package com.tigon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tigon.dao.DanhGiaDAO;

import com.tigon.model.DanhGia;
import com.tigon.model.TaiKhoan;

import java.util.List;

@Service
public class DanhGiaService {

	@Autowired
	private DanhGiaDAO danhGiaDAO;



	public List<DanhGia> getAllDanhGia() {
		return danhGiaDAO.findAll();
	}

	public void saveDanhGia(DanhGia danhGia) {

		danhGiaDAO.save(danhGia);
	}
	
}