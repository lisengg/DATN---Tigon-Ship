package com.tigon.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name="LICHSUDATVE")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LichSuDatVe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer ID;
    String TEN;
    String THAOTAC;
    
	@Column(name = "THOIGIAN")
	LocalDateTime THOIGIAN = LocalDateTime.now();
}
