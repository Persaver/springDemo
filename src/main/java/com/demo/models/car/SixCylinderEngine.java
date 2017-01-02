package com.demo.models.car;

//@Component("sixCyl")
public class SixCylinderEngine implements Engine {

	@Override
	public int getNumberOfCylinder() {
		return 6;
	}

	@Override
	public String drive() {
		return "Driving with 6 cylinder";

	}

}
