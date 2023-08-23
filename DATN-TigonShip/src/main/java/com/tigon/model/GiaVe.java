package com.tigon.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;

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

	BigDecimal GIA;
	Date NGAYBATDAU;
	Date NGAYKETTHUC;

	@ManyToOne
	@JoinColumn(name = "IDTUYEN")
	Tuyen TUYEN;

	@ManyToOne
	@JoinColumn(name = "IDLOAIVE")
	LoaiVe LOAIVE;

}
