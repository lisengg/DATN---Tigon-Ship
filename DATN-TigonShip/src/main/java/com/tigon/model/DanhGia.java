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
@Table(name="DANHGIAHANHKHACH")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DanhGia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer IDDANHGIAHK;
    Integer DANHGIA;
    String BINHLUAN;

    @ManyToOne
    @JoinColumn(name="IDTUYEN")
    Tuyen TUYEN;

    @ManyToOne
    @JoinColumn(name="IDHANHKHACH")
    HanhKhach HANHKHACH;
    
}
