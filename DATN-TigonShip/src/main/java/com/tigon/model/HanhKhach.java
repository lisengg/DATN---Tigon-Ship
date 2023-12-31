package com.tigon.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name="TAIKHOAN")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class HanhKhach {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer IDHANHKHACH;
    String HOVATEN;
    String SDT;
    String CCCD;
    String EMAIL;
    String QUOCTICH;
    @Temporal(TemporalType.DATE)
    Date NGAYSINH;
  
}
