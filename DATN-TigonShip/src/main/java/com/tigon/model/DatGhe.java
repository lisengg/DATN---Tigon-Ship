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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DATGHE")
@Entity
public class DatGhe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer IDDATGHE;

	@ManyToOne
	@JoinColumn(name = "IDGHE")
	GheNgoi GHENGOI;

	@ManyToOne
	@JoinColumn(name = "MADATVE")
	DatVe DATVE;

}
