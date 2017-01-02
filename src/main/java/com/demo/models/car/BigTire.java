package com.demo.models.car;

import org.springframework.stereotype.Component;

@Component("bTire")
public class BigTire implements Tire {

	public BigTire() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTireDiameter() {
		return "20 inch diameter";

	}

}
