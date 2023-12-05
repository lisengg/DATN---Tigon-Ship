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
@Table(name="OTP")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OTP {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer IDOTP;
    String MAOTP;
    @ManyToOne
    @JoinColumn(name="IDTAIKHOAN")
    TaiKhoan TAIKHOAN;
}
