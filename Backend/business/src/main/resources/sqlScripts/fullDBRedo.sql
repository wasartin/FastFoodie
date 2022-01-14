
DROP TABLE IF EXISTS ticket;
DROP TABLE IF EXISTS food_rating;
DROP TABLE IF EXISTS favorites;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS food;
DROP TABLE IF EXISTS restaurant;

CREATE TABLE restaurant(
	restaurant_id INT NOT NULL,
	restaurant_name VARCHAR(50),
	last_updated DATE,
	PRIMARY KEY(restaurant_id)
);

INSERT INTO restaurant VALUES 
	(0, 'McDonalds', DATE'2017-12-30'),
	(1, 'Chik-Fil-A', DATE'2018-10-12'),	
	(2, 'Subway', DATE'2016-08-15'),
    (3, 'Seabass Bar and Grill', DATE'1991-06-03');
	
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

INSERT INTO food (fname, protein, carb, fat, calorie, price, category, located) VALUES 
	('6-inch Bacon, Egg and Cheese', 25, 44, 18, 450, 4.23, 'Breakfast', 2),
	('6-inch Bacon, Egg White and Cheese', 26, 45, 13, 410, 3.35, 'Breakfast', 2),
	('6-inch Black Forest Ham, Egg and Cheese', 24, 45, 14, 400, 4.32, 'Breakfast', 2),
	('6-inch Egg and Cheese', 19, 44, 13, 370, 4.37, 'Breakfast', 2),
	('6-inch Egg White and Cheese', 20, 44, 8, 330, 4.12, 'Breakfast', 2),
	('6-inch Egg White and Cheese with ham', 25, 45, 9, 360, 3.34, 'Breakfast', 2),
	('6-inch Steak, Egg and Cheese', 28, 46, 15, 440, 3.61, 'Breakfast', 2),
	('6-inch Steak, Egg White and Cheese', 29, 46, 10, 400, 3.43, 'Breakfast', 2),
	('Black Forest Ham Salad', 12, 12, 3, 340, 3.00, 'Salad', 2),
	('Buffalo Chicken', 17, 2, 2, 100, 1.31, 'Chicken', 2),
	('Chicken Strips', 16, 0, 1.5, 80, 1.01, 'Chicken', 2),
	('Chicken Teriyaki with Spinach', 27, 23, 3, 415, 2.74, 'Salad', 2),
	('Chipotle Chicken with Guacamole Salad', 24, 19, 9, 466, 3.05, 'Salad', 2),
	('Double Chicken Chopped Salad', 36, 10, 4.5, 425, 1.77, 'Salad', 2),
	('Falafel Balls, 3', 12, 39, 3.5, 220, 1.90, 'Vegetarian', 2),
	('Hot Pastrami Melt Salad', 23, 12, 29, 394, 4.63, 'Salad', 2),
	('Meatballs', 13, 16, 16, 260, 2.10, 'Beef', 2),
	('Monterey Chicken Melt Salad', 22, 10, 7, 354, 3.47, 'Salad', 2),
	('Oven Roasted Chicken Salad', 19, 10, 2.5, 354, 2.60, 'Salad', 2),
	('Roast Beef', 16, 1, 2.5, 90, 1.67, 'Beef', 2),
	('Roast Beef Salad', 19, 11, 3.5, 354, 4.42, 'Salad', 2),
	('Roasted Chicken Patty', 15, 4, 2.5, 90, 1.42, 'Chicken', 2),
	('Rotisserie-Style Chicken with Cheese', 26, 24, 9, 422, 2.25, 'Salad', 2),
	('Rotisserie-Syle Chicken Fresh Fit', 24, 10, 4.5, 369, 1.62, 'Salad', 2),
	('Seafood Sensation', 5, 7, 16, 190, 2.55, 'Seafood', 2),
	('Subway Club Salad', 18, 12, 3.5, 140, 2.00, 'Salad', 2),
	('Sweet Onion Chicken Teriyaki Salad', 20, 32, 3, 411, 1.81, 'Salad', 2),
	('Teriyaki Chicken', 18, 16, 2.5, 170, 1.06, 'Chicken', 2),
	('Tuna', 10, 0, 24, 260, 2.09, 'Seafood', 2),
	('Turkey and Bacon Guacamole', 19, 19, 20, 330, 3.02, 'Salad', 2),
	('Turkey Breast', 9, 2, 1, 50, 0.50, 'Turkey', 2),
	('Turkey Breast and Ham Salad', 12, 12, 2.5, 340, 4.63, 'Salad', 2),
	('Turkey Breast Salad', 12, 12, 2, 340, 4.49, 'Salad', 2),
	('Turkey Italiano Salad', 18, 16, 29, 403, 2.52, 'Salad', 2),
	('Veggie Delite Salad', 3, 10, 1, 284, 2.89, 'Salad', 2),
	('Veggie Patty', 15, 12, 5, 160, 2.17, 'Vegetarian', 2),
    ('Artisan Grilled Chicken Sandwich', 33, 43, 6, 360, 9.19, 'Chicken', 0),
	('Bacon Clubhouse Burger, no sauce', 39, 49, 33, 650, 5.11, 'Beef', 0),
	('Bacon Ranch with Crispy Chicken, no dressing', 34, 26, 29, 490, 3.38, 'Salad', 0),
	('Bacon Ranch with Grilled Chicken, no dressing', 38, 9, 14, 310, 4.80, 'Salad', 0),
	('Bacon, Egg and Cheese Bagel', 26, 55, 27, 570, 6.75, 'Breakfast', 0),
	('Bacon, Egg and Cheese Biscuit (reg)', 19, 36, 26, 450, 8.32, 'Breakfast', 0),
	('Bacon, Egg and Cheese McGriddle', 19, 48, 21, 450, 4.26, 'Breakfast', 0),
	('Big Breakfast with Hotcakes', 33, 105, 56, 1050, 8.90, 'Breakfast', 0),
	('Big Mac, no sauce', 24, 45, 19, 450, 5.20, 'Beef', 0),
	('Cheeseburger', 15, 33, 12, 300, 3.82, 'Beef', 0),
	('Crispy Chicken Deluxe Sandwich, no mayo', 28, 61, 19, 530, 7.02, 'Beef', 0),
	('Double Cheeseburger', 25, 35, 22, 440, 6.78, 'Beef', 0),
	('Double Quarter Pounder w/Cheese no bun', 45, 5, 42, 580, 3.65, 'Beef', 0),
	('Double Quarter Pounder with Cheese', 50, 43, 45, 780, 2.68, 'Beef', 0),
	('Egg McMuffin', 17, 29, 12, 300, 9.94, 'Breakfast', 0),
	('Egg White Delight', 17, 29, 8, 250, 3.77, 'Breakfast', 0),
	('Filet-O-Fish, no sauce', 15, 38, 10, 300, 3.00, 'Seafood', 0),
	('Fruit and Maple Oatmeal', 5, 58, 4, 290, 7.30, 'Breakfast', 0),
	('Hamburger', 12, 32, 8, 250, 3.71, 'Beef', 0),
	('Hotcakes and Sausage', 15, 55, 25, 510, 5.72, 'Breakfast', 0),
	('McChicken, no mayo', 14, 40, 11, 320, 6.16, 'Chicken', 0),
	('McDouble', 22, 34, 18, 390, 5.31, 'Beef', 0),
	('McNuggets (10)', 22, 30, 30, 470, 5.06, 'Chicken', 0),
	('McNuggets (20)', 44, 59, 59, 940, 3.16, 'Chicken', 0),
	('McNuggets (4)', 9, 12, 12, 190, 4.81, 'Chicken', 0),
	('McNuggets (6)', 13, 18, 18, 280, 5.18, 'Chicken', 0),
	('Quarter Pounder with Cheese', 31, 42, 28, 540, 4.99, 'Beef', 0),
	('Royal with Cheese', 31, 42, 28, 540, 5.00, 'Beef', 0),
	('Sausage Biscuit (reg)', 11, 33, 30, 440, 8.92, 'Breakfast', 0),
	('Sausage Biscuit with Egg (reg)', 17, 35, 35, 520, 7.41, 'Breakfast', 0),
	('Sausage Burrito', 12, 26, 16, 300, 7.51, 'Breakfast', 0),
	('Sausage McGriddle', 11, 44, 25, 440, 6.54, 'Breakfast', 0),
	('Sausage McMuffin', 14, 28, 25, 400, 6.50, 'Breakfast', 0),
	('Sausage McMuffin with Egg', 21, 29, 30, 470, 8.62, 'Breakfast', 0),
	('Sausage, Egg and Cheese McGriddle', 19, 47, 34, 570, 4.31, 'Breakfast', 0),
	('Southwest Salad with Crispy Chicken, no dressing', 28, 43, 26, 510, 5.20, 'Salad', 0),
	('Southwest Salad with Grilled Chicken. no dressing', 33, 26, 11, 330, 4.56, 'Salad', 0),
	('Steak, Egg and Cheese Biscuit (reg)', 25, 37, 32, 530, 6.20, 'Breakfast', 0),
    ('Asian Salad', 29, 24, 13, 330, 3.26, 'Salad', 1),
	('Bacon Platter', 24, 52, 34, 610, 7.11, 'Breakfast', 1),
	('Chicken Biscuit', 16, 48, 20, 440, 5.33, 'Breakfast', 1),
	('Chicken Breakfast Burrito', 26, 43, 20, 460, 4.91, 'Breakfast', 1),
	('Chicken Deluxe', 32, 43, 23, 500, 1.87, 'Chicken', 1),
	('Chicken Nuggets, 6', 21, 7, 10, 200, 3.76, 'Chicken', 1),
	('Chicken Platter', 30, 58, 37, 680, 7.16, 'Breakfast', 1),
	('Chicken, Egg and Cheese bagel', 27, 48, 20, 480, 4.34, 'Breakfast', 1),
	('Chick-n-Minis', 20, 40, 14, 370, 4.86, 'Breakfast', 1),
	('Chick-n-Strips, 4', 43, 21, 24, 470, 4.66, 'Chicken', 1),
	('Cobb Salad no dressing', 40, 27, 27, 500, 3.43, 'Salad', 1),
	('Grilled Chicken Club', 38, 41, 14, 440, 2.02, 'Chicken', 1),
	('Grilled Chicken Club Sandwich', 38, 41, 14, 440, 1.00, 'Chicken', 1),
	('Grilled Chicken Cool Wrap', 36, 30, 13, 340, 2.27, 'Chicken', 1),
	('Grilled Chicken Sandwich', 30, 40, 5, 320, 1.32, 'Chicken', 1),
	('Grilled Market Salad', 23, 17, 5, 200, 2.85, 'Chicken', 1),
	('Grilled Market Salad', 26, 26, 14, 320, 3.55, 'Salad', 1),
	('Grilled Nuggets, 6', 17, 3, 3, 100, 4.92, 'Chicken', 1),
	('Grilled Nuggets, 8', 23, 4, 3, 140, 4.15, 'Chicken', 1),
	('Sausage Platter', 28, 52, 54, 810, 7.23, 'Breakfast', 1),
	('Spicy Southwest Salad', 34, 32, 19, 420, 4.73, 'Salad', 1);
    
CREATE TABLE user(
	user_email VARCHAR(50) NOT NULL,
	user_type CHAR(10),
	PRIMARY KEY(user_email)
);

INSERT INTO user VALUES 
	('TomDodge@gmail.com', 'registered'),
	('wasartin@iastate.edu', 'admin'),
	('BigBird@sesameStreet.com', 'registered'),
	('TunaFey@rockCentralPlaza.com', 'registered'),
	('hughMan@planetexpress.com', 'registered'),
	('longjohnSilver@plentyOfFish.com', 'registered'),
	('milkShakeSuma@dangerCart.com', 'admin'),
	('vincentAdultman@corporatePlace.com', 'admin'),
	('gilbertPatel@Uruk.com', 'registered'),
	('cowman@Springs.com', 'registered'),
	('grayman98@gmail.com', 'admin'),
    ('newunusedname@gmail.com', 'admin'),
    ('graysoncox98@gmail.com', 'admin');
	
    
CREATE TABLE favorites(
	favorites_id INT NOT NULL AUTO_INCREMENT,
	user_id VARCHAR(50),
	fid INT NOT NULL,
	PRIMARY KEY(favorites_id),
	FOREIGN KEY(user_id) REFERENCES user(user_email),
	FOREIGN KEY(fid) REFERENCES food(fid)
);

ALTER TABLE favorites AUTO_INCREMENT=0;

INSERT INTO favorites(user_id, fid) VALUES
	('BigBird@sesameStreet.com', 1),
	('BigBird@sesameStreet.com', 3),
	('BigBird@sesameStreet.com', 4), 
	('TomDodge@gmail.com', 20),
	('TomDodge@gmail.com', 40),
	('TomDodge@gmail.com', 80), 
	('vincentAdultman@corporatePlace.com', 7),
	('vincentAdultman@corporatePlace.com', 13),
	('vincentAdultman@corporatePlace.com', 64),
    ('grayman98@gmail.com', 13),
    ('grayman98@gmail.com', 50),
    ('grayman98@gmail.com', 78),
    ('newunusedname@gmail.com', 13),
    ('newunusedname@gmail.com', 64),
    ('newunusedname@gmail.com', 38),
    ('graysoncox98@gmail.com', 12),
    ('graysoncox98@gmail.com', 64);
    
    
CREATE TABLE food_rating(
	rating_id INT AUTO_INCREMENT,
	user_email VARCHAR(50),
	fid INT,
    rating INT,
	PRIMARY KEY(rating_id),
	FOREIGN KEY(user_email) REFERENCES user(user_email),
	FOREIGN KEY(fid) REFERENCES food(fid)
);

ALTER TABLE food_rating AUTO_INCREMENT=0;

INSERT INTO food_rating (user_email, fid, rating) VALUES 
	('grayman98@gmail.com', 50, 4),
	('grayman98@gmail.com', 66, 3),
	('newunusedname@gmail.com', 24, 2),
	('newunusedname@gmail.com', 50, 4),
	('wasartin@iastate.edu', 50, 1),
	('wasartin@iastate.edu', 66, 4),
	('wasartin@iastate.edu', 24, 5),
	('hughMan@planetexpress.com', 50, 1);
    
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

INSERT INTO ticket(user_id, admin_id, text, category) VALUES
	('TomDodge@gmail.com', 'wasartin@iastate.edu', 'My Tom Dodge just is not as Tom Dodge as it used to be', 'personalProblems'),
    ('hughMan@planetexpress.com', 'newunusedname@gmail.com', 'None of the McDonalds I have been have a Royal with Cheese. AM I doing something wrong?', 'food'),
    ('longjohnSilver@plentyOfFish.com', 'graysoncox98@gmail.com', 'Sometimes I like to send a message to the admins just to make sure they are ok, but not this time because you guys have really screwed up your stupid little app. I am not even going to tell you what is wrong, go figure it out.', 'admins');