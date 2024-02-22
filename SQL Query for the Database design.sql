-- CREATE TABLE dealer (
--     dealerId VARCHAR(20) PRIMARY KEY,
--     dealerName VARCHAR(100),
--     location VARCHAR(255),
--     contactNumber VARCHAR(20),
--     dealerMailId VARCHAR(255) NOT NULL UNIQUE, 
--     dealerPassword VARCHAR(255) NOT NULL
-- );

-- CREATE TABLE courierservice (
--     courierServiceId VARCHAR(20) PRIMARY KEY,
--     courierServiceName VARCHAR(100),
--     contactNumber VARCHAR(20),
--     courierServiceMailId VARCHAR(255) NOT NULL UNIQUE,
--     cSPassword VARCHAR(255) NOT NULL
-- );

-- CREATE TABLE watches (
--     watchId VARCHAR(20) PRIMARY KEY,
--     seriesName VARCHAR(255) NOT NULL,
--     brand VARCHAR(255),
--     price DECIMAL(10, 2) NOT NULL,
--     description TEXT,
--     numberOfStocks INT NOT NULL,
--     dealerId VARCHAR(20),
--     FOREIGN KEY (dealerId) REFERENCES dealer(dealerId)
-- );

-- CREATE TABLE admin (
--     adminId VARCHAR(20) PRIMARY KEY,
--     adminName VARCHAR(255),
--     mobileNumber VARCHAR(20),
--     mailId VARCHAR(255),
--     role VARCHAR(255),
--     password VARCHAR(255) NOT NULL
-- );

-- CREATE TABLE user (
--     userId VARCHAR(20) PRIMARY KEY,
--     userName VARCHAR(255) NOT NULL,
--     mobileNumber VARCHAR(20) NOT NULL,
--     emailId VARCHAR(255) NOT NULL,
--     address VARCHAR(255) NOT NULL,
--     password VARCHAR(255) NOT NULL,
--     createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
-- );

-- CREATE TABLE orders (
--     orderId VARCHAR(20) PRIMARY KEY,
--     userId VARCHAR(20),
--     dealerId VARCHAR(20),
--     watchId VARCHAR(20),
--     orderDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     deliveryDate TIMESTAMP DEFAULT NULL,
--     quantity INT DEFAULT 1,
--     totalAmount DECIMAL(10, 2) NOT NULL,
--     paymentMode VARCHAR(255) NOT NULL,
--     status VARCHAR(255) NOT NULL,
--     FOREIGN KEY (userId) REFERENCES user(userId),
--     FOREIGN KEY (watchId) REFERENCES watches(watchId),
--     FOREIGN KEY (dealerId) REFERENCES dealer(dealerId)
-- );

-- CREATE TABLE purchaseHistory (
--     historyId VARCHAR(20) PRIMARY KEY,
--     userId VARCHAR(20),
--     orderId VARCHAR(20),
--     purchaseDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     FOREIGN KEY (userId) REFERENCES user(userId),
--     FOREIGN KEY (orderId) REFERENCES orders(orderId)
-- );

-- CREATE TABLE cart (
--     cartId VARCHAR(20) PRIMARY KEY,
--     userId VARCHAR(20),
--     watchId VARCHAR(20),
--     cartDetails VARCHAR(255) NOT NULL,
--     FOREIGN KEY (userId) REFERENCES user(userId),
--     FOREIGN KEY (watchId) REFERENCES watches(watchId)
-- );

-- INSERT INTO admin(adminId, adminName, mobileNumber, mailId, role, password) 
-- VALUES ("AUID1000", "Rokesh Varma V", "6385918710", "vrokeshvarma@gmail.com", 'manager', "Roke$hv@rm@2603");

-- INSERT INTO admin(adminId, adminName, mobileNumber, mailId, role, password) 
-- VALUES ("AUID1001", "Venkadesan S", "8122289200", "vsrokesh62@gmail.com", 'executive', "Pr@veen@123");


-- DROP TABLE admin;


-- SELECT adminId FROM admin ORDER BY adminId DESC LIMIT 1;

SELECT * FROM cart;