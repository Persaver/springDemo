package com.demo.db;

import java.sql.SQLException;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

//@Component
@PropertySource(value={"classpath:com/demo/utils/dbInfo.properties"})
public class myBasicDataSource extends org.apache.commons.dbcp.BasicDataSource{
	protected static final Logger LOG = LoggerFactory.getLogger(myBasicDataSource.class);
	@Autowired
	private Environment env;
	//	@Autowired
	//	PropertySourcesPlaceholderConfigurer pspc;

	@Value("#{ds.driverClassName}")
	String driverClassName;

	@Value("#{ds.url}")
	String url;

	@Value("#{ds.userName}")
	String userName;

	@Value("#{ds.password}")
	String password;



	public myBasicDataSource() {
		LOG.info("#{ds.driverClassName}");
	}


	@PreDestroy
	public void preDestroy(){
		try {
			this.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOG.info(e.getMessage());
		}
	}


	//	public BasicDataSource getBasicDataSource() {
	//		BasicDataSource source = new BasicDataSource();
	//
	//		return source;
	//	}


}
