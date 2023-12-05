package com.tigon.model;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
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
@Table(name="HOADON")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer MAHD;
    BigDecimal TONGTIEN;
    String TRANGTHAI;
    String LOAITHANHTOAN;
    @Temporal(TemporalType.DATE)
   	@Column(name = "NGAYLAP")
   	Date NGAYLAP = new Date();
    
    @OneToOne
	@JoinColumn(name = "MADATVE")
	DatVe DATVE;

}
