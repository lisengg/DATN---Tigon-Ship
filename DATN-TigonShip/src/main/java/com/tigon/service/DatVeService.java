package com.tigon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tigon.model.DatVe;

public interface DatVeService {
	public DatVe getNgayDatMoiNhat(Integer idhanhkhach);
	public List<DatVe> findAll();
	public DatVe findById(Integer id);
	List<DatVe> ListDatVeByidKhach(Integer idhanhkhach);
	DatVe FINDIDMAX();
}
