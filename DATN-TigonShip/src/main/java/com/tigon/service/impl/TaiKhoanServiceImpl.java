package com.tigon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tigon.dao.TaiKhoanDAO;
import com.tigon.model.TaiKhoan;
import com.tigon.service.TaiKhoanService;

@Service
public class TaiKhoanServiceImpl implements TaiKhoanService {
	@Autowired
	TaiKhoanDAO dao;

	@Override
	public List<TaiKhoan> findAll() {
		return dao.findAll();
	}

	@Override
	public List<TaiKhoan> getAdminstrators() {
		return dao.getAdministrators();
	}

	@Override
	public TaiKhoan findIdByEmailOrPhone(String email) {
		// TODO Auto-generated method stub
		return dao.findIdByEmailOrPhone(email);
	}

	@Override
	public TaiKhoan findById(Integer username) {
		// TODO Auto-generated method stub
		return dao.findById(username).get();
	}

	@Override
	public TaiKhoan getAllEmail(String email) {
		// TODO Auto-generated method stub
		return dao.getAllEmail(email);
	}

	@Override
	public TaiKhoan updateTaiKhoan(String hovaten, String sdt, String cccd, String diachi, Integer id) {
		// TODO Auto-generated method stub
		return dao.updateTaiKhoan(hovaten, sdt, cccd, diachi, id);
	}

	@Override
	public TaiKhoan findByGoogleId(String googleid) {
		// TODO Auto-generated method stub
		return dao.findByGoogleId(googleid);
	}

}
