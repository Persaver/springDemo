package com.demo.utils;

import java.util.ArrayList;
import java.util.Random;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class DataSource extends DriverManagerDataSource{


	public static String getAppleTypeStatic() {
		ArrayList<String> types = new ArrayList<String>();

		types.add("State Fair");
		types.add("McIntosh");
		types.add("Honeycrisp");
		types.add("Regent");
		types.add("Pinata");
		types.add("Granny Smith");

		int index = new Random().nextInt(types.size());
		String type = types.get(index);

		//return type;
		return null;
	}

	public String getAppleType() {
		ArrayList<String> types = new ArrayList<String>();

		types.add("State Fair");
		types.add("McIntosh");
		types.add("Honeycrisp");
		types.add("Regent");
		types.add("Pinata");
		types.add("Granny Smith");

		int index = new Random().nextInt(types.size());
		String type = types.get(index);

		return type;
	}
}
