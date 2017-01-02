package com.demo.models.car;

import javax.inject.Named;

@Named("fourCyl")
public class FourCylinderEngine implements Engine {

	public FourCylinderEngine() {
	}

	@Override
	public int getNumberOfCylinder() {
		return 4;
	}

	@Override
	public String drive() {
		return "Driving efficiently with 4 cylinder";
	}

}
