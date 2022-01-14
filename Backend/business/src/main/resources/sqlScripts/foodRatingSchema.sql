CREATE TABLE foodRating(
	rating_id INT AUTO_INCREMENT,
	user_email VARCHAR(50),
	fid INT,
    rating INT,
	PRIMARY KEY(rating_id),
	FOREIGN KEY(user_email) REFERENCES user(user_email),
	FOREIGN KEY(fid) REFERENCES food(fid)
);

ALTER TABLE foodRating AUTO_INCREMENT=0;

-- TODO: Trigger for when a new rating is added, then update the food table
