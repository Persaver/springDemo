package com.demo.models.food;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;

public abstract class Food implements FoodGroup{

	protected static final Logger LOG = LoggerFactory.getLogger(Food.class);
	protected Integer id;
	protected String name;
	protected Integer nutritionalRatinng;
	protected String description = "not set";

	public Food() {
	}
	public Food(String name, String description){
		this(null,name,description);
	}
	public Food(Integer id, String name, String description) {
		this.setId(id);
		this.setName(name);
		this.setDescription(description);
	}

	@Override
	public String getName() {
		return this.name;
	}


	@Override
	public Integer getId() {
		return this.id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	@Autowired
	public void setName(@Value("#{T(com.demo.utils.DataSource).getAppleTypeStatic()?.toUpperCase() + 'Apple'}") String name) {
		this.name = name;
	}

	public int getNutritionalRatinng() {
		return this.nutritionalRatinng;
	}

	@Autowired
	public void setNutritionalRatinng(@Value("#{T(java.lang.Math).random() * 10 + dataSource.getAppleType().length()}")int nutritionalRatinng) {
		this.nutritionalRatinng = nutritionalRatinng;
	}

	@Override
	public String talkAboutYourself() {
		String about = this.getDescription();
		if(this.name != null) {	about += "My name is " + this.getName() + "\n";	}
		if(this.nutritionalRatinng != null) {	about += "My nutritional rating is " + this.getNutritionalRatinng();	}
		if(this.id != null) {	about += " My Id is " + this.getId();	}
		return about;
	};

	@Override
	@Required
	public abstract void setDescription(String description);

	@Override
	public String getDescription() {
		return this.description;
	}

	@PostConstruct
	public void initMethod() {
		LOG.info("The bean is ready to go {}" , this.getDescription() );
	}

	@PreDestroy
	public void destroyMethod() {
		LOG.info("The bean is about to be destriy");
	}


}
