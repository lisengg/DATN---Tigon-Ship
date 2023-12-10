package com.tigon.service;

import org.springframework.stereotype.Service;

import com.tigon.model.GheNgoi;

@Service
public interface GheNgoiService {

	public GheNgoi findByid(Integer IDGHE);
}

