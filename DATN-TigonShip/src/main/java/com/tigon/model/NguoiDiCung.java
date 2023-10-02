package com.tigon.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

    @ManyToOne
	@JoinColumn(name = "MADATVE")
	DatVe DATVE;

    @ManyToOne
	@JoinColumn(name = "IDLOAIHK")
	LoaiHanhKhach LOAIHANHKHACH;
}
