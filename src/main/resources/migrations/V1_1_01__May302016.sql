
 CREATE TABLE users(
          username VARCHAR(50) NOT NULL ,
		  password VARCHAR(250) NOT NULL ,
		  PRIMARY KEY (username),
		  INDEX(username)
  );
  
  CREATE TABLE user_roles (
	  user_role_id INT NOT NULL AUTO_INCREMENT,
	  username varchar(50) NOT NULL,
	  role varchar(45) NOT NULL,
	  PRIMARY KEY (user_role_id),
	  FOREIGN KEY(username) REFERENCES users(username)
  );
  
  
  CREATE TABLE CUSTOMER ( 
     id INT  NOT NULL AUTO_INCREMENT,
     customerName VARCHAR(45) NULL , 
     customerAddress VARCHAR(45) NULL , 
     city VARCHAR(45) NULL , 
     zipcode VARCHAR(45) NULL , 
     PRIMARY KEY (id) 
   );
  
INSERT INTO users(username,password) VALUES ('SourceLeadAdmin','c9436c72cea8f1f90296f70c2acb902cq7bff165a0b9c02b4da1a6bc299652afffq73f719ffdc2520d71b0d78f871ac9f0ecq710277cf00cdc41a422eefee6c7a1de11q7');
INSERT INTO users(username,password) VALUES ('SourceLeadUser','c9436c72cea8f1f90296f70c2acb902cq7bff165a0b9c02b4da1a6bc299652afffq73f719ffdc2520d71b0d78f871ac9f0ecq710277cf00cdc41a422eefee6c7a1de11q7');


INSERT INTO user_roles (username, role) VALUES ('SourceLeadAdmin', 'ROLE_USER');
INSERT INTO user_roles (username, role) VALUES ('SourceLeadAdmin', 'ROLE_ADMIN');
INSERT INTO user_roles (username, role) VALUES ('SourceLeadUser', 'ROLE_USER');

 
     