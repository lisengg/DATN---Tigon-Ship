package com.tigon.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "GIAVE")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class GiaVe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer IDGIAVE;
	String TRANGTHAI;
	BigDecimal GIA;
    @Temporal(TemporalType.DATE)
	@Column(name = "NGAYBATDAU")
	Date NGAYBATDAU = new Date();
    
    @Temporal(TemporalType.DATE)
	@Column(name = "NGAYKETTHUC")
	Date NGAYKETTHUC = new Date();
    
    @ManyToOne
    @JoinColumn(name = "IDLOAIHK")
    LoaiHanhKhach LOAIHK;
    
	@ManyToOne
	@JoinColumn(name = "IDTUYEN")
	Tuyen TUYEN;

	@ManyToOne
	@JoinColumn(name = "IDLOAIVE")
	LoaiVe LOAIVE;

	@ManyToOne
	@JoinColumn(name = "IDLOAIHK")
	LoaiHanhKhach LOAIHK;
}