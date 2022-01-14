CREATE TABLE ticket(
	ticket_id INT NOT NULL AUTO_INCREMENT,
	user_id VARCHAR(50),
	admin_id VARCHAR(50),
	date_assigned TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	text VARCHAR(300),
	category VARCHAR(30),
	PRIMARY KEY(ticket_id),
	FOREIGN KEY(user_id) REFERENCES user(user_email)  ON DELETE CASCADE,
	FOREIGN KEY(admin_id) REFERENCES user(user_email)  ON DELETE CASCADE
);