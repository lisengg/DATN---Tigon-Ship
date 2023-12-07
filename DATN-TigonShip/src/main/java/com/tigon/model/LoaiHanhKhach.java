package com.tigon.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name="LOAIHK")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LoaiHanhKhach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer IDLOAIHK;
    String LOAIHK;

    @JsonIgnore
    @OneToMany(mappedBy = "LOAIHK")
    @JsonBackReference
    List<TaiKhoan> TAIKHOAN;

    @JsonIgnore
    @OneToMany(mappedBy = "LOAIHK")
    @JsonBackReference
    List<NguoiDiCung> NGUOIDICUNG;

}
