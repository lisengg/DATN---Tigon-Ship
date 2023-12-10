package com.tigon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tigon.model.DatGhe;

@Service
public interface DatGheService {
	List<DatGhe> layDanhSachGheTheoMaDatVe(Integer idMadatve);
}
