package com.tigon.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Table(name="bangtin")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Bangtin {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
	@Column(name = "ngay_dang")
	LocalDateTime ngay_dang = LocalDateTime.now();
   
    String noi_dung;
    String tieu_de;
    String hinh_anh;
}




