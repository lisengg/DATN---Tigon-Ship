package com.tigon.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name="TAU")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Tau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer IDTAU;
    String TENTAU;
    String TINHTRANG;
    @Temporal(TemporalType.DATE)
	@Column(name = "NGAYNHAP")
	Date NGAYNHAP = new Date();
    Integer SOGHE;

    @ManyToOne
	@JoinColumn(name = "IDHANGTAU")
	HangTau HANGTAU;

    @JsonIgnore
    @OneToMany(mappedBy = "TAU")
    @JsonBackReference
    List<LichTauChay> LICHTAUCHAY;

    @JsonIgnore
    @OneToMany(mappedBy = "TAU")
    @JsonBackReference
    List<GheNgoi> GHENGOI;
}
