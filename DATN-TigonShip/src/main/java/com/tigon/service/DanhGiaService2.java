package com.tigon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tigon.model.Bangtin;
import com.tigon.model.DanhGia;


@Service
public interface DanhGiaService2 {
	public List<DanhGia> finddanhgiatk (Integer IDTAIKHOAN);
	 List<DanhGia> findListById(Integer id);
		public DanhGia findById(Integer id);
	 
}
