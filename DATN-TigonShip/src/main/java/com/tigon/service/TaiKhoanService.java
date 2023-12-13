package com.tigon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tigon.model.DatVe;
import com.tigon.model.TaiKhoan;
@Service
public interface TaiKhoanService {
	public List<TaiKhoan> findAll();
	public TaiKhoan findById(Integer username);
	public List<TaiKhoan> getAdminstrators();
	public TaiKhoan findIdByEmailOrPhone(String email);
	public TaiKhoan getAllEmail(String email);
	public TaiKhoan updateTaiKhoan(String hovaten, String sdt, String cccd, String diachi, Integer id);
	public TaiKhoan findByGoogleId(String googleid);
}
