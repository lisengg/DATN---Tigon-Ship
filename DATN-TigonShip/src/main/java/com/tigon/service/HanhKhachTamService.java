package com.tigon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tigon.model.HanhKhachTam;

@Service
public interface HanhKhachTamService {
	public List<HanhKhachTam> findAll();

}
