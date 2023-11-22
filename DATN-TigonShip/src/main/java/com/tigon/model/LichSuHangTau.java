package com.tigon.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
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
@Table(name="LICHSUHANGTAU")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LichSuHangTau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer ID;
    String TEN;
    String THAOTAC;
    
    
	@Column(name = "THOIGIAN")
	LocalDateTime THOIGIAN = LocalDateTime.now();

    @ManyToOne
	@JoinColumn(name = "IDHANGTAU")
	HangTau HANGTAU;
}
