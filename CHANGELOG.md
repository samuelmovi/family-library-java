# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]
- create all necessary tests
- replace the column width scheme with something better
- all books table not refreshing when adding new book
- home panel still erratic about showing collection info, buttons, and welcome message
- fix column width mismatch problem
- programmatically click ok buttons when adding items during tests
- change delete book table model fom all books model to all available books model


## 2019-10-12
### Changed
- tweaks to repositories

### Added
- more controller tests

## 2019-10-11
### Fixed
- method to modify book

### Added
- new controller test

### Changed
- improvements to model classes

## 2019-10-10
### Fixed
- controller code for the search book button
### Added
- controller class tests

## 2019-10-05
### Fixed
- tests now using correct test database

## 2019-10-02
### Changed
- refactor project with Spring and JPA

### Fixed
- collection summary not showing in welcome panel

## 2019-09-20
### Fixed
- refreshing all books tab function was not using book views

## 2019-09-18
### Changed
- Moved project to maven on IntelliJ
- Moved database to H2 file-based database

### Added
- new book samuelmovi.familyLibraryJava.view with joined location name

## 2019-09-15

### Changed
- only string fields in search book dropdown, translations in the strings files
- restructuring of strings file

### Added
- added searchFieldText to strings file
- total books, and loand in library displayed in welcome panel

## 2019-09-14

### Added
- functionality to search book button

### Fixed
- index causing errors, changed it to string
- make table contents span the whole table
- find way to feed languag-specific aliases to the table samuelmovi.familyLibraryJava.model
- fix erroneous content change when modifying location
- fix empty rows in table for each real row
- fix modifying entry doubles entry in samuelmovi.familyLibraryJava.model

## 2019-09-13

### Fixed
- locations list properly displaying the information

### Changed
- removed calls to getBook_index in book dao because of autoincrement
- new map-based system for loading the strings from file
- got workaorund for setting content in samuelmovi.familyLibraryJava.view form samuelmovi.familyLibraryJava.controller

### Added
- mew functionality to the add book buttom in samuelmovi.familyLibraryJava.controller
- new implementation for add and modify buttons
- missing fields to models's pojos
- add close function to close button
- create list of fields for find book panel
- independent functions to populate table models

### Fixed
- Starting code wrong on Main. All better now
- Issue with dropdowns content in views construction
- functionality in daos


## 2019-09-12

### Changed
- Moved functions in old Model to their respective classes, as seen fit.

### Added
- New functionality for DAOs
