package com.demo.models.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class Dairy extends Food {

	@Override
	@Autowired
	public void setDescription(@Value("I am a Dairy, I come from I don't know") String description) {
		this.description = description;
	}
}
