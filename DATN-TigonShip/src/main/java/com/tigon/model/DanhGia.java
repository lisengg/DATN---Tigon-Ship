package com.tigon.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name="DANHGIA")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DanhGia {
    Integer DANHGIA;
    String BINHLUAN;

    @ManyToOne
    @JoinColumn(name="IDTUYEN")
    Tuyen TUYEN;

    @ManyToOne
    @JoinColumn(name="IDHANHKHACH")
    HanhKhach HANHKHACH;
    
}
