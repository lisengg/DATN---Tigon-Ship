package com.tigon.service;

import org.springframework.stereotype.Service;

import com.tigon.model.Tau;

@Service
public interface TauService {
	public Tau findById(Integer id);
}
