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
import de.pwc.digispace.javadevcourse.backend.entities.Food;
import de.pwc.digispace.javadevcourse.backend.entities.Order;
import de.pwc.digispace.javadevcourse.backend.entities.PaymentMethod;

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
					+ "isOpen, datePaid, paymentMethod) VALUES(?,?,?,?,?,?,?)";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setObject(1, order.getOrderId());
			preparedStatement.setObject(2, order.getDateCreated());
			preparedStatement.setInt(3, order.getTableNumber());
			preparedStatement.setBoolean(4, order.isOpen());
			preparedStatement.setString(5, order.getPaymentMethod().toString());
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
	
	public void update(Order order) {
		try {
			String queryString = "UPDATE orders SET tableNumber=?, "
					+ "isOpen=? WHERE orderId=?";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setInt(1, order.getTableNumber());
			preparedStatement.setBoolean(2, order.isOpen());
			preparedStatement.setObject(3, order.getOrderId());
			int i = preparedStatement.executeUpdate();
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
		Order order = new Order(orderId);
		try {
			String queryString = "SELECT FROM orders where orderId=?";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setObject(1, orderId);
			resultSet = preparedStatement.executeQuery();
			order.setDateCreated(LocalDateTime.parse(resultSet.getDate("dateCreated").toString()));
			order.setDateEdited(LocalDateTime.parse(resultSet.getDate("dateEdited").toString()));
			order.setTableNumber(resultSet.getInt("tableNumber"));
			order.setOpen(resultSet.getBoolean("isOpen"));
			order.setMeals(findFoodById(orderId));
			order.setDrinks(findBeverageById(orderId));
			order.setPaymentMethod((PaymentMethod) resultSet.getObject("paymentMethod"));
			order.setDatePaid(LocalDateTime.parse(resultSet.getDate("datePaid").toString()));
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
	
	public List<Order> findAll() {
		List<Order> orders = new ArrayList<>();
		try {
			String queryString = "SELECT * FROM orders";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				UUID orderId = (UUID) resultSet.getObject("orderId");
				Order order = new Order(orderId);
				order.setDateCreated(LocalDateTime.parse(resultSet.getDate("dateCreated").toString()));
				order.setDateEdited(LocalDateTime.parse(resultSet.getDate("dateEdited").toString()));
				order.setTableNumber(resultSet.getInt("tableNumber"));
				order.setOpen(resultSet.getBoolean("isOpen"));
				order.setMeals(findFoodById(orderId));
				order.setDrinks(findBeverageById(orderId));
				order.setPaymentMethod((PaymentMethod) resultSet.getObject("paymentMethod"));
				order.setDatePaid(LocalDateTime.parse(resultSet.getDate("datePaid").toString()));
				orders.add(order);
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
				meals.add(foodDAO.findById(resultSet.getString("name")));
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
