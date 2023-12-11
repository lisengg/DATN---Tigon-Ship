package com.tigon.service;

import org.springframework.stereotype.Service;

import com.tigon.model.HoaDon;

@Service
public interface HoaDonService {
	public HoaDon findByMaDatVe(Integer madatve);
	HoaDon findMaxDatVe();
}
