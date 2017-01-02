package com.demo.models.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("fruit")
public class Fruit extends Food{

	public Fruit() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Fruit(Integer id, String name, String description) {
		super(id, name, description);
		// TODO Auto-generated constructor stub
	}

	public Fruit(String name, String description) {
		this(null,name, description);
		// TODO Auto-generated constructor stub
	}

	@Override
	@Autowired
	public void setDescription(@Value("I am a Fruit, I come from plants or trees with the seeds") String description) {
		this.description = description;
	}

}
