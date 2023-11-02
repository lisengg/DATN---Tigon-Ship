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
@Table(name="HANGTAU")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class HangTau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer IDHANGTAU;
    String TENHANGTAU;
    String DIACHI;
    String SDT;
    String EMAIL;
    
    @JsonIgnore
    @OneToMany(mappedBy = "HANGTAU")
    @JsonBackReference
    List<Tau> TAU;

    @JsonIgnore
    @OneToMany(mappedBy = "HANGTAU")
    @JsonBackReference
    List<LichSuHangTau> HangTauHistory;
    
}
