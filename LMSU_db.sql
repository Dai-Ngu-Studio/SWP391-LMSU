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
	name varchar(255) NOT NULL,
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
	authorID varchar(255) NOT NULL FOREIGN KEY REFERENCES Authors(id),
	subjectID varchar(255) NOT NULL FOREIGN KEY REFERENCES Subjects(id),
	publisher varchar(255) NOT NULL,
	publishDate date,
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
insert into Subjects (id, name, semester_no) values (1, 'Art', 1);
insert into Subjects (id, name, semester_no) values (2, 'History', 1);
insert into Subjects (id, name, semester_no) values (3, 'Language', 1);
insert into Subjects (id, name, semester_no) values (4, 'Music', 1);
insert into Subjects (id, name, semester_no) values (5, 'Math', 2);
insert into Subjects (id, name, semester_no) values (6, 'Writing', 2);

insert into Authors (id, name, bio) values (1, 'Fitzgerald', 'Maecenas ut massa quis augue luctus tincidunt. Nulla mollis molestie lorem. Quisque ut erat. Curabitur gravida nisi at nibh.');
insert into Authors (id, name, bio) values (2, 'Esta', 'Donec ut dolor. Morbi vel lectus in quam fringilla rhoncus. Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis.');
insert into Authors (id, name, bio) values (3, 'Kathie', 'Nulla ac enim. In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem. Duis aliquam convallis nunc. Proin at turpis a pede posuere nonummy. Integer non velit. Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue.');
insert into Authors (id, name, bio) values (4, 'Bert', 'Fusce consequat.');
insert into Authors (id, name, bio) values (5, 'Olympie', 'Duis consequat dui nec nisi volutpat eleifend. Donec ut dolor. Morbi vel lectus in quam fringilla rhoncus. Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis. Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci. Mauris lacinia sapien quis libero. Nullam sit amet turpis elementum ligula vehicula consequat. Morbi a ipsum. Integer a nibh. In quis justo.');
insert into Authors (id, name, bio) values (6, 'Carter', 'Integer ac leo. Pellentesque ultrices mattis odio. Donec vitae nisi. Nam ultrices, libero non mattis pulvinar, nulla pede ullamcorper augue, a suscipit nulla elit ac nulla. Sed vel enim sit amet nunc viverra dapibus. Nulla suscipit ligula in lacus. Curabitur at ipsum ac tellus semper interdum.');

insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits) values (1, 'sem fusce consequat nulla nisl nunc nisl', '5', 3, 'Wordpedia', '2020/09/17', 1, 16.55, 20, 1, '2020/12/09', 4.1, '439024713', '7749623681234');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits) values (2, 'at turpis a pede posuere nonummy', 6, 2, 'Divape', '2020/09/04', 2, 20.28, 13, 1, '2020/06/16', 1.0, '581621217', '5157100231234');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits) values (3, 'lacus morbi quis tortor', 3, 6, 'Zoombeat', '2020/10/27', 3, 20.4, 1, 1, '2020/06/10', 0.1, '421605316', '3568879141234');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits) values (4, 'pellentesque eget nunc donec quis orci eget orci', 6, 1, 'Janyx', '2021/01/24', 4, 23.2, 1, 0, '2020/06/18', 3.7, '404663114', '3665328051234');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits) values (5, 'risus auctor sed tristique', 4, 1, 'Pixoboo', '2021/04/30', 5, 32.37, 15, 1, '2020/11/13', 2.7, '035966056', '4426072141234');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits) values (6, 'vitae nisl', 2, 1, 'Tagchat', '2020/08/01', 6, 18.3, 7, 1, '2020/11/15', 2.8, '901484984', '8576192651234');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits) values (7, 'cubilia curae ', 5, 3, 'Rhynyx', '2021/03/05', 7, 22.19, 15, 1, '2020/11/24', 4.0, '276332597', '0975709511234');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits) values (8, 'justo eu massa donec dapibus duis at velit eu', 5, 5, 'Innotype', '2020/10/10', 8, 46.01, 11, 0, '2020/12/27', 0.4, '759833713', '801023987-9');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits) values (9, 'mauris', 1, 1, 'Kwimbee', '2021/05/13', 9, 45.39, 6, 0, '2020/09/11', 1.3, '486202312', '4376406501234');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits) values (10, 'habitasse platea dictumst', 1, 2, 'Kwimbee', '2020/10/29', 10, 28.24, 6, 1, '2021/04/30', 4.0, '076228356', '5154947231234');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits) values (11, 'mattis nibh ligula nec sem duis aliquam convallis', 4, 1, 'Edgeclub', '2020/09/24', 11, 26.67, 18, 0, '2020/12/24', 2.7, '278713956', '1000736211234');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits) values (12, 'porta volutpat erat quisque erat eros viverra eget congue', 4, 5, 'Trilith', '2021/04/03', 12, 27.87, 7, 1, '2020/06/23', 3.4, '199263196', '7785898321234');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits) values (13, 'dapibus augue vel accumsan tellus', 5, 6, 'Youfeed', '2021/05/15', 13, 16.8, 12, 1, '2020/08/13', 1.9, '416309651', '9771421951234');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits) values (14, 'odio curabitur convallist', 5, 5, 'Plajo', '2020/11/09', 14, 31.83, 10, 0, '2020/12/17', 1.7, '382417431', '4110274001234');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits) values (15, 'mauris morbi non lectus aliquam sit amet', 2, 6, 'Vipe', '2021/04/29', 15, 35.47, 13, 0, '2020/09/13', 2.0, '507392395', '2147368091234');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits) values (16, 'ante vivamus tortor', 1, 5, 'Kamba', '2021/03/10', 16, 30.62, 7, 1, '2020/08/21', 0.6, '239724965', '2125483091234');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits) values (17, 'etiam vel augue vestibulum rutrum', 1, 4, 'Pixonyx', '2020/09/05', 17, 15.66, 11, 0, '2021/01/16', 1.4, '204390397', '8816519391234');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits) values (18, 'in purus eu magna vulputate', 6, 5, 'Zoomlounge', '2021/02/01', 18, 20.04, 6, 1, '2021/03/26', 0.2, '189775883', '472913602-0');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits) values (19, 'blandit nam nulla', 5, 6, 'Skiba', '2021/04/20', 19, 48.62, 4, 1, '2021/04/19', 2.5, '237484306', '0773323141234');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits) values (20, 'diam id ornare imperdiet sapien urna pretium', 2, 3, 'Kwideo', '2020/11/08', 20, 13.32, 6, 1, '2020/08/11', 0.3, '498193421', '2973443681234');