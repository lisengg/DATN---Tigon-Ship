package com.tigon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tigon.model.NguoiDiCung;
@Service
public interface NguoiDiCungService {
	List<NguoiDiCung> ListNguoiDiCungByiddatve(Integer id);

}
