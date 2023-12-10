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
@Table(name="GHENGOI")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class GheNgoi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer IDGHE;
    String TENGHE;
    Integer KHOANG;
    String TRANGTHAI;

    @ManyToOne
	@JoinColumn(name = "IDTAU")
	Tau TAU;

}
