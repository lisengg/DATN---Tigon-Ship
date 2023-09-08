package com.tigon.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tigon.model.Tuyen;

public interface TuyenService {
	List<Tuyen> searchTuyen(String keyword);
	Page<Tuyen> getAll(Integer pageNo);
}
