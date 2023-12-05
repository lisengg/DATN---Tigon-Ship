package com.tigon.model;

import java.time.LocalDateTime;
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
@Table(name="BANGTIN")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Bangtin {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer IDBANGTIN;
	@Column(name = "NGAYDANG")
	LocalDateTime NGAYDANG = LocalDateTime.now();
    String NOIDUNG;
    String TIEUDE;
    String HINHANH;
    @ManyToOne
    @JoinColumn(name="IDTAIKHOAN")
    TaiKhoan TAIKHOAN;
}




