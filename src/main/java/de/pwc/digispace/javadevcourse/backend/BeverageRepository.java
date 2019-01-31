package de.pwc.digispace.javadevcourse.backend;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.css.Counter;

import de.pwc.digispace.javadevcourse.backend.entities.Beverage;
import de.pwc.digispace.javadevcourse.backend.entities.BeverageType;

public class BeverageRepository implements Dao<Beverage, String>{
	
	public final Logger logger = LoggerFactory.getLogger(BeverageRepository.class);
	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	public BeverageRepository() {
		Beverage beverage = new Beverage("Coke", BigDecimal.valueOf(1.9), 19, BeverageType.SOFTDRINK, true, "from USA");
	}
	
	private Connection getConnection() throws SQLException {
		return ConnectionFactory.getInstance().getConnection();
	}
	
	public void add( Beverage beverage ) {
		try {
			int attributeCounter = 1;
			String queryString = 
					"INSERT INTO drinks(name, description, price, tax, dateCreated,"
					+ "dateEdited, deprecated, containsAlcohol, beverageType) VALUES(?,?,?,?,?,?,?,?,?)";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setString(attributeCounter++, beverage.getName());
			preparedStatement.setString(attributeCounter++, beverage.getDescription());
			preparedStatement.setBigDecimal(attributeCounter++, beverage.getPrice());
			preparedStatement.setInt(attributeCounter++, beverage.getTax());
			preparedStatement.setObject(attributeCounter++, beverage.getDateCreated());
			preparedStatement.setObject(attributeCounter++, beverage.getDateEdited());
			preparedStatement.setBoolean(attributeCounter++, beverage.isDeprecated());
			preparedStatement.setBoolean(attributeCounter++, beverage.containsAlcohol());
			preparedStatement.setString(attributeCounter++, beverage.getBeverageType().toString());
			int i = preparedStatement.executeUpdate();
			logger.info("{} drinks added successfully", i);
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
	
	public void update(Beverage beverage) {
		try {
			int attributeCounter = 1;
			String queryString = "UPDATE drinks SET name=?, description=?, price=?, tax=?,"
					+ "dateEdited=?, deprecated=?, containsAlohol=?, beverageType=? WHERE name=?";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setString(attributeCounter++, beverage.getName());
			preparedStatement.setString(attributeCounter++, beverage.getDescription());
			preparedStatement.setBigDecimal(attributeCounter++, beverage.getPrice());
			preparedStatement.setInt(attributeCounter++, beverage.getTax());
			preparedStatement.setObject(attributeCounter++, beverage.getDateEdited());
			preparedStatement.setBoolean(attributeCounter++, beverage.isDeprecated());
			preparedStatement.setBoolean(attributeCounter++, beverage.containsAlcohol());
			preparedStatement.setString(attributeCounter++, beverage.getBeverageType().toString());
			int i = preparedStatement.executeUpdate();
			logger.info("{} beverage(s) UPDATED!", i);
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
			String queryString = "DELETE FROM drinks WHERE name=?";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setString(1, name);
			i = preparedStatement.executeUpdate();
			logger.info("DELETED {} beverage.", name);
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
	
	public Beverage findById(String name) {
		Beverage beverage = new Beverage();
		try {
			String queryString = "SELECT FROM drinks where name=?";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			
			beverage.setName(resultSet.getString("name"));
			beverage.setDescription(resultSet.getString("description"));
			beverage.setPrice(resultSet.getBigDecimal("price"));
			beverage.setTax(resultSet.getInt("tax"));
			beverage.setDateEdited(LocalDateTime.parse(resultSet.getDate("dateEdited").toString()));
			beverage.setDateCreated(LocalDateTime.parse(resultSet.getDate("dateCreated").toString()));
			beverage.containsAlcohol(resultSet.getBoolean("containsAlcohol"));
			beverage.setDeprecated(resultSet.getBoolean("deprecated"));
			beverage.setBeverageType(BeverageType.valueOf(resultSet.getString("beverageType")));
			beverage.setDeprecated(resultSet.getBoolean("deprecated"));
			logger.info("FOUND:\n{}", beverage.toString());
			
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
		return beverage;
	}
	
	public List<Beverage> findAll() {
		List<Beverage> drinks = new ArrayList<>();
		try {
			String queryString = "SELECT * FROM orders";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Beverage beverage = new Beverage();
				beverage.setName(resultSet.getString("name"));
				beverage.setDescription(resultSet.getString("description"));
				beverage.setPrice(resultSet.getBigDecimal("price"));
				beverage.setTax(resultSet.getInt("tax"));
				beverage.setDateEdited(LocalDateTime.parse(resultSet.getDate("dateEdited").toString()));
				beverage.setDateCreated(LocalDateTime.parse(resultSet.getDate("dateCreated").toString()));
				beverage.containsAlcohol(resultSet.getBoolean("containsAlcohol"));
				beverage.setDeprecated(resultSet.getBoolean("deprecated"));
				beverage.setBeverageType(BeverageType.valueOf(resultSet.getString("beverageType")));
				beverage.setDeprecated(resultSet.getBoolean("deprecated"));
				drinks.add(beverage);

				logger.info(beverage.toString());
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
		return drinks;
	}
	
}
