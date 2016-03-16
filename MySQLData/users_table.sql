Use orderinfo;
create table users ( 
	userid int(10) unsigned NOT NULL AUTO_INCREMENT,
	username varchar(15),
    password varbinary(256),
    userprivileges int(1),
    PRIMARY KEY (userid)
);