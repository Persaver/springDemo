package com.demo.models.food;

public interface FoodGroup {
	public String talkAboutYourself();
	public void setId(Integer id);
	public void setName(String Name);
	public void setDescription(String description);

	public Integer getId();
	public String getName();
	public String getDescription();
}
