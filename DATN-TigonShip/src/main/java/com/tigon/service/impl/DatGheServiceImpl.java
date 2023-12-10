package com.tigon.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tigon.dao.DatGheDAO;
import com.tigon.dao.DatVeDAO;
import com.tigon.model.DatGhe;
import com.tigon.service.DatGheService;

@Service
public class DatGheServiceImpl implements DatGheService {
	@Autowired
	DatGheDAO dao;

	@Override
	public List<DatGhe> layDanhSachGheTheoMaDatVe(Integer idMadatve) {
		// TODO Auto-generated method stub
		return dao.layDanhSachGheTheoMaDatVe(idMadatve);
	}

	@Override
	public int countDatGheTimTuyen(Date ngayDi, Integer idtau, Integer Idtuyen) {
		// TODO Auto-generated method stub
		return dao.countDatGheTimTuyen(ngayDi, idtau, Idtuyen);
	}

}
