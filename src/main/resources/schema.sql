CREATE TABLE orders(
	orderId UUID PRIMARY KEY,
	dateCreated LocalDateTime NOT NULL,
	dateEdited LocalDateTime,
	tableNumber INT NOT NULL,
	isOpen BOOLEAN NOT NULL,
	paymentMethod VARCHAR(20),
	datePaid LocalDateTime,
	totalAmount DECIMAL(20,2)
);

CREATE TABLE meals(
	name VARCHAR(50) PRIMARY KEY,
	description VARCHAR(300),
	price DECIMAL(20,2) NOT NULL,
	tax INT NOT NULL,
	dateCreated LocalDateTime NOT NULL,
	dateEdited LocalDateTime,
	deprecated BOOLEAN,
	containsMeat BOOLEAN NOT NULL,
	foodType VARCHAR(30)
);

CREATE TABLE drinks(
	name VARCHAR(50) PRIMARY KEY,
	description VARCHAR(300),
	price DECIMAL(20,2) NOT NULL,
	tax INT NOT NULL,
	dateCreated LocalDateTime NOT NULL,
	dateEdited LocalDateTime,
	deprecated BOOLEAN,
	containsAlcohol BOOLEAN NOT NULL
	beverageType VARCHAR(20)
);

CREATE TABLE tax(
	taxId UUID PRIMARY KEY,
	taxRate INT NOT NULL,
	taxTotal DECIMAL(20,2) NOT NULL
);

CREATE TABLE orderfood(
	orderId UUID NOT NULL,
	name VARCHAR(300) NOT NULL,
	amountOrdered INT NOT NULL,
	FOREIGN KEY (orderId) REFERENCES orders(orderId),
	FOREIGN KEY (name) REFERENCES meals(name),
	UNIQUE (orderId, name)
);

CREATE TABLE orderbeverage(
	orderId UUID NOT NULL,
	name VARCHAR(300) NOT NULL,
	amountOrdered INT NOT NULL,
	FOREIGN KEY (orderId) REFERENCES orders(orderId),
	FOREIGN KEY (name) REFERENCES drinks(name),
	UNIQUE (orderId, name)
);

CREATE TABLE ordertax(
	orderId UUID NOT NULL,
	taxId UUID NOT NULL,
	FOREIGN KEY (orderId) REFERENCES orders(orderId),
	FOREIGN KEY (taxId) REFERENCES tax(taxId),
	UNIQUE (orderId, tax)
);