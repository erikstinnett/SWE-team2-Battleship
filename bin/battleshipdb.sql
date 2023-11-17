CREATE TABLE gameData
(username VARCHAR (20),
password VARBINARY (16) NOT NULL,
wins INTEGER (100),
losses INTEGER (100),
CONSTRAINT gameData_username_pk PRIMARY KEY (username));