create table if not exists locations(
  locations_index integer primary key,
  address text,
  room text,
  furniture text,
   details text
   );
create table if not exists books(
  books_index integer primary key, 
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
  foreign key(location) references locations(locations_index)
  );
create table if not exists loans(
  loans_index integer primary key, 
  recipient text, 
  book int, 
  loan_date date,
  return_date date,
  foreign key (book) references books(books_index));


