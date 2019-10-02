# FAMILY LIBRARY MANAGER

Java-based desktop app for the managemnt of an individual/family book collection, including location data, and loan tracking.

## Requirements
- Spring Framework:
	- Spring-core
	- Spring-context
	- Spring-tx
	- Spring-data-jpa
- H2 database
- HiakriCP
- Hibernate

## Installation


### Database Setup

- For pre-JPA versions, use this sql statements to reproduce database:
```sql
create database if not exists web_library;

create table if not exists web_library.locations(
	location_index int not null auto_increment primary key,
	address varchar(45),
	room varchar(45),
	furniture varchar(45),
	details varchar(45),
	registration_date date not null,
	modification_date date
	);

create table if not exists web_library.books(
  book_index int not null auto_increment primary key,  
  title text not null, 
  author text not null,
  genre text, 
  publisher text, 
  isbn text,
  publish_date text, 
  purchase_date text,
  location int, 
  loaned boolean, 
  registration_date date not null,
  modification_date date,
  foreign key(location) references web_library.locations(location_index)
  );

create table if not exists web_library.loans(
	loan_index int not null auto_increment primary key,
	book int,
	borrower varchar(100),
	loan_date date not null,
	return_date date,
	constraint foreign key (book) references web_library.books(book_index)
	);

CREATE USER 'admin'@'%' IDENTIFIED BY 'mysecretpassword';
GRANT ALL PRIVILEGES ON web_library.* TO 'admin'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;
exit

```


## Usage
Execute jar file. Navigate UI.


## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[GPLv2](https://choosealicense.com/licenses/gpl-2.0/)
