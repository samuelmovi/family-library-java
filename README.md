# Family Library [java]
This software has been developed for the management of personal private book collections.  It aims to be a pragmatic, portable, local solution to keep track of a personal and/or family book collection, as well as their locations, and any book loans.

## Requirements
- Java 8
- SQLite JDBC ([download](https://bitbucket.org/xerial/sqlite-jdbc/downloads/))

## Installation
The program doesn't require installation. Simply compile project with your choice of IDE, or download the latest build from [here](http://pc.cd/mKritalK), and execute the jar file.

## Usage
### Welcome screen
It is the first thing you'll see after execution. It has 3 buttons, for Books, Locations, and Loans. They each bring up the interface for its property's management.

### Books
Books are defined by the following fields:

- Title
- Author
- Genre
- Editorial
- ISBN
- Publish-date
- Purchase-date
- Location

There are 5 tabs for book management:

- All: it shows every book in the database.
- Search: you can choose a field, input a value and search for database entries that match.
- New: here you can enter info on a new book to add.
- Modify: click on a row from table to populate the fields. Modify accordingly and save changes.
- Delete: choose entry from table and click delete button to remove from database.

### Locations
Locations are defined by the following fields:

- Address
- Room
- Furniture
- Details

It has 4 tabs:

- All: it shows all locations in the database.
- New: you can enter info on a new book location.
- Modify: click on a row from table to populate fields, modify them, and save the changes.
- Delete: choose entry from table and click delete button to remove from database.


### Loans
Loans have 4 fields:

- Book name
- Loaned to
- Date of loan
- Date of return

There are 3 tabs to manage loans:

- All: it shows all book loans, past and current.
- Loan: choose a book, input the borrower's name, and save the transaction to the database.
- Return: choose from the table of current loans, and set it as returned.

## Multilingual Support
The text on the UI is sourced from `` strings.txt ``, which is a key:value flat file. With the project, I've included 2 string files `` strings-EN.txt `` and `` strings-ES.txt ``. Many more UI translations can be made by changing the string values in the file.


To change the language, copy your chosen language file `` strings-XX.txt `` to the same folder, rename file to `` strings.txt ``, and restart the application.


## Database Storage
The application uses an [SQLite](https://en.wikipedia.org/wiki/SQLite) database engine. On first run, the application will create a file named `` LibraryData.sqlite ``. This file contains ALL of the database information in binary format, and it should be readable with any other SQLite application.

To ensure reliability I have also implemented a way to save the data in a human-readable format. In this particular case, it's [CSV](https://en.wikipedia.org/wiki/Comma-separated_values).

To save data to CSV click on the "Menu" item in the top bar, and choose "Save Data". It will prompt you with a confirmation dialog. If YES, a CSV file will be created for each database table in the project's root folder. These files will follow the naming convention: YYYY-MM-DDThh-mm-ss_-_$table.csv

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[GPLv2](https://choosealicense.com/licenses/gpl-2.0/)