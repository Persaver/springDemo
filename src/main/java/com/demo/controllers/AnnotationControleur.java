package com.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.models.car.FamilyCar;
import com.demo.models.food.Fruit;
import com.demo.models.food.Meal;
import com.demo.scripts.ScriptLoaderDemoClass;

@Controller
public class AnnotationControleur {
	private static final Logger LOG = LoggerFactory.getLogger(AnnotationControleur.class);

	@Autowired
	private ApplicationContext appContext;

	@RequestMapping("/annotation")
	public String Annotation(Model model) {
		LOG.info("AnnotationControleur");

		//ApplicationContext appContext = new ClassPathXmlApplicationContext();
		//this.appContext.getResource("com.demo.myTestPackage.appContext.xml");
		Meal meal = this.appContext.getBean("mealByAnnotation",Meal.class);

		model.addAttribute("whatInThisMeal", meal.whatInThisMeal());

		LOG.info("Spring meal {}", meal.whatInThisMeal());

		//((ClassPathXmlApplicationContext)appContext).close();

		return "annotation";
	}

	@RequestMapping("/meal")
	public String getMeal(Model model) {
		LOG.info("AnnotationControleur");

		//ApplicationContext appContext = new ClassPathXmlApplicationContext();
		//this.appContext.getResource("com.demo.myTestPackage.appContext.xml");
		Meal meal = this.appContext.getBean("mealByAnnotation",Meal.class);

		model.addAttribute("whatInThisMeal", meal.whatInThisMeal());

		model.addAttribute("whatInThisMealDescription", meal.whatInThisMealDescription());



		LOG.info("Spring meal {}", meal.whatInThisMeal());

		//((ClassPathXmlApplicationContext)appContext).close();

		return "meal";
	}

	@RequestMapping("/familyCar")
	public String getFamilyCar(Model model) {

		FamilyCar familyCar = this.appContext.getBean("familyCar",FamilyCar.class);

		model.addAttribute("familyCarDescription", familyCar.getCarDescription());

		return "familyCar";
	}

	@RequestMapping("/fruit")
	public String getFruit(Model model) {

		Fruit fruit = this.appContext.getBean("fruit",Fruit.class);

		model.addAttribute("fruit", fruit.talkAboutYourself());

		fruit = null;

		return "fruit";
	}

	@RequestMapping("/script")
	public String getScript(Model model) {

		ScriptLoaderDemoClass sl = this.appContext.getBean(ScriptLoaderDemoClass.class);

		model.addAttribute("loadScript", sl.loadScript());

		return "script";
	}

}
