package com.tigon.service;

import java.util.List;

import com.tigon.model.HanhKhach;

public interface HanhKhachService {
	public List<HanhKhach> findAll();
	public HanhKhach findById(String username);
	public List<HanhKhach> getAdinstrators();
}
