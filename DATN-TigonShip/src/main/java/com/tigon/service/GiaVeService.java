package com.tigon.service;

import org.springframework.stereotype.Service;

import com.tigon.model.GiaVe;
@Service
public interface GiaVeService {
	public GiaVe findByIdTuyenIdLoaiVe(Integer tuyen, Integer loaive);
}
