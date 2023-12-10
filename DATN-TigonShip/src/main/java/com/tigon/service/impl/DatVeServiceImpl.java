package com.tigon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tigon.dao.DatVeDAO;
import com.tigon.model.DatVe;
import com.tigon.service.DatVeService;
@Service
public class DatVeServiceImpl implements DatVeService {
	@Autowired
	DatVeDAO dao;

	@Override
	public DatVe getNgayDatMoiNhat(Integer idtaikhoan) {
		// TODO Auto-generated method stub
		return dao.getNgayDatMoiNhat(idtaikhoan);
	}

	@Override
	public List<DatVe> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public DatVe findById(Integer id) {
		// TODO Auto-generated method stub
		return dao.findById(id).get();
	}

	@Override
	public List<DatVe> ListDatVeByIdTaiKhoan(Integer idtaikhoan) {
		// TODO Auto-generated method stub
		return dao.ListDatVeByIdTaiKhoan(idtaikhoan);
	}

	@Override
	public DatVe FINDIDMAX() {
		// TODO Auto-generated method stub
		return dao.FINDIDMAX();
	}

}
