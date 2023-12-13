package com.tigon.service;

import org.springframework.stereotype.Service;

import com.tigon.model.LoaiVe;

import lombok.Setter;

@Service
public interface LoaiVeService {
public LoaiVe findById(Integer id);
}
