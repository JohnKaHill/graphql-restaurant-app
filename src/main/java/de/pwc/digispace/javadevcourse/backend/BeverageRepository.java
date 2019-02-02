package de.pwc.digispace.javadevcourse.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.pwc.digispace.javadevcourse.backend.entities.Beverage;
import de.pwc.digispace.javadevcourse.backend.entities.BeverageType;

public class BeverageRepository implements Dao<Beverage, String>{
	
	public final Logger logger = LoggerFactory.getLogger(BeverageRepository.class);
	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

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
			preparedStatement.setBoolean(attributeCounter++, beverage.isContainsAlcohol());
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
			String queryString = "UPDATE drinks SET name=?, description=?, price=?, "
					+ "dateEdited=?, deprecated=? WHERE name=?";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setString(attributeCounter++, beverage.getName());
			preparedStatement.setString(attributeCounter++, beverage.getDescription());
			preparedStatement.setBigDecimal(attributeCounter++, beverage.getPrice());
			preparedStatement.setObject(attributeCounter++, beverage.getDateEdited());
			preparedStatement.setBoolean(attributeCounter++, beverage.isDeprecated());
			preparedStatement.setString(attributeCounter++, beverage.getName());
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
		Beverage beverage = null;
		try {
			String queryString = "SELECT * FROM drinks WHERE name = ?";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			System.out.println("RS LENGTH: " + name );
			if( resultSet.next() ){
				beverage = new Beverage(resultSet.getString("name"),
										resultSet.getBigDecimal("price"),
										resultSet.getInt("tax"),
										resultSet.getString("description"),
										resultSet.getObject("dateCreated", LocalDateTime.class),
										resultSet.getObject("dateEdited", LocalDateTime.class),
										resultSet.getBoolean("deprecated"),
										BeverageType.valueOf(resultSet.getString("beverageType")),
										resultSet.getBoolean("containsAlcohol"));

				logger.info("FOUND:\n{}", beverage.toString());
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
		return beverage;
	}
	
	public Map<String, Beverage> findAll() {
		Map<String, Beverage> drinks = new HashMap<>();
		try {
			String queryString = "SELECT * FROM drinks";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Beverage beverage = new Beverage(resultSet.getString("name"),
						resultSet.getBigDecimal("price"),
						resultSet.getInt("tax"),
						resultSet.getString("description"),
						resultSet.getObject("dateCreated", LocalDateTime.class),
						resultSet.getObject("dateEdited", LocalDateTime.class),
						resultSet.getBoolean("deprecated"),
						BeverageType.valueOf(resultSet.getString("beverageType")),
						resultSet.getBoolean("containsAlcohol"));
				drinks.put(beverage.getName(), beverage);

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
