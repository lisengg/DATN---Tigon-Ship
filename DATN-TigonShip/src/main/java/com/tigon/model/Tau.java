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
public class Tau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer MATAU;
    String TENTAU;
    String TINHTRANG;
    Date NGAYNHAP;
    Integer SOGHE;

    @ManyToOne
	@JoinColumn(name = "IDHANGTAU")
	HangTau HANGTAU;

    @JsonIgnore
    @OneToMany(mappedBy = "TAU")
    @JsonBackReference
    List<LichTauChay> LICHTAUCHAY;

   

    

}
