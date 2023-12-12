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
import javax.persistence.criteria.CriteriaBuilder.In;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="DATGHE")
@Entity
public class DatGhe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer IDDATGHE;
	String IDTUYEN;
	@Temporal(TemporalType.DATE)
    Date THOIGIAN;
    @ManyToOne
	@JoinColumn(name = "IDGHE")
	GheNgoi GHENGOI;

    @ManyToOne
	@JoinColumn(name = "IDTAU")
	Tau TAU;

    @ManyToOne
	@JoinColumn(name = "IDDATVE")
	DatVe DATVE;

}
