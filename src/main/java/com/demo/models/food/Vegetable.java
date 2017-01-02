package com.demo.models.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class Vegetable extends Food {


	@Override
	@Autowired
	public void setDescription(@Value("I am a Vegetable, I come from plants or trees") String description) {
		this.description = description;
	}
}
