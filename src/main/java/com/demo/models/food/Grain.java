package com.demo.models.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class Grain extends Food {


	@Override
	@Autowired
	public void setDescription(@Value("I am a Grain, I come cerales") String description) {
		this.description = description;
	}


}
