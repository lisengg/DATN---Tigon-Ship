package com.tigon.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name="NGUOIDICUNGTAM")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class NguoiDiCungTam {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer IDTAM;
    String HOVATEN;
    String SDT;
    String CCCD;
    Integer IDLOAIKH;
    String QUOCTICH;
    @Temporal(TemporalType.DATE)
    Date NGAYSINH;
}
