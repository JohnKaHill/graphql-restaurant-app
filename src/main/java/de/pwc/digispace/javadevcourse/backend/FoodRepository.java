package de.pwc.digispace.javadevcourse.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.pwc.digispace.javadevcourse.backend.entities.Food;
import de.pwc.digispace.javadevcourse.backend.entities.FoodType;

public class FoodRepository implements Dao<Food, String>{
	
	public final Logger logger = LoggerFactory.getLogger(FoodRepository.class);

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	public FoodRepository() {

	}
	private Connection getConnection() throws SQLException {
		return ConnectionFactory.getInstance().getConnection();
	}
	
	public void add( Food food ) {
		try {
			int attributeCounter = 1;
			String queryString = 
					"INSERT INTO meals(name, description, price, tax, dateCreated,"
					+ "dateEdited, deprecated, containsAlcohol, foodType) VALUES(?,?,?,?,?,?,?,?)";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setString(attributeCounter++, food.getName());
			preparedStatement.setString(attributeCounter++, food.getDescription());
			preparedStatement.setBigDecimal(attributeCounter++, food.getPrice());
			preparedStatement.setInt(attributeCounter++, food.getTax());
			preparedStatement.setObject(attributeCounter++, food.getDateCreated());
			preparedStatement.setObject(attributeCounter++, food.getDateEdited());
			preparedStatement.setBoolean(attributeCounter++, food.isDeprecated());
			preparedStatement.setBoolean(attributeCounter++, food.containsMeat());
			preparedStatement.setString(attributeCounter++, food.getFoodType().toString());
			int i = preparedStatement.executeUpdate();
			logger.info("{} orders added successfully", i);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void update(Food food) {
		try {
			int attributeCounter = 1;
			String queryString = "UPDATE meals SET name=?, description=?, price=?, tax=?,"
					+ "dateEdited=?, deprecated=?, containsAlohol=?, foodType=? WHERE name=?";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setString(attributeCounter++, food.getName());
			preparedStatement.setString(attributeCounter++, food.getDescription());
			preparedStatement.setBigDecimal(attributeCounter++, food.getPrice());
			preparedStatement.setInt(attributeCounter++, food.getTax());
			preparedStatement.setObject(attributeCounter++, food.getDateEdited());
			preparedStatement.setBoolean(attributeCounter++, food.isDeprecated());
			preparedStatement.setBoolean(attributeCounter++, food.containsMeat());
			preparedStatement.setString(attributeCounter++, food.getFoodType().toString());
			int i = preparedStatement.executeUpdate();
			logger.info("UPDATED dish {} !", food.getName());
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean delete(String name) {
		int i = 0;
		try {
			String queryString = "DELETE FROM meals WHERE name=?";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setString(1, name);
			i = preparedStatement.executeUpdate();
			logger.info("DELETED dish {}.", name);
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return (i != 0);
	}
	
	public Food findById(String name) {
		Food food = new Food();
		try {
			String queryString = "SELECT FROM meals where name=?";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			
			food.setName(resultSet.getString("name"));
			food.setDescription(resultSet.getString("description"));
			food.setPrice(resultSet.getBigDecimal("price"));
			food.setTax(resultSet.getInt("tax"));
			food.setDateEdited(LocalDateTime.parse(resultSet.getDate("dateEdited").toString()));
			food.setDateCreated(LocalDateTime.parse(resultSet.getDate("dateCreated").toString()));
			food.containsMeat(resultSet.getBoolean("containsMeat"));
			food.setDeprecated(resultSet.getBoolean("deprecated"));
			food.setFoodType(FoodType.valueOf(resultSet.getString("name")));
			food.setDeprecated(resultSet.getBoolean("deprecated"));
			
			logger.info("FOUND:\n{}", food.toString());
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return food;
	}
	
	public List<Food> findAll() {
		List<Food> meals = new ArrayList<>();
		try {
			String queryString = "SELECT * FROM meals";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Food food = new Food();
				food.setName(resultSet.getString("name"));
				food.setDescription(resultSet.getString("description"));
				food.setPrice(resultSet.getBigDecimal("price"));
				food.setTax(resultSet.getInt("tax"));
				food.setDateEdited(LocalDateTime.parse(resultSet.getDate("dateEdited").toString()));
				food.setDateCreated(LocalDateTime.parse(resultSet.getDate("dateCreated").toString()));
				food.containsMeat(resultSet.getBoolean("containsMeat"));
				food.setDeprecated(resultSet.getBoolean("deprecated"));
				food.setFoodType(FoodType.valueOf(resultSet.getString("name")));
				food.setDeprecated(resultSet.getBoolean("deprecated"));
				meals.add(food);
				
				logger.info(food.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return meals;
	}
		
}
