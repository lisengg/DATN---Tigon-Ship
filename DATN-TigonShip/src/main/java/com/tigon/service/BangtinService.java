package com.tigon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tigon.model.Bangtin;
import com.tigon.model.TaiKhoan;

@Service
public interface BangtinService {
	public List<Bangtin> findAll();

	public Bangtin findById(Integer id);

	public Bangtin findBytieude(String TIEUDE);
}