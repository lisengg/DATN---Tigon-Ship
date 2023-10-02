package com.tigon.service;

import java.util.List;

import com.tigon.model.HanhKhach;

public interface HanhKhachService {
	public List<HanhKhach> findAll();
	public HanhKhach findById(Integer username);
	public List<HanhKhach> getAdminstrators();
	public HanhKhach findIdByEmailOrPhone(String email);
	public HanhKhach getAllEmail(String email);
	public HanhKhach updateHanhKhach(String hovaten, String sdt, String cccd, String diachi, Integer id);
}
