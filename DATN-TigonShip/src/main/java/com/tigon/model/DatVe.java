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
@Table(name="DATVE")
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class DatVe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer MADATVE;
    Integer SOGHE;
    @Temporal(TemporalType.DATE)
    Date NGAYDI;
    @Temporal(TemporalType.DATE)
    Date NGAYVE;
    @Temporal(TemporalType.DATE)
    Date NGAYDAT;

    @ManyToOne
	@JoinColumn(name = "IDHANHKHACH")
	HanhKhach HANHKHACH;

    @JsonIgnore
    @OneToMany(mappedBy = "DATVE")
    @JsonBackReference
    List<HoaDon> HoaDon;
    
    @JsonIgnore
    @OneToMany(mappedBy = "DATVE")
    @JsonBackReference
    List<NguoiDiCung> NGUOIDICUNG;
    



}
