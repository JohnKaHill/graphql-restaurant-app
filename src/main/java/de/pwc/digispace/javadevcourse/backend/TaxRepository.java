package de.pwc.digispace.javadevcourse.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.pwc.digispace.javadevcourse.backend.entities.Beverage;
import de.pwc.digispace.javadevcourse.backend.entities.BeverageType;
import de.pwc.digispace.javadevcourse.backend.entities.Tax;

public class TaxRepository {

	public final Logger logger = LoggerFactory.getLogger(TaxRepository.class);
	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	public TaxRepository() {}
	
	private Connection getConnection() throws SQLException {
		return ConnectionFactory.getInstance().getConnection();
	}
	
	public void add( Tax tax ) {
		try {
			String queryString = "INSERT INTO tax(taxId, taxRate, taxTotal) VALUES(?,?,?)";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			int attributeCounter = 1;
			preparedStatement.setObject(attributeCounter++, tax.getTaxId());
			preparedStatement.setInt(attributeCounter++, tax.getTaxRate());
			preparedStatement.setObject(attributeCounter++, tax.getTaxTotal());
			int i = preparedStatement.executeUpdate();
			logger.info("{} tax added successfully", i);
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
	
	public void update(Tax tax) {
		try {
			String queryString = "UPDATE tax SET taxRate=?, taxTotal=? WHERE taxId=?";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			int attributeCounter = 1;
			preparedStatement.setInt(attributeCounter++, tax.getTaxRate());
			preparedStatement.setObject(attributeCounter++, tax.getTaxTotal());
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
	
	public boolean delete(UUID taxId) {
		int i = 0;
		try {
			String queryString = "DELETE FROM tax WHERE taxId=?";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setObject(1, taxId);
			i = preparedStatement.executeUpdate();
			logger.info("DELETED {} tax.", taxId);
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
	
	public Tax findById(UUID taxId) {
		Tax tax = null;
		try {
			String queryString = "SELECT FROM drinks where name=?";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setObject(1, taxId);
			resultSet = preparedStatement.executeQuery();
			tax = new Tax(taxId,
				resultSet.getInt("taxRate"),
				resultSet.getBigDecimal("taxTotal") );			
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
		return tax;
	}
	
	public List<Tax> findAll() {
		List<Tax> taxes = new ArrayList<>();
		try {
			String queryString = "SELECT * FROM tax";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				taxes.add( new Tax( (UUID) resultSet.getObject("taxId"),
					resultSet.getInt("taxRate"),
					resultSet.getBigDecimal("taxTotal") ));	
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
		return taxes;
	}
		

	
	
}
