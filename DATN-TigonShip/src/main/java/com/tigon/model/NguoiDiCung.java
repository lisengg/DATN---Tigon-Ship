package com.tigon.model;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Table(name="NGUOIDICUNG")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class NguoiDiCung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer IDNGUOIDICUNG;
    String HOVATEN;
    String SDT;
    String CCCD;
    String QUOCTICH;
    @Temporal(TemporalType.DATE)
    Date NGAYSINH;
    @ManyToOne
	@JoinColumn(name = "MADATVE")
	DatVe DATVE;

    @ManyToOne
	@JoinColumn(name = "IDLOAIHK")
	LoaiHanhKhach LOAIHANHKHACH;
}
