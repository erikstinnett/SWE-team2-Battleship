DROP TABLE gameData;

CREATE TABLE gameData
(username VARCHAR (20),
password VARBINARY (16) NOT NULL,
wins INTEGER (100),
losses INTEGER (100),
CONSTRAINT gameData_username_pk PRIMARY KEY (username));

INSERT INTO gameData VALUES('commander',AES_ENCRYPT('1234567','key'),998,3);
INSERT INTO gameData VALUES('private',AES_ENCRYPT('password','key'),30,50);
INSERT INTO gameData VALUES('George',AES_ENCRYPT('secured','key'),50,10);
INSERT INTO gameData VALUES('xX_Gamer_Xx',AES_ENCRYPT('mrgames','key'),3,5);
