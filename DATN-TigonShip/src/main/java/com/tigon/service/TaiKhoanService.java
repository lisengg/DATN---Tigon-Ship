package com.tigon.service;

import java.util.List;

import com.tigon.model.DatVe;
import com.tigon.model.TaiKhoan;

public interface TaiKhoanService {
	public List<TaiKhoan> findAll();
	public TaiKhoan findById(Integer username);
	public List<TaiKhoan> getAdminstrators();
	public TaiKhoan findIdByEmailOrPhone(String email);
	public TaiKhoan getAllEmail(String email);
	public TaiKhoan updateHanhKhach(String hovaten, String sdt, String cccd, String diachi, Integer id);
}
