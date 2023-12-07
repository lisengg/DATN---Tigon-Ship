package com.tigon.payment;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class paymentDTO implements Serializable{
	private String Status;
	private String messages;
	private String URL;
}
