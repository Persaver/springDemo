package com.demo.models.car;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("familyCar")
public class FamilyCar {

	@Autowired
	@Qualifier("sTire")
	private Tire frontLeft;
	@Autowired
	@Qualifier("sTire")
	private Tire frontRight;
	@Inject
	@Named("bTire")
	private Tire rearLeft;
	@Resource(name="bTire")
	//@Qualifier("bTire")
	private Tire rearRight;

	@Autowired(required=false)
	@Qualifier("fourCyl")
	private Engine engineType;

	public Engine getEngine() {
		return this.engineType;
	}

	public void setEngine(Engine engine) {
		this.engineType = engine;
	}

	public String getCarDescription(){
		Boolean hasDescription =false;
		StringBuilder out = new StringBuilder("This car contains ");

		if(this.frontLeft != null){	out.append("frontLeft Tire " + this.frontLeft.getTireDiameter()); hasDescription = true;}
		if(this.frontRight != null){	out.append("frontRight Tire " + this.frontRight.getTireDiameter()); hasDescription = true;}
		if(this.rearLeft != null){	out.append("rearLeft Tire " + this.rearLeft.getTireDiameter()); hasDescription = true;}
		if(this.rearRight != null){	out.append("rearRight Tire " + this.rearRight.getTireDiameter()); hasDescription = true;}
		if(this.engineType != null){	out.append("engineType " + this.engineType.drive()); hasDescription = true;}


		return hasDescription ? out.toString() : "not set";
	}

}
