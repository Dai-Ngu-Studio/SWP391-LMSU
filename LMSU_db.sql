--USE [master]
--GO
--    CREATE DATABASE LMSU_database
GO
    DROP TABLE IF EXISTS Announcement
GO
    DROP TABLE IF EXISTS Feedback
GO
    DROP TABLE IF EXISTS DeliveryOrder
GO
    DROP TABLE IF EXISTS DirectOrder
GO
    DROP TABLE IF EXISTS RenewalRequests
GO
    DROP TABLE IF EXISTS OrderItems
GO
    DROP TABLE IF EXISTS Orders
GO
    DROP TABLE IF EXISTS Comments
GO
    DROP TABLE IF EXISTS ImportLogs
GO
    DROP TABLE IF EXISTS AuthorBookMaps
GO
    DROP TABLE IF EXISTS Books
GO
    DROP TABLE IF EXISTS Authors
GO
    DROP TABLE IF EXISTS Users
GO
    DROP TABLE IF EXISTS Subjects
GO
    DROP TABLE IF EXISTS Roles
GO
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
    CREATE TABLE AuthorBookMaps(
        id int NOT NULL PRIMARY KEY IDENTITY(1, 1),
        authorID varchar(255) NOT NULL FOREIGN KEY REFERENCES Authors(id),
        bookID varchar(255) NOT NULL FOREIGN KEY REFERENCES Books(id),
    );

GO
    CREATE TABLE ImportLogs(
        id int NOT NULL PRIMARY KEY IDENTITY(1, 1),
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
        CONSTRAINT PK_UserComment PRIMARY KEY (memberID, bookID),
        textComment nvarchar(MAX),
        rating decimal,
        editorID varchar(255),
        isEdited bit,
        deleteStatus bit NOT NULL
    );

GO
    CREATE TABLE Orders(
        id int NOT NULL PRIMARY KEY IDENTITY(1, 1),
        memberID varchar(255) NOT NULL FOREIGN KEY REFERENCES Users(id),
        orderDate date,
        lendMethod bit,
        activeStatus int NOT NULL
    );

GO
    CREATE TABLE OrderItems(
        id int NOT NULL PRIMARY KEY IDENTITY(1, 1),
        orderID int NOT NULL FOREIGN KEY REFERENCES Orders(id),
        returnOrderID int FOREIGN KEY REFERENCES Orders(id),
        bookID varchar(255) NOT NULL FOREIGN KEY REFERENCES Books(id),
        lendStatus int NOT NULL,
        returnDeadline date,
        lendDate date,
        returnDate date,
        penaltyAmount decimal,
        penaltyStatus bit
    );

GO
    CREATE TABLE RenewalRequests(
        id varchar(255) NOT NULL PRIMARY KEY,
        itemID int NOT NULL FOREIGN KEY REFERENCES OrderItems(id),
        librarianID varchar(255) FOREIGN KEY REFERENCES Users(id),
        reason varchar(MAX),
        requestedExtendDate date,
        approvalStatus bit
    );

GO
    CREATE TABLE DirectOrder(
        orderID int NOT NULL FOREIGN KEY REFERENCES Orders(id),
        librarianID varchar(255) FOREIGN KEY REFERENCES Users(id),
        scheduledTime datetime,
    );

GO
    CREATE TABLE DeliveryOrder(
        orderID int NOT NULL FOREIGN KEY REFERENCES Orders(id),
        managerID varchar(255) FOREIGN KEY REFERENCES Users(id),
        deliverer varchar(255),
        scheduledDeliveryTime date,
        phoneNumber varchar(10),
        deliveryAddress1 nvarchar(MAX),
        deliveryAddress2 nvarchar(MAX),
        city nvarchar(255),
        district nvarchar(255),
        ward nvarchar(255),
        trackingCode varchar(255),
        isReturnOrder bit NOT NULL
    );

GO
    CREATE TABLE Feedback (
        id int NOT NULL PRIMARY KEY IDENTITY(1, 1),
        fullName NVARCHAR(MAX),
        email VARCHAR(255),
        phone VARCHAR(10),
        feedbackType INT,
        attachment VARCHAR(MAX),
        feedbackMessage nvarchar(MAX)
    );

GO
    CREATE TABLE Announcement(
        id INT NOT NULL PRIMARY KEY IDENTITY(1, 1),
        creationDate DATE,
        announcementText NVARCHAR(MAX),
        writerId VARCHAR(255) FOREIGN KEY REFERENCES Users(id),
        returnDeadline DATE
    );

GO
INSERT INTO
    Subjects (id, name, semester_no, deleteStatus)
values
    (1, 'Art', 1, 0);

GO
INSERT INTO
    Subjects (id, name, semester_no, deleteStatus)
values
    (2, 'History', 1, 0);

GO
INSERT INTO
    Subjects (id, name, semester_no, deleteStatus)
values
    (3, 'Language', 1, 0);

GO
INSERT INTO
    Subjects (id, name, semester_no, deleteStatus)
values
    (4, 'Music', 1, 0);

GO
INSERT INTO
    Subjects (id, name, semester_no, deleteStatus)
values
    (5, 'Math', 2, 0);

GO
INSERT INTO
    Subjects (id, name, semester_no, deleteStatus)
values
    (6, 'Writing', 2, 0);

GO
INSERT INTO
    Authors (id, name, bio, profilePicturePath, deleteStatus)
values
    (
        1,
        'Fitzgerald',
        'Maecenas ut massa quis augue luctus tincidunt. Nulla mollis molestie lorem. Quisque ut erat. Curabitur gravida nisi at nibh.',
        'C:\a.png',
        0
    );

GO
INSERT INTO
    Authors (id, name, bio, profilePicturePath, deleteStatus)
values
    (
        2,
        'Esta',
        'Donec ut dolor. Morbi vel lectus IN quam fringilla rhoncus. Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis.',
        'C:\a.png',
        0
    );

GO
INSERT INTO
    Authors (id, name, bio, profilePicturePath, deleteStatus)
values
    (
        3,
        'Kathie',
        'Nulla ac enim. IN tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem. Duis aliquam convallis nunc. Proin at turpis a pede posuere nonummy. Integer non velit. Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue.',
        'C:\a.png',
        0
    );

GO
INSERT INTO
    Authors (id, name, bio, profilePicturePath, deleteStatus)
values
    (4, 'Bert', 'Fusce consequat.', 'C:\a.png', 0);

GO
INSERT INTO
    Authors (id, name, bio, profilePicturePath, deleteStatus)
values
    (
        5,
        'Olympie',
        'Duis consequat dui nec nisi volutpat eleifend. Donec ut dolor. Morbi vel lectus IN quam fringilla rhoncus. Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis. Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci. Mauris lacinia sapien quis libero. Nullam sit amet turpis elementum ligula vehicula consequat. Morbi a ipsum. Integer a nibh. IN quis justo.',
        'C:\a.png',
        0
    );

GO
INSERT INTO
    Authors (id, name, bio, profilePicturePath, deleteStatus)
values
    (
        6,
        'Carter',
        'Integer ac leo. Pellentesque ultrices mattis odio. Donec vitae nisi. Nam ultrices, libero non mattis pulvinar, nulla pede ullamcorper augue, a suscipit nulla elit ac nulla. Sed vel enim sit amet nunc viverra dapibus. Nulla suscipit ligula IN lacus. Curabitur at ipsum ac tellus semper interdum.',
        'C:\a.png',
        0
    );

GO
INSERT INTO
    Books (
        id,
        title,
        subjectID,
        publisher,
        publishDate,
        description,
        price,
        quantity,
        deleteStatus,
        lastLentDate,
        avgRating,
        ISBN_tenDigits,
        ISBN_thirteenDigits,
        coverPicturePath
    )
values
    (
        1,
        'Software Engineering at Google: Lessons Learned',
        3,
        'Wordpedia',
        '2020/09/17',
        'Today, software engineers need to know not only how to program effectively but also how to develop proper engineering practices to make their codebase sustainable AND healthy. This book emphasizes this difference BETWEEN programming AND software engineering. How can software engineers manage a living codebase that evolves AND responds to changing requirements AND demands over the length of its life? Based
ON their experience at Google, software engineers Titus Winters AND Hyrum Wright, along WITH technical writer Tom Manshreck, present a candid AND insightful look at how some of the world’s leading practitioners construct AND maintain software. This book covers Google’s unique engineering culture, processes, AND tools AND how these aspects contribute to the effectiveness of an engineering organization.',
        16.55,
        20,
        0,
        '2020/12/09',
        4.1,
        '439024713',
        '7749623681234',
        'C:\b.png'
    );

GO
INSERT INTO
    Books (
        id,
        title,
        subjectID,
        publisher,
        publishDate,
        description,
        price,
        quantity,
        deleteStatus,
        lastLentDate,
        avgRating,
        ISBN_tenDigits,
        ISBN_thirteenDigits,
        coverPicturePath
    )
values
    (
        2,
        'Fundamentals of Software Architecture',
        2,
        'Divape',
        '2020/09/04',
        'Salary surveys worldwide regularly place software architect IN the top 10 best jobs, yet no real guide exists to help developers become architects. Until now. This book provides the first comprehensive overview of software architecture’s many aspects. Aspiring AND existing architects alike will examine architectural characteristics, architectural patterns, component determination, diagramming AND presenting architecture, evolutionary architecture, AND many other topics.',
        20.28,
        13,
        0,
        '2020/06/16',
        1.0,
        '581621217',
        '5157100231234',
        'C:\b.png'
    );

INSERT INTO
    Books (
        id,
        title,
        subjectID,
        publisher,
        publishDate,
        description,
        price,
        quantity,
        deleteStatus,
        lastLentDate,
        avgRating,
        ISBN_tenDigits,
        ISBN_thirteenDigits,
        coverPicturePath
    )
values
    (
        3,
        'Python Crash Course, 2nd Edition',
        6,
        'Zoombeat',
        '2020/10/27',
        'Python Crash Course is the world''s best-selling guide to the Python programming language. This fast-paced, thorough introduction to programming WITH Python will have you writing programs, solving problems, AND making things that work IN no time.',
        20.4,
        1,
        0,
        '2020/06/10',
        0.1,
        '421605316',
        '3568879141234',
        'C:\b.png'
    );

INSERT INTO
    Books (
        id,
        title,
        subjectID,
        publisher,
        publishDate,
        description,
        price,
        quantity,
        deleteStatus,
        lastLentDate,
        avgRating,
        ISBN_tenDigits,
        ISBN_thirteenDigits,
        coverPicturePath
    )
values
    (
        4,
        'Blockchain Bubble or Revolution',
        1,
        'Janyx',
        '2021/01/24',
        'Authored by Silicon Valley leaders
FROM Google, Microsoft, AND Facebook, Bubble or Revolution cuts through the hype to offer a balanced, comprehensive, AND accessible analysis of blockchains AND cryptocurrencies.',
        23.2,
        1,
        0,
        '2020/06/18',
        3.7,
        '404663114',
        '3665328051234',
        'C:\b.png'
    );

INSERT INTO
    Books (
        id,
        title,
        subjectID,
        publisher,
        publishDate,
        description,
        price,
        quantity,
        deleteStatus,
        lastLentDate,
        avgRating,
        ISBN_tenDigits,
        ISBN_thirteenDigits,
        coverPicturePath
    )
values
    (
        5,
        'Expert Python Programming: Master Python',
        1,
        'Pixoboo',
        '2021/04/30',
        'The book will start by taking you through the new features IN Python 3.7. You''ll THEN learn the advanced components of Python syntax, IN addition to understanding how to apply concepts of various programming paradigms, including object-oriented programming, functional programming, AND event-driven programming. This book will also guide you through learning the best naming practices, writing your own distributable Python packages, AND getting up to speed WITH automated ways of deploying your software
ON remote servers. You''ll discover how to

CREATE useful Python extensions WITH C, C++, Cython, AND CFFI. Furthermore, studying about code management tools, writing clear documentation, AND exploring test-driven development will help you write clean code.',
        32.37,
        15,
        0,
        '2020/11/13',
        2.7,
        '035966056',
        '4426072141234',
        'C:\b.png'
    );

INSERT INTO
    Books (
        id,
        title,
        subjectID,
        publisher,
        publishDate,
        description,
        price,
        quantity,
        deleteStatus,
        lastLentDate,
        avgRating,
        ISBN_tenDigits,
        ISBN_thirteenDigits,
        coverPicturePath
    )
values
    (
        6,
        'ASP.NET Core 5 AND Angular: Full-stack Web development',
        1,
        'Tagchat',
        '2020/08/01',
        'Learning full-stack development calls for knowledge of both front-end AND back-end web development. ASP.NET Core 5 AND Angular, Fourth Edition will enhance your ability to create, debug, AND deploy efficient web applications USING ASP.NET Core AND Angular. This revised edition includes coverage of the Angular routing module, expanded discussion
ON the Angular CLI, AND detailed instructions for deploying apps
ON Azure, AS well AS both Windows AND Linux.',
        18.3,
        7,
        0,
        '2020/11/15',
        2.8,
        '901484984',
        '8576192651234',
        'C:\b.png'
    );

INSERT INTO
    Books (
        id,
        title,
        subjectID,
        publisher,
        publishDate,
        description,
        price,
        quantity,
        deleteStatus,
        lastLentDate,
        avgRating,
        ISBN_tenDigits,
        ISBN_thirteenDigits,
        coverPicturePath
    )
values
    (
        7,
        'The Road to React: Your Journey to Master',
        3,
        'Rhynyx',
        '2021/03/05',
        'In "The Road to React" you will learn about all the fundamentals of React.js WITH Hooks while building a full-blown React application step by step. While you

CREATE the React application, every chapter will introduce you to a new React key feature. However, there is more than only the fundamentals: The book dives into related topics (e.g. React WITH TypeScript, Testing, Performance Optimizations) AND advanced feature implementations like client- AND server-side searching. At the end of the book, you will have a fully working deployed React application.Is it up to date?Programming books are usually outdated soon after their release, but since this book is self- published, I can update it AS needed whenever a new version of something related to this book gets released.I am a beginner. Is this book for me?Yes. The book starts
FROM zero AND takes you through the learning experience step by step. Every chapter builds up
ON the learnings
FROM the previous chapter. IN addition, at the end of every chapter, exercises fortify your lessons learned. If you got stuck IN a chapter, you will always find a reference URL to the status quo of the actual code.',
        22.19,
        15,
        0,
        '2020/11/24',
        4.0,
        '276332597',
        '0975709511234',
        'C:\b.png'
    );

INSERT INTO
    Books (
        id,
        title,
        subjectID,
        publisher,
        publishDate,
        description,
        price,
        quantity,
        deleteStatus,
        lastLentDate,
        avgRating,
        ISBN_tenDigits,
        ISBN_thirteenDigits,
        coverPicturePath
    )
values
    (
        8,
        'JavaScript AND JQuery: Interactive Front-End Web Development',
        5,
        'Innotype',
        '2020/10/10',
        'By the end of the book, not only will you be able to use the thousands of scripts, JavaScript APIs, AND jQuery plugins that are freely available
ON the web, AND be able to customize them - you will also be able to

CREATE your own scripts
FROM scratch.',
        46.01,
        11,
        0,
        '2020/12/27',
        0.4,
        '759833713',
        '801023987-9',
        'C:\b.png'
    );

INSERT INTO
    Books (
        id,
        title,
        subjectID,
        publisher,
        publishDate,
        description,
        price,
        quantity,
        deleteStatus,
        lastLentDate,
        avgRating,
        ISBN_tenDigits,
        ISBN_thirteenDigits,
        coverPicturePath
    )
values
    (
        9,
        'PHP & MySQL: Server-side Web Development',
        1,
        'Kwimbee',
        '2021/05/13',
        'This full-color book is packed WITH inspiring code examples, infographics AND photography that not only teach you the PHP language AND how to work WITH databases, but also show you how to build new applications
FROM scratch.',
        45.39,
        6,
        0,
        '2020/09/11',
        1.3,
        '486202312',
        '4376406501234',
        'C:\b.png'
    );

INSERT INTO
    Books (
        id,
        title,
        subjectID,
        publisher,
        publishDate,
        description,
        price,
        quantity,
        deleteStatus,
        lastLentDate,
        avgRating,
        ISBN_tenDigits,
        ISBN_thirteenDigits,
        coverPicturePath
    )
values
    (
        10,
        'Mobile Development WITH .NET',
        2,
        'Kwimbee',
        '2020/10/29',
        'Quisque ligula lorem, dictum vitae ante in, pretium finibus ante. Sed sem mauris, convallis eget lectus non, vestibulum pharetra felis. Vivamus a metus neque. Duis sed est lorem. Duis imperdiet eu nisl vulputate scelerisque. Donec ut ligula neque. Proin nunc augue, pharetra quis vulputate quis, finibus a orci. Sed rutrum dapibus mattis. ',
        28.24,
        6,
        0,
        '2021/04/30',
        4.0,
        '076228356',
        '5154947231234',
        'C:\b.png'
    );

INSERT INTO
    Books (
        id,
        title,
        subjectID,
        publisher,
        publishDate,
        description,
        price,
        quantity,
        deleteStatus,
        lastLentDate,
        avgRating,
        ISBN_tenDigits,
        ISBN_thirteenDigits,
        coverPicturePath
    )
values
    (
        11,
        'mattis nibh ligula nec sem duis aliquam convallis',
        1,
        'Edgeclub',
        '2020/09/24',
        'Maecenas IN pharetra nisl, nec laoreet purus. IN posuere mi nunc, IN porttitor odio maximus vel. Proin semper porttitor odio. Aliquam non nibh eu urna semper tincidunt. Morbi ac cursus eros. Praesent viverra non lorem nec malesuada. Sed commodo id nibh lobortis sodales. Maecenas rhoncus iaculis nisi, ac placerat diam.',
        26.67,
        18,
        0,
        '2020/12/24',
        2.7,
        '278713956',
        '1000736211234',
        'C:\b.png'
    );

INSERT INTO
    Books (
        id,
        title,
        subjectID,
        publisher,
        publishDate,
        description,
        price,
        quantity,
        deleteStatus,
        lastLentDate,
        avgRating,
        ISBN_tenDigits,
        ISBN_thirteenDigits,
        coverPicturePath
    )
values
    (
        12,
        'porta volutpat erat quisque erat eros viverra eget congue',
        5,
        'Trilith',
        '2021/04/03',
        'Quisque ligula lorem, dictum vitae ante in, pretium finibus ante. Sed sem mauris, convallis eget lectus non, vestibulum pharetra felis. Vivamus a metus neque. Duis sed est lorem. Duis imperdiet eu nisl vulputate scelerisque. Donec ut ligula neque. Proin nunc augue, pharetra quis vulputate quis, finibus a orci. Sed rutrum dapibus mattis. ',
        27.87,
        7,
        0,
        '2020/06/23',
        3.4,
        '199263196',
        '7785898321234',
        'C:\b.png'
    );

INSERT INTO
    Books (
        id,
        title,
        subjectID,
        publisher,
        publishDate,
        description,
        price,
        quantity,
        deleteStatus,
        lastLentDate,
        avgRating,
        ISBN_tenDigits,
        ISBN_thirteenDigits,
        coverPicturePath
    )
values
    (
        13,
        'dapibus augue vel accumsan tellus',
        6,
        'Youfeed',
        '2021/05/15',
        'Maecenas IN pharetra nisl, nec laoreet purus. IN posuere mi nunc, IN porttitor odio maximus vel. Proin semper porttitor odio. Aliquam non nibh eu urna semper tincidunt. Morbi ac cursus eros. Praesent viverra non lorem nec malesuada. Sed commodo id nibh lobortis sodales. Maecenas rhoncus iaculis nisi, ac placerat diam.',
        16.8,
        12,
        0,
        '2020/08/13',
        1.9,
        '416309651',
        '9771421951234',
        'C:\b.png'
    );

INSERT INTO
    Books (
        id,
        title,
        subjectID,
        publisher,
        publishDate,
        description,
        price,
        quantity,
        deleteStatus,
        lastLentDate,
        avgRating,
        ISBN_tenDigits,
        ISBN_thirteenDigits,
        coverPicturePath
    )
values
    (
        14,
        'odio curabitur convallist',
        5,
        'Plajo',
        '2020/11/09',
        'Quisque ligula lorem, dictum vitae ante in, pretium finibus ante. Sed sem mauris, convallis eget lectus non, vestibulum pharetra felis. Vivamus a metus neque. Duis sed est lorem. Duis imperdiet eu nisl vulputate scelerisque. Donec ut ligula neque. Proin nunc augue, pharetra quis vulputate quis, finibus a orci. Sed rutrum dapibus mattis. ',
        31.83,
        10,
        0,
        '2020/12/17',
        1.7,
        '382417431',
        '4110274001234',
        'C:\b.png'
    );

INSERT INTO
    Books (
        id,
        title,
        subjectID,
        publisher,
        publishDate,
        description,
        price,
        quantity,
        deleteStatus,
        lastLentDate,
        avgRating,
        ISBN_tenDigits,
        ISBN_thirteenDigits,
        coverPicturePath
    )
values
    (
        15,
        'mauris morbi non lectus aliquam sit amet',
        6,
        'Vipe',
        '2021/04/29',
        'Maecenas IN pharetra nisl, nec laoreet purus. IN posuere mi nunc, IN porttitor odio maximus vel. Proin semper porttitor odio. Aliquam non nibh eu urna semper tincidunt. Morbi ac cursus eros. Praesent viverra non lorem nec malesuada. Sed commodo id nibh lobortis sodales. Maecenas rhoncus iaculis nisi, ac placerat diam.',
        35.47,
        13,
        0,
        '2020/09/13',
        2.0,
        '507392395',
        '2147368091234',
        'C:\b.png'
    );

INSERT INTO
    Books (
        id,
        title,
        subjectID,
        publisher,
        publishDate,
        description,
        price,
        quantity,
        deleteStatus,
        lastLentDate,
        avgRating,
        ISBN_tenDigits,
        ISBN_thirteenDigits,
        coverPicturePath
    )
values
    (
        16,
        'ante vivamus tortor',
        5,
        'Kamba',
        '2021/03/10',
        'Quisque ligula lorem, dictum vitae ante in, pretium finibus ante. Sed sem mauris, convallis eget lectus non, vestibulum pharetra felis. Vivamus a metus neque. Duis sed est lorem. Duis imperdiet eu nisl vulputate scelerisque. Donec ut ligula neque. Proin nunc augue, pharetra quis vulputate quis, finibus a orci. Sed rutrum dapibus mattis. ',
        30.62,
        7,
        0,
        '2020/08/21',
        0.6,
        '239724965',
        '2125483091234',
        'C:\b.png'
    );

INSERT INTO
    Books (
        id,
        title,
        subjectID,
        publisher,
        publishDate,
        description,
        price,
        quantity,
        deleteStatus,
        lastLentDate,
        avgRating,
        ISBN_tenDigits,
        ISBN_thirteenDigits,
        coverPicturePath
    )
values
    (
        17,
        'etiam vel augue vestibulum rutrum',
        4,
        'Pixonyx',
        '2020/09/05',
        'Maecenas IN pharetra nisl, nec laoreet purus. IN posuere mi nunc, IN porttitor odio maximus vel. Proin semper porttitor odio. Aliquam non nibh eu urna semper tincidunt. Morbi ac cursus eros. Praesent viverra non lorem nec malesuada. Sed commodo id nibh lobortis sodales. Maecenas rhoncus iaculis nisi, ac placerat diam.',
        15.66,
        11,
        0,
        '2021/01/16',
        1.4,
        '204390397',
        '8816519391234',
        'C:\b.png'
    );

INSERT INTO
    Books (
        id,
        title,
        subjectID,
        publisher,
        publishDate,
        description,
        price,
        quantity,
        deleteStatus,
        lastLentDate,
        avgRating,
        ISBN_tenDigits,
        ISBN_thirteenDigits,
        coverPicturePath
    )
values
    (
        18,
        'in purus eu magna vulputate',
        5,
        'Zoomlounge',
        '2021/02/01',
        'Quisque ligula lorem, dictum vitae ante in, pretium finibus ante. Sed sem mauris, convallis eget lectus non, vestibulum pharetra felis. Vivamus a metus neque. Duis sed est lorem. Duis imperdiet eu nisl vulputate scelerisque. Donec ut ligula neque. Proin nunc augue, pharetra quis vulputate quis, finibus a orci. Sed rutrum dapibus mattis. ',
        20.04,
        6,
        0,
        '2021/03/26',
        0.2,
        '189775883',
        '472913602-0',
        'C:\b.png'
    );

INSERT INTO
    Books (
        id,
        title,
        subjectID,
        publisher,
        publishDate,
        description,
        price,
        quantity,
        deleteStatus,
        lastLentDate,
        avgRating,
        ISBN_tenDigits,
        ISBN_thirteenDigits,
        coverPicturePath
    )
values
    (
        19,
        'blandit nam nulla',
        6,
        'Skiba',
        '2021/04/20',
        'Maecenas IN pharetra nisl, nec laoreet purus. IN posuere mi nunc, IN porttitor odio maximus vel. Proin semper porttitor odio. Aliquam non nibh eu urna semper tincidunt. Morbi ac cursus eros. Praesent viverra non lorem nec malesuada. Sed commodo id nibh lobortis sodales. Maecenas rhoncus iaculis nisi, ac placerat diam.',
        48.62,
        4,
        0,
        '2021/04/19',
        2.5,
        '237484306',
        '0773323141234',
        'C:\b.png'
    );

INSERT INTO
    Books (
        id,
        title,
        subjectID,
        publisher,
        publishDate,
        description,
        price,
        quantity,
        deleteStatus,
        lastLentDate,
        avgRating,
        ISBN_tenDigits,
        ISBN_thirteenDigits,
        coverPicturePath
    )
values
    (
        20,
        'diam id ornare imperdiet sapien urna pretium',
        3,
        'Kwideo',
        '2020/11/08',
        'Quisque ligula lorem, dictum vitae ante in, pretium finibus ante. Sed sem mauris, convallis eget lectus non, vestibulum pharetra felis. Vivamus a metus neque. Duis sed est lorem. Duis imperdiet eu nisl vulputate scelerisque. Donec ut ligula neque. Proin nunc augue, pharetra quis vulputate quis, finibus a orci. Sed rutrum dapibus mattis. ',
        13.32,
        6,
        0,
        '2020/08/11',
        0.3,
        '498193421',
        '2973443681234',
        'C:\b.png'
    );

INSERT INTO
    Roles (id, name)
values
    ('1', 'ADM');

INSERT INTO
    Roles (id, name)
values
    ('2', 'MNG');

INSERT INTO
    Roles (id, name)
values
    ('3', 'LIB');

INSERT INTO
    Roles (id, name)
values
    ('4', 'MEM');

INSERT INTO
    Users (
        id,
        name,
        roleID,
        semester_no,
        password,
        email,
        phoneNumber,
        profilePicturePath,
        activeStatus
    )
values
    (
        'SE000001',
        'Dat',
        '4',
        1,
        '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4',
        'dat@fpt.edu.vn',
        '123456789',
        'images/default-user-icon.png',
        0
    );

INSERT INTO
    Users (
        id,
        name,
        roleID,
        semester_no,
        password,
        email,
        phoneNumber,
        profilePicturePath,
        activeStatus
    )
values
    (
        'LE000001',
        'Nguyen',
        '4',
        1,
        '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4',
        'nguyen@fpt.edu.vn',
        '123456789',
        'images/default-user-icon.png',
        0
    );

INSERT INTO
    Users (
        id,
        name,
        roleID,
        semester_no,
        password,
        email,
        phoneNumber,
        profilePicturePath,
        activeStatus
    )
values
    (
        'LIB00001',
        'Dung',
        '3',
        1,
        '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4',
        'dung@fpt.edu.vn',
        '123456789',
        'images/default-user-icon.png',
        0
    );

INSERT INTO
    Users (
        id,
        name,
        roleID,
        semester_no,
        password,
        email,
        phoneNumber,
        profilePicturePath,
        activeStatus
    )
values
    (
        'MNG00001',
        'Phuc',
        '2',
        1,
        '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4',
        'phuc@fpt.edu.vn',
        '123456789',
        'images/default-user-icon.png',
        0
    );

INSERT INTO
    Users (
        id,
        name,
        roleID,
        semester_no,
        email,
        phoneNumber,
        activeStatus
    )
values
    (
        'SE151274',
        'Nguyen Gia Nguyen',
        '4',
        5,
        'nguyenngse151274@fpt.edu.vn',
        '0901666918',
        0
    );

INSERT INTO
    Users (
        id,
        name,
        roleID,
        semester_no,
        email,
        phoneNumber,
        activeStatus
    )
values
    (
        'SE151271',
        'Nguyen Dung',
        '3',
        5,
        'dungnse151271@fpt.edu.vn',
        '1234567890',
        0
    );

INSERT INTO
    Users (
        id,
        name,
        roleID,
        semester_no,
        email,
        phoneNumber,
        activeStatus
    )
values
    (
        'SE151261',
        'Nguyen Thien Phuc',
        '2',
        5,
        'phucntse151261@fpt.edu.vn',
        '1234567890',
        0
    );

INSERT INTO
    Users (
        id,
        name,
        roleID,
        semester_no,
        email,
        phoneNumber,
        activeStatus
    )
values
    (
        'SE151249',
        'Ho Tien Dat',
        '4',
        5,
        'dathtse151249@fpt.edu.vn',
        '1234567890',
        0
    );

INSERT INTO
    ImportLogs (bookID, managerID, dateTaken, supplier, quantity)
values
    (
        1,
        'LIB00001',
        '2020/06/06',
        'Xuong IN Thien Phuc',
        1
    );

INSERT INTO
    ImportLogs (bookID, managerID, dateTaken, supplier, quantity)
values
    (
        2,
        'LIB00001',
        '2021/06/07',
        'Xuong IN Nguyen Dung',
        1
    );

INSERT INTO
    Comments (
        memberID,
        bookID,
        textComment,
        rating,
        isEdited,
        deleteStatus
    )
values
    (
        'LIB00001',
        1,
        'Not bad, could be more informative.',
        0,
        0,
        0
    );

INSERT INTO
    Comments (
        memberID,
        bookID,
        textComment,
        rating,
        isEdited,
        deleteStatus
    )
values
    (
        'LIB00001',
        2,
        'Great book, much insightful.',
        5,
        0,
        0
    );

INSERT INTO
    Comments (
        memberID,
        bookID,
        textComment,
        rating,
        isEdited,
        deleteStatus
    )
values
    (
        'SE000001',
        1,
        'Fine work, not much application though.',
        3.5,
        0,
        0
    );

INSERT INTO
    AuthorBookMaps (authorID, bookID)
VALUES
    (N'1', N'1');

INSERT INTO
    AuthorBookMaps (authorID, bookID)
VALUES
    (N'1', N'2');

INSERT INTO
    AuthorBookMaps (authorID, bookID)
VALUES
    (N'1', N'3');

INSERT INTO
    AuthorBookMaps (authorID, bookID)
VALUES
    (N'2', N'4');

INSERT INTO
    AuthorBookMaps (authorID, bookID)
VALUES
    (N'2', N'5');

INSERT INTO
    AuthorBookMaps (authorID, bookID)
VALUES
    (N'2', N'6');

INSERT INTO
    AuthorBookMaps (authorID, bookID)
VALUES
    (N'3', N'7');

INSERT INTO
    AuthorBookMaps (authorID, bookID)
VALUES
    (N'3', N'8');

INSERT INTO
    AuthorBookMaps (authorID, bookID)
VALUES
    (N'3', N'9');

INSERT INTO
    AuthorBookMaps (authorID, bookID)
VALUES
    (N'4', N'10');

INSERT INTO
    AuthorBookMaps (authorID, bookID)
VALUES
    (N'4', N'11');

INSERT INTO
    AuthorBookMaps (authorID, bookID)
VALUES
    (N'4', N'12');

INSERT INTO
    AuthorBookMaps (authorID, bookID)
VALUES
    (N'5', N'13');

INSERT INTO
    AuthorBookMaps (authorID, bookID)
VALUES
    (N'5', N'14');

INSERT INTO
    AuthorBookMaps (authorID, bookID)
VALUES
    (N'5', N'15');

INSERT INTO
    AuthorBookMaps (authorID, bookID)
VALUES
    (N'6', N'16');

INSERT INTO
    AuthorBookMaps (authorID, bookID)
VALUES
    (N'6', N'17');

INSERT INTO
    AuthorBookMaps (authorID, bookID)
VALUES
    (N'6', N'18');

INSERT INTO
    AuthorBookMaps (authorID, bookID)
VALUES
    (N'1', N'19');

INSERT INTO
    AuthorBookMaps (authorID, bookID)
VALUES
    (N'2', N'20');

INSERT INTO
    AuthorBookMaps (authorID, bookID)
VALUES
    (N'2', N'1');

INSERT INTO
    AuthorBookMaps (authorID, bookID)
VALUES
    (N'3', N'1');

INSERT INTO
    AuthorBookMaps (authorID, bookID)
VALUES
    (N'4', N'1');

INSERT INTO
    AuthorBookMaps (authorID, bookID)
VALUES
    (N'4', N'2');