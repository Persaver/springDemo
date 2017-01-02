package com.demo.models.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class Meal {

	@Value("#{fruit}")
	private Fruit fruit;

	@Value("#{fruit.name}")
	private String fruitName;

	@Autowired
	private Dairy dairy;
	@Autowired(required=false)
	private Grain grain;
	@Autowired
	private Meat meat;
	@Autowired
	private Vegetable veg;



	public Meal() {

	}

	@Autowired
	public Meal(Fruit fruit, Dairy dairy, Grain grain, Meat meat, Vegetable veg) {
		super();
		this.fruit = fruit;
		this.dairy = dairy;
		this.grain = grain;
		this.meat = meat;
		this.veg = veg;
	}

	public Fruit getFruit() {
		return this.fruit;
	}

	public void setFruit(Fruit fruit) {
		this.fruit = fruit;
	}

	public Dairy getDairy() {
		return this.dairy;
	}

	public void setDairy(Dairy dairy) {
		this.dairy = dairy;
	}


	public Grain getGrain() {
		return this.grain;
	}


	public void setGrain(Grain grain) {
		this.grain = grain;
	}


	public Meat getMeat() {
		return this.meat;
	}


	public void setMeat(Meat meat) {
		this.meat = meat;
	}

	public Vegetable getVeg() {
		return this.veg;
	}


	public void setVeg(Vegetable veg) {
		this.veg = veg;
	}

	public String whatInThisMeal(){

		StringBuilder out = new StringBuilder("This meal contains ");
		if(this.fruit != null){	out.append("some fruit "); 	}
		if(this.fruitName != null){	out.append("Fruit name : " + this.fruitName + " ");	}
		if(this.dairy != null){	out.append("some dairy "); 	}
		if(this.grain != null){	out.append("some grain "); 	}
		if(this.meat != null){	out.append("some meat "); 	}
		if(this.veg != null){	out.append("some vegetables"); 	}

		return out.toString();
	}

	public String whatInThisMealDescription(){

		StringBuilder out = new StringBuilder("This meal contains ");
		if(this.fruit != null){	out.append(this.fruit.talkAboutYourself() + " "); 	}
		if(this.dairy != null){	out.append(this.dairy.talkAboutYourself() + " "); 	}
		if(this.grain != null){	out.append(this.grain.talkAboutYourself() + " "); 	}
		if(this.meat != null){	out.append(this.meat.talkAboutYourself() + " "); 	}
		if(this.veg != null){	out.append(this.veg.talkAboutYourself() + " "); 	}

		return out.toString();
	}


}
