package com.demo.models.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class Meat extends Food {


	@Override
	@Autowired
	public void setDescription(@Value("I am a Meat, I come from animals") String description) {
		this.description = description;
	}

}
