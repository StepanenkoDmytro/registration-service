CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(255) NOT NULL,

  date_request TIMESTAMP NOT NULL,
  date_decision TIMESTAMP
);

CREATE TABLE IF NOT EXISTS requests (
  id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  decision VARCHAR(255) NOT NULL,
  registration_token VARCHAR(255) NOT NULL,
  date_request TIMESTAMP NOT NULL,
  date_decision TIMESTAMP
);

INSERT INTO users (email, password, role, date_request, date_decision)
VALUES ('admin@admin.com', 'admin', 'ROLE_ADMIN', CURRENT_TIMESTAMP, NULL);