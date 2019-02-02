package de.pwc.digispace.javadevcourse.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.pwc.digispace.javadevcourse.backend.entities.Beverage;
import de.pwc.digispace.javadevcourse.backend.entities.Food;
import de.pwc.digispace.javadevcourse.backend.entities.Order;
import de.pwc.digispace.javadevcourse.backend.entities.PaymentMethod;
import de.pwc.digispace.javadevcourse.backend.entities.Tax;

public class OrderRepository implements Dao<Order, UUID>{
	
	public final Logger logger = LoggerFactory.getLogger(OrderRepository.class);

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	public OrderRepository() {}
	
	private Connection getConnection() throws SQLException {
		return ConnectionFactory.getInstance().getConnection();
	}
	
	public void add( Order order ) {
		try {
			String queryString = 
					"INSERT INTO orders(orderId, dateCreated, tableNumber, "
					+ "isOpen, paymentMethod) VALUES(?,?,?,?,?)";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			int attributeCounter = 1;
			preparedStatement.setObject(attributeCounter++, order.getOrderId());
			preparedStatement.setObject(attributeCounter++, LocalDateTime.now());
			preparedStatement.setInt(attributeCounter++, order.getTableNumber());
			preparedStatement.setBoolean(attributeCounter++, true);
			preparedStatement.setString(attributeCounter++, order.getPaymentMethod().toString());
			int i = preparedStatement.executeUpdate();

			/* 
				Add insertion of meals and drinks into helper tables
			*/

			logger.info("{} order added successfully", i);
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
	
	public void update(Order order) {
		try {
			String queryString = "UPDATE orders SET tableNumber=?, dateEdited=?, "
					+ "isOpen=?, datePaid=?, totalAmount=? WHERE orderId=?";
			connection = getConnection();
			int attributeCounter = 1;
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setInt(attributeCounter++, order.getTableNumber());
			preparedStatement.setObject(attributeCounter++, LocalDateTime.now());
			preparedStatement.setBoolean(attributeCounter++, order.getIsOpen());
			preparedStatement.setObject(attributeCounter++, order.getDatePaid());
			preparedStatement.setBigDecimal(attributeCounter++, order.getTotalAmount());
			preparedStatement.setObject(attributeCounter++, order.getOrderId());
			int i = preparedStatement.executeUpdate();

			/* 
				Add insertion of meals and drinks into helper tables
			*/

			logger.info("{} orders UPDATED!", i);
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
	
	public boolean delete(UUID orderId) {
		int i = 0;
		try {
			String queryString = "DELETE FROM orders WHERE orderid=?";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setObject(1, orderId);
			i = preparedStatement.executeUpdate();
			logger.info("DELETED {} order.", i);
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
	
	public Order findById(UUID orderId) {
		Order order = null;
		try {
			String queryString = "SELECT * FROM orders where orderId=?";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setObject(1, orderId);
			resultSet = preparedStatement.executeQuery();
			if( resultSet.next() ) {
				order = new Order( orderId,
								resultSet.getObject("dateCreated", LocalDateTime.class),
								resultSet.getObject("dateEdited", LocalDateTime.class),	
								resultSet.getInt("tableNumber"),
								resultSet.getBoolean("isOpen"),
								resultSet.getObject("datePaid", LocalDateTime.class),

								null,
								null,
								/* 
									Add insertion of meals and drinks into helper tables
								*/

								PaymentMethod.valueOf(resultSet.getString("paymentMethod")),

								null,
								/* 
									Add insertion of meals and drinks into helper tables
								*/

								resultSet.getBigDecimal("totalAmount"));
			}
			
			String orderString = order.toString();
			logger.info(orderString);
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
		return order;
	}
	
	public Map<UUID, Order> findAll() {
		Map<UUID, Order> orders = new HashMap<>();
		try {
			String queryString = "SELECT * FROM orders";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				UUID orderId = (UUID) resultSet.getObject("orderId");
				orders.put( orderId, new Order( orderId,
								resultSet.getObject("dateCreated", LocalDateTime.class),
								resultSet.getObject("dateEdited", LocalDateTime.class),	
								resultSet.getInt("tableNumber"),
								resultSet.getBoolean("isOpen"),
								resultSet.getObject("datePaid", LocalDateTime.class),

								null,
								null,
								/* 
									Add insertion of meals and drinks into helper tables
								*/

								PaymentMethod.valueOf(resultSet.getString("paymentMethod")),

								null,
								/* 
									Add insertion of meals and drinks into helper tables
								*/

								resultSet.getBigDecimal("totalAmount")) );
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
		return orders;
	}
	
	public List<Beverage> findBeverageById(UUID orderID) {
		List<Beverage> drinks = new ArrayList<>();
		BeverageRepository beverageDAO = new BeverageRepository();
		
		try {
			String queryString = "SELECT * FROM orderbeverage WHERE orderId=?";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setObject(1, orderID);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				drinks.add(beverageDAO.findById(resultSet.getString("name")));
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
	
	public List<Food> findFoodById(UUID orderID) {
		List<Food> meals = new ArrayList<>();
		FoodRepository foodDAO = new FoodRepository();
		
		try {
			String queryString = "SELECT * FROM orderfood WHERE orderId=?";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setObject(1, orderID);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				meals.add(foodDAO.findById(resultSet.getString("name")) );
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

	public List<Tax> getTaxById(UUID orderId)
	{
		List<Tax> taxes = new ArrayList<>();
		TaxRepository taxDAO = new TaxRepository();
		
		try {
			String queryString = "SELECT * FROM ordertax WHERE orderId=?";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setObject(1, orderId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				taxes.add( taxDAO.findById(resultSet.getString("taxId")) );
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
