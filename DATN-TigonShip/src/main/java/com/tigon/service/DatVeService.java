package com.tigon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tigon.model.DatVe;
import com.tigon.model.GiaVe;

public interface DatVeService {
	public DatVe getNgayDatMoiNhat(Integer idtaikhoan);
	public List<DatVe> findAll();
	public DatVe findById(Integer id);
	List<DatVe> ListDatVeByIdTaiKhoan(Integer idtaikhoan);
	DatVe FINDIDMAX();
}
