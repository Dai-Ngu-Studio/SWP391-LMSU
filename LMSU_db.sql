USE [master]
GO
CREATE DATABASE LMSU_database

USE [LMSU_database]
GO
CREATE TABLE Roles(
	id varchar(255) NOT NULL PRIMARY KEY,
	name varchar(255)
);
GO
CREATE TABLE Subjects(
	id varchar(255) NOT NULL PRIMARY KEY,
	name varchar(255) NOT NULL,
	semester_no int NOT NULL
);
GO
CREATE TABLE Users(
	id varchar(255) NOT NULL PRIMARY KEY,
	roleID varchar(255) NOT NULL FOREIGN KEY REFERENCES Roles(id),
	semester_no int NOT NULL,
	password varchar(255),
	passwordGoogle varchar(255),
	email varchar(255),
	phoneNumber varchar(255),
	profilePicturePath varchar(255)
);
GO
CREATE TABLE Authors(
	id varchar(255) NOT NULL PRIMARY KEY,
	name varchar(255) NOT NULL,
	bio varchar(MAX) NOT NULL
);
GO
CREATE TABLE Books(
	id varchar(255) NOT NULL PRIMARY KEY,
	title varchar(255) NOT NULL,
	name varchar(255) NOT NULL,
	authorID varchar(255) NOT NULL FOREIGN KEY REFERENCES Authors(id),
	subjectID varchar(255) NOT NULL FOREIGN KEY REFERENCES Subjects(id),
	publisher varchar(255) NOT NULL,
	puplishDate varchar(255) NOT NULL,
	description varchar(MAX) NOT NULL,
	price decimal NOT NULL,
	quantity int NOT NULL,
	deleteStatus bit NOT NULL,
	lastLentDate date,
	avgRating decimal,
	ISBN_tenDigits varchar(10),
	ISBN_thirteenDigits varchar(13),
);
GO
CREATE TABLE ImportLogs(
	id varchar(255) NOT NULL PRIMARY KEY,
	dateTaken date,
	senderAddress varchar(255) NOT NULL,
	receiverAddress varchar(255) NOT NULL
);
GO
CREATE TABLE ImportMap(
	bookID varchar(255) NOT NULL FOREIGN KEY REFERENCES Books(id),
	importID varchar(255) NOT NULL FOREIGN KEY REFERENCES ImportLogs(id)
);
