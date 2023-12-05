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
@Table(name="TUYEN")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Tuyen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer IDTUYEN;   
    String TENTUYEN;
    @JsonIgnore
    @OneToMany(mappedBy = "TUYEN")
    @JsonBackReference
    List<LichTauChay> LICHTAUCHAY;

    @JsonIgnore
    @OneToMany(mappedBy = "TUYEN")
    @JsonBackReference
    List<DanhGia> DANHGIA;

    @JsonIgnore
    @OneToMany(mappedBy = "TUYEN")
    @JsonBackReference
    List<GiaVe> GIAVE;

   
}