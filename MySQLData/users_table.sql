Use orderinfo;
DROP TABLE IF EXISTS `users`;
create table users ( 
	userid int(10) unsigned NOT NULL AUTO_INCREMENT,
	username varchar(15),
    password varbinary(256),
    userprivileges int(1),
    PRIMARY KEY (userid)
);

LOCK TABLES `users` WRITE;
Insert into users VALUES (1, 'daniel', md5('ola'), 1);
UNLOCK TABLES;