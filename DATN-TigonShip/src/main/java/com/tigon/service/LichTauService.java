package com.tigon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tigon.model.DatVe;
import com.tigon.model.LichTauChay;

@Service
public interface LichTauService {

	List<LichTauChay> findByLichTau(int idtuyen);

	public LichTauChay findByid(Integer idlichtau);


}
