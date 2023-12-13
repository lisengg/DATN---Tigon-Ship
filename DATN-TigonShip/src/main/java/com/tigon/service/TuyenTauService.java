package com.tigon.service;

import java.util.List;

import com.tigon.model.Tuyen;

public interface TuyenTauService {

	List<Tuyen> findAll();

	List<Tuyen> findListByTuyen(String fullname);

	Tuyen findByTuyen(String fullname);
}
