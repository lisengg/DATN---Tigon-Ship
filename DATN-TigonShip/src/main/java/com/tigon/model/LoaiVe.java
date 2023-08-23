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
@Table(name="GIAVE")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LoaiVe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer IDLOIAVE;
    String LOAIVE;

    @JsonIgnore
    @OneToMany(mappedBy = "LOAIVE")
    @JsonBackReference
    List<DatVe> DATVE;

    @JsonIgnore
    @OneToMany(mappedBy = "LOAIVE")
    @JsonBackReference
    List<GiaVe> GIAVE;
}
