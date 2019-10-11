# SPRING LIBRARY MANAGER

## MySQL Setup

- Create database: `create database if not exists web_library;`
- Create user:
```
CREATE USER 'admin'@'%' IDENTIFIED BY 'supersecretpassword';
GRANT ALL PRIVILEGES on web_library.* to 'admin'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;
```
- Create tables:
```
create table if not exists web_library.locations(
	index int not null auto_increment primary key,
	address varchar(45),
	room varchar(45),
	furniture varchar(45),
	details varchar(45),
	registrationDate date,
	modificationDate date
	);

create table if not exists web_library.books(
  index int not null auto_increment primary key,  
  title text, 
  author text,
  genre text, 
  editorial text, 
  isbn text,
  publishDate text, 
  purchase_date text,
  location int, 
  loaned boolean, 
  registrationDate date,
  modificationDate date,
  foreign key(location) references web_library.locations(index)
  );

create table if not exists web_library.loans(
	index int not null auto_increment primary key,
	book int,
	borrower varchar(100),
	registrationDate date,
	modificationDate date,
	constraint foreign key (book) references web_library.books(index)
	);

```
