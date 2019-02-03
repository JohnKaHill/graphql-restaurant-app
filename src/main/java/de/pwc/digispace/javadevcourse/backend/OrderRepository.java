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
	
	public OrderRepository() {}
	
	private Connection getConnection() throws SQLException {
		return ConnectionFactory.getInstance().getConnection();
	}
	
	public void add( Order order ) {
		PreparedStatement preparedStatement = null;
	
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
		PreparedStatement preparedStatement = null;
	
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
		PreparedStatement preparedStatement = null;
	
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
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
	
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
								findDrinksById(orderId),
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
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

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
								findDrinksById(orderId),
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

	public void addBeverageToOrder( UUID orderId, String name, int amountOrdered )
	{
		PreparedStatement preparedStatement = null;
	
		try {
			String queryString = 
					"INSERT INTO orderbeverage(orderId, name, amountOrdered) "
					+ "VALUES(?,?,?)";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			int attributeCounter = 1;
			// add Beverage(s) to order
			preparedStatement.setObject(attributeCounter++, orderId);
			preparedStatement.setString(attributeCounter++, name);
			preparedStatement.setInt(attributeCounter++, amountOrdered);
			int i = preparedStatement.executeUpdate();

			// set taxes for order according to added beverages and meals
			List<Beverage> drinks = findDrinksById(orderId);
			// List<Food> meals = findFoodById(orderId);
			Order order = findById(orderId);
			order.setTaxes(null, drinks);
			update(order);

			// persist taxes to database
			/** TODO: add batch saving instead for every single object */
			for( Tax tax : order.getTax() ) { addTaxToOrder(orderId, tax.getTaxId()); }

			logger.info("{} beverages added to order successfully", amountOrdered);
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
	
	private List<Beverage> findDrinksById(UUID orderID) {
		List<Beverage> drinks = new ArrayList<>();
		BeverageRepository beverageDAO = new BeverageRepository();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;	

		try {
			String queryString = "SELECT * FROM orderbeverage WHERE orderId=?";
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
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return drinks;
	}

	private void addFoodToOrder( UUID orderId, String name, int amountOrdered )
	{
		PreparedStatement preparedStatement = null;
	
		try {
			String queryString = 
					"INSERT INTO orderfood(orderId, name, amountOrdered) "
					+ "VALUES(?,?,?)";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			int attributeCounter = 1;
			preparedStatement.setObject(attributeCounter++, orderId);
			preparedStatement.setString(attributeCounter++, name);
			preparedStatement.setInt(attributeCounter++, amountOrdered);

			logger.info("{} meals added to order successfully", amountOrdered);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private List<Food> findMealsById(UUID orderID) {
		List<Food> meals = new ArrayList<>();
		FoodRepository foodDAO = new FoodRepository();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;	

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
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return meals;
	}

	public void addTaxToOrder( UUID orderId, UUID taxId )
	{
		PreparedStatement preparedStatement = null;
	
		try {
			String queryString = 
					"INSERT INTO ordertax(orderId, taxId) VALUES(?,?)";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			int attributeCounter = 1;
			preparedStatement.setObject(attributeCounter++, orderId);
			preparedStatement.setObject(attributeCounter++, taxId);
			int i = preparedStatement.executeUpdate();

			logger.info("tax added to order successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private List<Tax> findTaxById(UUID orderId)
	{
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;	
		List<Tax> taxes = new ArrayList<>();
		TaxRepository taxDAO = new TaxRepository();
		
		try {
			String queryString = "SELECT * FROM ordertax WHERE orderId=?";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setObject(1, orderId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				taxes.add( taxDAO.findById((UUID) resultSet.getObject("taxId")) );
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
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return taxes;
	}
		
}
