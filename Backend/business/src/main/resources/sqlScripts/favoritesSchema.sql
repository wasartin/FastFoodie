CREATE TABLE favorites(
	favorites_id INT NOT NULL AUTO_INCREMENT,
	user_id VARCHAR(50),
	fid INT NOT NULL,
	PRIMARY KEY(favorites_id),
	FOREIGN KEY(user_id) REFERENCES user(user_email),
	FOREIGN KEY(fid) REFERENCES food(fid)
);

ALTER TABLE favorites AUTO_INCREMENT=0;