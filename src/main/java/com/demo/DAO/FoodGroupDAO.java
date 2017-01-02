package com.demo.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.demo.models.food.FoodGroup;
import com.demo.models.food.Fruit;

@Repository("foodGroupDAO")
public class FoodGroupDAO {
	private static final Logger LOG = LoggerFactory.getLogger(FoodGroupDAO.class);

	private NamedParameterJdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert insertFoodGroup;

	private JdbcTemplate jdbcTemplate2;

	public List<Fruit> getFoodGroups() {

		LOG.info("jdbcTemplate DAO " + this.getJdbcTemplate().toString());
		MapSqlParameterSource myMap = new MapSqlParameterSource();
		myMap.addValue("groupName", "DigitalMeatUpdate");
		return this.jdbcTemplate.query("select * from foodgroups", myMap , new RowMapper<Fruit>() {

			//return this.jdbcTemplate.query("select * from foodgroups where name=:groupName", myMap , new RowMapper<Fruit>() {

			@Override
			public Fruit mapRow(ResultSet rs, int arg1) throws SQLException {
				Fruit fg = new Fruit();
				fg.setId(rs.getInt("id"));
				fg.setName(rs.getString("name"));
				fg.setDescription(rs.getString("description"));

				return fg;
			}

		});
	}

	public FoodGroup getFoodGroup(int id) {

		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("id", id);

		return this.jdbcTemplate.queryForObject(("select * from foodgroups where id=:id"),parameterSource, new RowMapper<FoodGroup>() {

			@Override
			public FoodGroup mapRow(ResultSet rs, int rowNum) throws SQLException {

				FoodGroup fg = new Fruit();
				fg.setId(rs.getInt("id"));
				fg.setName(rs.getString("name"));
				fg.setDescription(rs.getString("description"));

				return fg;
			}
		}); 
	}

	public Boolean addFoodGoup(String name, String description){
		Boolean res = false;
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", name);
		params.addValue("description", description);

		int numOfRowsAffected = this.jdbcTemplate.update("insert into foodgroups (name,description) values (:name,:description)", params);

		if(numOfRowsAffected == 1){
			res=true;
		}
		else {
			LOG.info("Impossible to save foodGroup");
		}
		return res;
	}

	public Boolean create(FoodGroup fg) {
		Boolean res = false;
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(fg);

		int numOfRowsAffected = this.jdbcTemplate.update("insert into foodgroups (name,description) values (:name,:description)", params);

		if(numOfRowsAffected == 1){
			res=true;
		}
		else {
			LOG.info("Impossible to create foodGroup");
		}
		return res;

	}

	public Boolean update(FoodGroup fg) {
		Boolean res = false;

		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(fg);

		int numOfRowsAffected = this.jdbcTemplate.update("update foodgroups set name=:name, description=:description where id=:id", params);

		if(numOfRowsAffected == 1){
			res=true;
		}
		else {
			LOG.info("Impossible to update foodGroup");
		}
		return res;

	}

	public Boolean delete(int id){
		Boolean res = false;

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);

		int numOfRowsAffected = this.jdbcTemplate.update("delete from foodgroups where id=:id", params);

		if(numOfRowsAffected == 1){
			res=true;
		}
		else {
			LOG.info("Impossible to delete foodGroup");
		}

		return res;

	}
	@Transactional("myTransactionManager")
	public int[] createFoodGroups(List<FoodGroup> groups){

		// methode longue
		//		ArrayList<MapSqlParameterSource> paramsArrayList = new ArrayList<MapSqlParameterSource>();
		//
		//		// place dans une liste 
		//		for(FoodGroup fg: groups){
		//			MapSqlParameterSource tempParam = new MapSqlParameterSource();
		//			tempParam.addValue("name", fg.getName());
		//			tempParam.addValue("description", fg.getDescription());
		//
		//			paramsArrayList.add(tempParam);
		//		}
		//		// transforme la liste en tableau
		//		SqlParameterSource[] batchParams = new MapSqlParameterSource[paramsArrayList.size()];
		//		paramsArrayList.toArray(batchParams);

		// Rapide en une ligne
		SqlParameterSource[] batchParams = SqlParameterSourceUtils.createBatch(groups.toArray());

		int[] numOfRowsAffectedArray = this.jdbcTemplate.batchUpdate("insert into foodgroups (name,description) values (:name,:description)",batchParams);

		return numOfRowsAffectedArray;
	}

	public int create_si(FoodGroup fg){
		SqlParameterSource params = new BeanPropertySqlParameterSource(fg);

		//		int numOfRowsAffected = this.insertFoodGroup.execute(params);
		//
		//		return numOfRowsAffected;

		Number insertID = this.insertFoodGroup.executeAndReturnKey(params);

		return insertID.intValue();
	}

	public void demoMethod() {
		// ENSURE integer returned
		Integer value = this.jdbcTemplate2.queryForObject("select count(*) from foodgroups",Integer.class);

		LOG.info("Count * " + value.intValue());

		FoodGroup fg = this.jdbcTemplate2.queryForObject("select * fromm foodgroups where id=4", new RowMapper<FoodGroup>(){

			@Override
			public FoodGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Fruit(rs.getInt("id"),rs.getString("name"),rs.getString("description"));
			}

		});
	}

	public NamedParameterJdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	@Autowired
	public void setJdbcTemplate(BasicDataSource ds) {
		LOG.info("url jdbcTemplate" + ds.getUrl());
		this.jdbcTemplate = new NamedParameterJdbcTemplate(ds);

		this.insertFoodGroup = new SimpleJdbcInsert(ds).withTableName("foodgroups").usingGeneratedKeyColumns("id");

		this.jdbcTemplate2 = new JdbcTemplate(ds);
	}

}
