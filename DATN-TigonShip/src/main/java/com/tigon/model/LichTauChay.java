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
@Table(name="LICHTAUCHAY")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LichTauChay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer IDLICHTAU;
    String GIOXUATPHAT;
    String GIODENNOI;
    String TRANGTHAI;
    @ManyToOne
    @JoinColumn(name="IDTAU")
    Tau TAU;

    @ManyToOne
    @JoinColumn(name="IDTUYEN")
    Tuyen TUYEN;

    @JsonIgnore
    @OneToMany(mappedBy = "LICHTAUCHAY")
    @JsonBackReference
    List<DatVe> DATVE;


    
}
