drop table if exists user;

create table User (
	user_id integer identity,
	login varchar(16) unique not null,
	password varchar(25) not null,
	email varchar(30) not null,
	firstName varchar(16),
	lastName VARCHAR (16)
);