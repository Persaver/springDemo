package com.demo.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.DAO.FoodGroupDAO;
import com.demo.models.food.FoodGroup;
import com.demo.models.food.Fruit;

@Controller
public class DataController {
	private static final Logger LOG = LoggerFactory.getLogger(DataController.class);

	@Autowired
	private ApplicationContext appContext;

	@RequestMapping("/DAO")
	public String getDao(Model model) {
		LOG.info("/DAO");
		try{
			FoodGroupDAO dao = this.appContext.getBean(FoodGroupDAO.class);

			//		FoodGroup fg = new Fruit(8,"DigitalMeatUpdate","I come from the update");
			//
			//
			//		dao.update(fg);

			//dao.addFoodGoup("fishes", "I come from the sea or the river");

			//			FoodGroup a = new Fruit("Ananas","I come from the sun"); 
			//			FoodGroup b = new Fruit("Banana","I come from the sun too"); 
			//			FoodGroup c = new Fruit("citrouille","I come from the rain"); 
			//
			//			List<FoodGroup> foodGroups = new ArrayList<FoodGroup>();
			//
			//			foodGroups.add(a);
			//			foodGroups.add(b);
			//			foodGroups.add(c);
			//
			//			dao.createFoodGroups(foodGroups);

			//			FoodGroup simpleInsert = new Fruit("simpleInsert3","I come from the easy way"); 
			//
			//			int insertID = dao.create_si(simpleInsert);
			//
			//			dao.delete(8);

			List<Fruit> fruits = dao.getFoodGroups();

			StringBuilder out = new StringBuilder("Fruit \n");

			for(Fruit f : fruits){
				LOG.info(f.talkAboutYourself());
				out.append(f.talkAboutYourself() + "/n");
			}

			//out.append("SimpelID keygenerated key " + insertID);

			model.addAttribute("fruitsTalk",out.toString());

			FoodGroup foodGroup = dao.getFoodGroup(1);

			model.addAttribute("fruitTalk",foodGroup.talkAboutYourself());

			dao.demoMethod();
		}
		catch(PermissionDeniedDataAccessException e) {
			// use hierachie 
		}
		catch(DataAccessException e){
			LOG.info(e.getMessage());
			LOG.info(e.getClass().getName());
		}




		return "dao";
	}
}
