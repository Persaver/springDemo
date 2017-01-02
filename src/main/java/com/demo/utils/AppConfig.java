package com.demo.utils;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.demo.models.car.Engine;
import com.demo.models.car.FamilyCar;
import com.demo.models.car.SixCylinderEngine;
import com.demo.models.food.Dairy;
import com.demo.models.food.Meal;
import com.demo.models.food.Meat;
import com.demo.models.food.Vegetable;

@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages="com.demo.controllers,com.demo.myTestPackage,com.demo.models,com.demo.utils,com.demo.scripts,com.demo.DAO")
@Configuration
@PropertySource({"classpath:com/demo/utils/dbInfo.properties"})
public class AppConfig extends WebMvcConfigurerAdapter{
	private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);
	@Autowired
	private Environment env;

	@Bean
	public InternalResourceViewResolver getIRVR() {
		LOG.info("Setting up view resolver");
		InternalResourceViewResolver irvr = new InternalResourceViewResolver();

		irvr.setPrefix("WEB-INF/jsp/");
		irvr.setSuffix(".jsp");

		return irvr;
	}

	@Bean(name="mealByAnnotation")
	public Meal getMBA(){

		return new Meal();
	}

	//	@Bean(name="fruit")
	//	public Fruit getFruit(){
	//		Fruit fruit = new Fruit();
	//		fruit.setName("Apple");
	//		return fruit;
	//	}

	@Bean(name="veg")
	public Vegetable getVegetable(){
		return new Vegetable();
	}

	@Bean(name="dairy")
	public Dairy getDairy(){
		return new Dairy();
	}

	@Bean(name="meat")
	public Meat getMeat(){
		return new Meat();
	}

	//	@Bean(name="grain")
	//	public Grain getGrain(){
	//		return new Grain();
	//	}
	//	@Bean(name="sTire")
	//	public Tire getSmallTire() {
	//		return new SmallTire();
	//	}
	//	@Bean(name="bTire")
	//	public Tire getBigTire() {
	//		return new BigTire();
	//	}
	//	@Bean(name="fourCyl")
	//	public Engine getFourCylinderEnginne() {
	//		return new FourCylinderEngine();
	//	}
	@Bean(name="sixCyl")
	public Engine getSixCylinderEnginne() {
		return new SixCylinderEngine();
	}

	@Bean(name="familyCar")
	public FamilyCar getFamilyCar() {

		FamilyCar car = new FamilyCar();

		car.setEngine(this.getSixCylinderEnginne());
		return new FamilyCar();
	}

	@Bean("myDriverManagerDataSource")
	public BasicDataSource getDataSource() {
		LOG.info("driverClass " + this.env.getProperty("ds.driverClassName"));
		LOG.info("url " + this.env.getProperty("ds.url"));

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(this.env.getProperty("ds.driverClassName"));
		dataSource.setUrl(this.env.getProperty("ds.url"));
		dataSource.setUsername(this.env.getProperty("ds.userName"));
		dataSource.setPassword(this.env.getProperty("ds.password"));

		return dataSource;
	}

	@Bean("myTransactionManager")
	@Autowired
	public DataSourceTransactionManager getDataSourceTransactionManager(BasicDataSource ds){
		return new DataSourceTransactionManager(ds);
	}


	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		LOG.info("Setting up resources");

		registry.addResourceHandler("/myResources/**").addResourceLocations("/resources/");
	}
}
