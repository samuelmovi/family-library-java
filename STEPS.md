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
	location_index int not null auto_increment primary key,
	address varchar(45),
	room varchar(45),
	furniture varchar(45),
	details varchar(45),
	registration_date date,
	modification_date date
	);

create table if not exists web_library.books(
  book_index int not null auto_increment primary key,  
  title text, 
  author text,
  genre text, 
  editorial text, 
  isbn text,
  publish_date text, 
  purchase_date text,
  location int, 
  loaned boolean, 
  registration_date date,
  modification_date date,
  foreign key(location) references web_library.locations(location_index)
  );

create table if not exists web_library.loans(
	loan_index int not null auto_increment primary key,
	book int,
	borrower varchar(100),
	registration_date date,
	modification_date date,
	constraint foreign key (book) references web_library.books(book_index)
	);

```
