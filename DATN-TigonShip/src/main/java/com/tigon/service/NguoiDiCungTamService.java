package com.tigon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tigon.model.NguoiDiCungTam;

@Service
public interface NguoiDiCungTamService {
	public List<NguoiDiCungTam> findAll();

}
