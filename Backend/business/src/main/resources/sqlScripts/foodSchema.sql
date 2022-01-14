CREATE TABLE food(
	fid INT NOT NULL AUTO_INCREMENT,
	fname VARCHAR(80),
	protein INT,
	carb INT,
	fat INT,
	calorie INT,
	price DOUBLE,
	category varchar(80),
	located INT NOT NULL,
	rating DOUBLE DEFAULT 0,
	rated INT DEFAULT 0,
	PRIMARY KEY(fid),
	FOREIGN KEY(located) REFERENCES restaurant(restaurant_id)
);

ALTER TABLE food AUTO_INCREMENT=0;
