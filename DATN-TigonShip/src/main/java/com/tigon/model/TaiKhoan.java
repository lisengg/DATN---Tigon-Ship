package com.tigon.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name="TAIKHOAN")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer IDTAIKHOAN;
    String MATKHAU;
    String HOVATEN;
    String SDT;
    String EMAIL;
    String DIACHI;
    String CCCD;
    String VAITRO;
    String QUOCTICH;
    @Temporal(TemporalType.DATE)
    Date NGAYSINH;
    @ManyToOne
    @JoinColumn(name = "IDLOAIHK")
    LoaiHanhKhach LOAIHANHKHACH;

    @JsonIgnore
    @OneToMany(mappedBy = "TAIKHOAN")
    @JsonBackReference
    List<DanhGia> DANHGIA;

    
    
}
