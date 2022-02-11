DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
  customer_id INT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  date_of_birth date NOT NULL,

  status_s VARCHAR(50) NOT NULL
);