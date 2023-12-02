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
	public DatVe getNgayDatMoiNhat(Integer idhanhkhach) {
		// TODO Auto-generated method stub
		return dao.getNgayDatMoiNhat(idhanhkhach);
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
	public List<DatVe> ListDatVeByidKhach(Integer idhanhkhach) {
		// TODO Auto-generated method stub
		return dao.ListDatVeByidKhach(idhanhkhach);
	}

	@Override
	public DatVe FINDIDMAX() {
		// TODO Auto-generated method stub
		return dao.FINDIDMAX();
	}

}
