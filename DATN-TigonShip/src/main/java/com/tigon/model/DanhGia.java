package com.tigon.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name="DANHGIA")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DanhGia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer  IDDANHGIA;
    Integer DANHGIA;
    String BINHLUAN;
    @Column(name = "NGAYDANHGIA")
	LocalDateTime NGAYDANHGIA = LocalDateTime.now();
    @ManyToOne(optional = true)
    @JoinColumn(name = "IDTUYEN")
    Tuyen TUYEN;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IDTAIKHOAN")
    TaiKhoan TAIKHOAN;

}
