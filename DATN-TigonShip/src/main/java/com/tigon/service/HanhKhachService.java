package com.tigon.service;

import java.util.List;

import org.springframework.security.core.userdetails.User;

import com.tigon.model.HanhKhach;

public interface HanhKhachService {
	public List<HanhKhach> findAll();
	public HanhKhach findById(Integer username);
	public List<HanhKhach> getAdminstrators();
	public HanhKhach findIdByEmailOrPhone(String email);
	public User findByUsername(String name);
}
