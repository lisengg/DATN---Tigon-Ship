package com.tigon.model;

import java.math.BigDecimal;
import java.util.Date;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name="GIAVE")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class GiaVe {

    BigDecimal GIA;
    Date NGAYBATDAU;
    Date NGAYKETTHUC;

    @ManyToOne
    @JoinColumn(name = "IDGIAVE")
    Tuyen TUYEN;

    @ManyToOne
    @JoinColumn(name = "IDGIAVE")
    LoaiVe LOAIVE;


}
