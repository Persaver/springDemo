package com.demo.models.car;

import javax.inject.Named;

import com.demo.annot.RandomDemoAnnotation;

//@Component("sTire")
@RandomDemoAnnotation
@Named("sTire")
public class SmallTire implements Tire {

	public SmallTire() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTireDiameter() {
		return "14 inch diameter";
	}

}
