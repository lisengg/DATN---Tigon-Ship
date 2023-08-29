package com.tigon.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Table(name="HANGTAU")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class HangTau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer IDHANGTAU;

    String TENHANGTAU;
    String DIACHI;
    String SDT;
    String EMAIL;
    
}
