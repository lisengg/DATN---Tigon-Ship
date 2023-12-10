package com.tigon.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tigon.dao.LichTauChayDAO;
import com.tigon.model.DatVe;
import com.tigon.model.LichTauChay;
import com.tigon.service.LichTauService;

@Service
public class LichTauServiceImpl implements LichTauService{

	@Autowired
	LichTauChayDAO ltdao;

	@Override
	public List<LichTauChay> findByLichTau(int idtuyen) {
		// TODO Auto-generated method stub
		return ltdao.findByLichTau(idtuyen);
	}

	@Override
	public LichTauChay findByid(Integer idlichtau) {
		// TODO Auto-generated method stub
		return ltdao.findById(idlichtau).get();
	}
	

}
