package com.tigon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tigon.model.HanhKhachTam;

public interface HanhKhachTamDAO extends JpaRepository<HanhKhachTam, Integer>{

}
