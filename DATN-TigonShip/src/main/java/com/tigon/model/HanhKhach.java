package com.tigon.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name="HANHKHACH")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class HanhKhach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer IDHANHKHACH;
    String MATKHAU;
    String HOVATEN;
    String SDT;
    String EMAIL;
    String DIACHI;
    String CCCD;
    String QUYEN;

    @ManyToOne
    @JoinColumn(name = "IDLOAIHK")
    LoaiHanhKhach LOAIHANHKHACH;

    @JsonIgnore
    @OneToMany(mappedBy = "HANHKHACH")
    @JsonBackReference
    List<DanhGia> DANHGIA;

    
    
}
