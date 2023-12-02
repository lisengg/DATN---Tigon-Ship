package com.tigon.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name="HANHKHACHTAM")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class HanhKhachTam {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer IDTAM;
    String HOVATEN;
    String SDT;
    String CCCD;
    Integer MAHK;
}
