USE [master]
GO
CREATE DATABASE LMSU_database

DROP TABLE IF EXISTS DeliveryOrder
DROP TABLE IF EXISTS DirectOrder
DROP TABLE IF EXISTS RenewalRequests
DROP TABLE IF EXISTS Penalties
DROP TABLE IF EXISTS OrderItems
DROP TABLE IF EXISTS Orders
DROP TABLE IF EXISTS Comments
DROP TABLE IF EXISTS ImportLogs
DROP TABLE IF EXISTS Books
DROP TABLE IF EXISTS Authors
DROP TABLE IF EXISTS Users
DROP TABLE IF EXISTS Subjects
DROP TABLE IF EXISTS Roles

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
	semester_no int NOT NULL,
	deleteStatus bit NOT NULL
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
	phoneNumber varchar(10),
	profilePicturePath varchar(MAX),
	activeStatus bit NOT NULL
);
GO
CREATE TABLE Authors(
	id varchar(255) NOT NULL PRIMARY KEY,
	name varchar(255) NOT NULL,
	bio varchar(MAX) NOT NULL,
	profilePicturePath varchar(MAX),
	deleteStatus bit NOT NULL
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
	coverPicturePath varchar(MAX)
);
GO
CREATE TABLE ImportLogs(
	id varchar(255) NOT NULL PRIMARY KEY,
	bookID varchar(255) NOT NULL FOREIGN KEY REFERENCES Books(id),
	managerID varchar(255) NOT NULL FOREIGN KEY REFERENCES Users(id),
	dateTaken date,
	supplier varchar(255) NOT NULL,
	quantity int,
);
GO
CREATE TABLE Comments(
	memberID varchar(255) NOT NULL FOREIGN KEY REFERENCES Users(id),
	bookID varchar(255) NOT NULL FOREIGN KEY REFERENCES Books(id),
	CONSTRAINT PK_UserComment PRIMARY KEY
    (
        memberID,
        bookID
    ),

	textComment varchar(MAX),
	rating decimal,
	editorID varchar(255),
	isEdited bit,
	deleteStatus bit NOT NULL
);
CREATE TABLE Orders(
	id int NOT NULL PRIMARY KEY IDENTITY(1,1),
	memberID varchar(255) NOT NULL FOREIGN KEY REFERENCES Users(id),
	orderDate date,
	lendMethod bit,
	activeStatus bit NOT NULL
);
CREATE TABLE OrderItems(
	id int NOT NULL PRIMARY KEY IDENTITY(1,1),
	orderID int NOT NULL FOREIGN KEY REFERENCES Orders(id),
	memberID varchar(255) NOT NULL FOREIGN KEY REFERENCES Users(id),
	bookID varchar(255) NOT NULL FOREIGN KEY REFERENCES Books(id),
	lendStatus int,
	returnDeadline date,
	lendDate date,
	returnDate date
);
CREATE TABLE Penalties(
	itemID int NOT NULL FOREIGN KEY REFERENCES OrderItems(id),
	penaltyAmount decimal,
	penaltyStatus bit
);
CREATE TABLE RenewalRequests(
	id varchar(255) NOT NULL PRIMARY KEY,
	itemID int NOT NULL FOREIGN KEY REFERENCES OrderItems(id),
	librarianID varchar(255) FOREIGN KEY REFERENCES Users(id),
	reason varchar(MAX),
	requestedExtendDate date,
	approvalStatus bit
);
CREATE TABLE DirectOrder(
	orderID int NOT NULL FOREIGN KEY REFERENCES Orders(id),
	librarianID varchar(255) FOREIGN KEY REFERENCES Users(id),
	scheduledTime datetime,
);
CREATE TABLE DeliveryOrder(
	orderID int NOT NULL FOREIGN KEY REFERENCES Orders(id),
	managerID varchar(255) FOREIGN KEY REFERENCES Users(id),
	deliverer varchar(255),
	scheduledDeliveryTime date,
	phoneNumber varchar(10),
	deliveryAddress1 varchar(MAX),
	deliveryAddress2 varchar(MAX),
	city varchar(255),
	district varchar(255),
	ward varchar(255)
);

insert into Subjects (id, name, semester_no, deleteStatus) values (1, 'Art', 1, 0);
insert into Subjects (id, name, semester_no, deleteStatus) values (2, 'History', 1, 0);
insert into Subjects (id, name, semester_no, deleteStatus) values (3, 'Language', 1, 0);
insert into Subjects (id, name, semester_no, deleteStatus) values (4, 'Music', 1, 0);
insert into Subjects (id, name, semester_no, deleteStatus) values (5, 'Math', 2, 0);
insert into Subjects (id, name, semester_no, deleteStatus) values (6, 'Writing', 2, 0);

insert into Authors (id, name, bio, profilePicturePath, deleteStatus) values (1, 'Fitzgerald', 'Maecenas ut massa quis augue luctus tincidunt. Nulla mollis molestie lorem. Quisque ut erat. Curabitur gravida nisi at nibh.', 'C:\a.png', 0);
insert into Authors (id, name, bio, profilePicturePath, deleteStatus) values (2, 'Esta', 'Donec ut dolor. Morbi vel lectus in quam fringilla rhoncus. Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis.', 'C:\a.png', 0);
insert into Authors (id, name, bio, profilePicturePath, deleteStatus) values (3, 'Kathie', 'Nulla ac enim. In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem. Duis aliquam convallis nunc. Proin at turpis a pede posuere nonummy. Integer non velit. Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue.', 'C:\a.png', 0);
insert into Authors (id, name, bio, profilePicturePath, deleteStatus) values (4, 'Bert', 'Fusce consequat.', 'C:\a.png', 0);
insert into Authors (id, name, bio, profilePicturePath, deleteStatus) values (5, 'Olympie', 'Duis consequat dui nec nisi volutpat eleifend. Donec ut dolor. Morbi vel lectus in quam fringilla rhoncus. Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis. Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci. Mauris lacinia sapien quis libero. Nullam sit amet turpis elementum ligula vehicula consequat. Morbi a ipsum. Integer a nibh. In quis justo.', 'C:\a.png', 0);
insert into Authors (id, name, bio, profilePicturePath, deleteStatus) values (6, 'Carter', 'Integer ac leo. Pellentesque ultrices mattis odio. Donec vitae nisi. Nam ultrices, libero non mattis pulvinar, nulla pede ullamcorper augue, a suscipit nulla elit ac nulla. Sed vel enim sit amet nunc viverra dapibus. Nulla suscipit ligula in lacus. Curabitur at ipsum ac tellus semper interdum.', 'C:\a.png', 0);

insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits, coverPicturePath) values (1, 'Software Engineering at Google: Lessons Learned', '5', 3, 'Wordpedia', '2020/09/17', 1, 16.55, 20, 1, '2020/12/09', 4.1, '439024713', '7749623681234', 'C:\b.png');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits, coverPicturePath) values (2, 'Fundamentals of Software Architecture', 6, 2, 'Divape', '2020/09/04', 2, 20.28, 13, 1, '2020/06/16', 1.0, '581621217', '5157100231234', 'C:\b.png');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits, coverPicturePath) values (3, 'Python Crash Course, 2nd Edition', 3, 6, 'Zoombeat', '2020/10/27', 3, 20.4, 1, 1, '2020/06/10', 0.1, '421605316', '3568879141234', 'C:\b.png');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits, coverPicturePath) values (4, 'Blockchain Bubble or Revolution', 6, 1, 'Janyx', '2021/01/24', 4, 23.2, 1, 0, '2020/06/18', 3.7, '404663114', '3665328051234', 'C:\b.png');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits, coverPicturePath) values (5, 'Expert Python Programming: Master Python', 4, 1, 'Pixoboo', '2021/04/30', 5, 32.37, 15, 1, '2020/11/13', 2.7, '035966056', '4426072141234', 'C:\b.png');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits, coverPicturePath) values (6, 'ASP.NET Core 5 and Angular: Full-stack Web development', 2, 1, 'Tagchat', '2020/08/01', 6, 18.3, 7, 1, '2020/11/15', 2.8, '901484984', '8576192651234', 'C:\b.png');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits, coverPicturePath) values (7, 'The Road to React: Your Journey to Master', 5, 3, 'Rhynyx', '2021/03/05', 7, 22.19, 15, 1, '2020/11/24', 4.0, '276332597', '0975709511234', 'C:\b.png');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits, coverPicturePath) values (8, 'JavaScript and JQuery: Interactive Front-End Web Development', 5, 5, 'Innotype', '2020/10/10', 8, 46.01, 11, 0, '2020/12/27', 0.4, '759833713', '801023987-9', 'C:\b.png');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits, coverPicturePath) values (9, 'PHP & MySQL: Server-side Web Development', 1, 1, 'Kwimbee', '2021/05/13', 9, 45.39, 6, 0, '2020/09/11', 1.3, '486202312', '4376406501234', 'C:\b.png');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits, coverPicturePath) values (10, 'Mobile Development with .NET', 1, 2, 'Kwimbee', '2020/10/29', 10, 28.24, 6, 1, '2021/04/30', 4.0, '076228356', '5154947231234', 'C:\b.png');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits, coverPicturePath) values (11, 'mattis nibh ligula nec sem duis aliquam convallis', 4, 1, 'Edgeclub', '2020/09/24', 11, 26.67, 18, 0, '2020/12/24', 2.7, '278713956', '1000736211234', 'C:\b.png');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits, coverPicturePath) values (12, 'porta volutpat erat quisque erat eros viverra eget congue', 4, 5, 'Trilith', '2021/04/03', 12, 27.87, 7, 1, '2020/06/23', 3.4, '199263196', '7785898321234', 'C:\b.png');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits, coverPicturePath) values (13, 'dapibus augue vel accumsan tellus', 5, 6, 'Youfeed', '2021/05/15', 13, 16.8, 12, 1, '2020/08/13', 1.9, '416309651', '9771421951234', 'C:\b.png');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits, coverPicturePath) values (14, 'odio curabitur convallist', 5, 5, 'Plajo', '2020/11/09', 14, 31.83, 10, 0, '2020/12/17', 1.7, '382417431', '4110274001234', 'C:\b.png');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits, coverPicturePath) values (15, 'mauris morbi non lectus aliquam sit amet', 2, 6, 'Vipe', '2021/04/29', 15, 35.47, 13, 0, '2020/09/13', 2.0, '507392395', '2147368091234', 'C:\b.png');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits, coverPicturePath) values (16, 'ante vivamus tortor', 1, 5, 'Kamba', '2021/03/10', 16, 30.62, 7, 1, '2020/08/21', 0.6, '239724965', '2125483091234', 'C:\b.png');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits, coverPicturePath) values (17, 'etiam vel augue vestibulum rutrum', 1, 4, 'Pixonyx', '2020/09/05', 17, 15.66, 11, 0, '2021/01/16', 1.4, '204390397', '8816519391234', 'C:\b.png');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits, coverPicturePath) values (18, 'in purus eu magna vulputate', 6, 5, 'Zoomlounge', '2021/02/01', 18, 20.04, 6, 1, '2021/03/26', 0.2, '189775883', '472913602-0', 'C:\b.png');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits, coverPicturePath) values (19, 'blandit nam nulla', 5, 6, 'Skiba', '2021/04/20', 19, 48.62, 4, 1, '2021/04/19', 2.5, '237484306', '0773323141234', 'C:\b.png');
insert into Books (id, title, authorID, subjectID, publisher, publishDate, description, price, quantity, deleteStatus, lastLentDate, avgRating, ISBN_tenDigits, ISBN_thirteenDigits, coverPicturePath) values (20, 'diam id ornare imperdiet sapien urna pretium', 2, 3, 'Kwideo', '2020/11/08', 20, 13.32, 6, 1, '2020/08/11', 0.3, '498193421', '2973443681234', 'C:\b.png');

insert into Roles (id, name) values ('1', 'ADM');
insert into Roles (id, name) values ('2', 'MNG');
insert into Roles (id, name) values ('3', 'LIB');
insert into Roles (id, name) values ('4', 'MEM');

insert into Users (id, name, roleID, semester_no, password, passwordGoogle, email, phoneNumber, profilePicturePath, activeStatus) values ('SE000001', 'Dat', '4', 1, '1234', '1234', 'dat@fpt.edu.vn', '123456789', 'C:\a.png', 0);
insert into Users (id, name, roleID, semester_no, password, passwordGoogle, email, phoneNumber, profilePicturePath, activeStatus) values ('LE000001', 'Nguyen', '4', 1, '1234', '1234', 'nguyen@fpt.edu.vn', '123456789', 'C:\a.png', 0);
insert into Users (id, name, roleID, semester_no, password, passwordGoogle, email, phoneNumber, profilePicturePath, activeStatus) values ('LIB00001', 'Dung', '3', 1, '1234', '1234', 'dung@fpt.edu.vn', '123456789', 'C:\a.png', 0);
insert into Users (id, name, roleID, semester_no, password, passwordGoogle, email, phoneNumber, profilePicturePath, activeStatus) values ('MNG00001', 'Phuc', '2', 1, '1234', '1234', 'phuc@fpt.edu.vn', '123456789', 'C:\a.png', 0);

insert into ImportLogs (id,	bookID,	managerID, dateTaken, supplier, quantity) values (1, 1, 'LIB00001', '2020/06/06', 'Xuong in Thien Phuc', 1);
insert into ImportLogs (id,	bookID,	managerID, dateTaken, supplier, quantity) values (2, 2, 'LIB00001', '2021/06/07', 'Xuong in Nguyen Dung', 1);

insert into Comments (memberID, bookID, textComment, rating, isEdited, deleteStatus) values ('LIB00001', 1, 'Not bad, could be more informative.', 0, 0, 0);
insert into Comments (memberID, bookID, textComment, rating, isEdited, deleteStatus) values ('LIB00001', 2, 'Great book, much insightful.', 5, 0, 0);
insert into Comments (memberID, bookID, textComment, rating, isEdited, deleteStatus) values ('SE000001', 1, 'Fine work, not much application though.', 3.5, 0, 0);
