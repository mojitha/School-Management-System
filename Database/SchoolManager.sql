--CREATION OF TABLES--

--1)  User
CREATE TABLE User (
    userID VARCHAR(10),
    name VARCHAR(30),
    userName VARCHAR(30) DEFAULT NULL,
    password VARCHAR(30) DEFAULT '1111',
    birthDate DATE,
    registeredDate DATE DEFAULT '2000-01-01',
    email VARCHAR(50),
    phone VARCHAR(10),
    address VARCHAR(50),
    bio VARCHAR(200) DEFAULT NULL,
    img LONGBLOB DEFAULT NULL,
    status INT DEFAULT '1',

    PRIMARY KEY(userID)
);

--2)  AcademicStaff
CREATE TABLE AcademicStaff (
    userID VARCHAR(10),
    designation VARCHAR(30) DEFAULT NULL,
    qualification VARCHAR(200) DEFAULT NULL,
    availability INT DEFAULT 4,

    PRIMARY KEY(userID),
    
    FOREIGN KEY(userID)
    REFERENCES User(userID)
    ON UPDATE CASCADE
);

--3)  NonAcademicStaff
CREATE TABLE NonAcademicStaff (
    userID VARCHAR(10),
    designation VARCHAR(30) DEFAULT NULL,
    qualification VARCHAR(200) DEFAULT NULL,

    PRIMARY KEY(userID),

    FOREIGN KEY(userID)
    REFERENCES User(userID)
    ON UPDATE CASCADE
);

--4)  Student
CREATE TABLE Student (
    userID VARCHAR(10),
    grade VARCHAR(8),
    class CHAR(1),
    religion VARCHAR(30),

    PRIMARY KEY(userID),

    FOREIGN KEY(userID)
    REFERENCES User(userID)
    ON UPDATE CASCADE
);

--5)  Item
CREATE TABLE Item (
    resourceID VARCHAR(10),
    description VARCHAR(50) DEFAULT NULL,
    category VARCHAR(30) DEFAULT NULL,
    supplyDate DATE,
    updatedOn DATE,
    status VARCHAR(20),
    quantity VARCHAR(15),
    unit VARCHAR(15),
    cost DOUBLE,
    expireDate VARCHAR(14) DEFAULT "Not Specified",

    PRIMARY KEY(resourceID)
);

--6)  Book
CREATE TABLE Book (
    resourceID VARCHAR(10),    
    title VARCHAR(50) DEFAULT NULL,
    language VARCHAR(30),
    category VARCHAR(50),
    author VARCHAR(100),
    price DOUBLE,
    updatedOn DATE,
    copies INT DEFAULT 0,

    PRIMARY KEY(resourceID)
);

--7)  BookHasCopies        
CREATE TABLE BookHasCopies (
    resourceID VARCHAR(10),
    copy INT,
    ISBN VARCHAR(30) DEFAULT NULL,
    supplyDate DATE,
    status VARCHAR(20),

    PRIMARY KEY(resourceID,copy),

    FOREIGN KEY(resourceID)
    REFERENCES Book(resourceID)
    ON UPDATE CASCADE
);

--8)  Subject
CREATE TABLE Subject (
    subjectID VARCHAR(10),
    subject VARCHAR(50),
    grade VARCHAR(8),

    PRIMARY KEY(subjectID)
);

--9)  StudentHasSubjects
CREATE TABLE StudentHasSubjects (
    userID VARCHAR(10),
    subjectID VARCHAR(10),

    PRIMARY KEY(userID,subjectID),

    FOREIGN KEY(userID)
    REFERENCES Student(userID)
    ON UPDATE CASCADE,

    FOREIGN KEY(subjectID)
    REFERENCES Subject(subjectID)
    ON UPDATE CASCADE
);

--10)  AcademicTeachSubjects
CREATE TABLE AcademicTeachSubjects (
    userID VARCHAR(10),
    subjectID VARCHAR(10),
    assignedDate DATE,

    PRIMARY KEY(userID,subjectID),

    FOREIGN KEY(userID)
    REFERENCES AcademicStaff(userID)
    ON UPDATE CASCADE,

    FOREIGN KEY(subjectID)
    REFERENCES Subject(subjectID)
    ON UPDATE CASCADE
);

--11)  Exam
CREATE TABLE Exam (
    examID VARCHAR(10),
    examDate DATE,
    grade VARCHAR(8),
    subjectID VARCHAR(10),
    startTime TIME,
    endTime TIME,
    year YEAR,
    term INT,
    status VARCHAR(20) DEFAULT 'Ahead',

    PRIMARY KEY(examID),
    
    FOREIGN KEY(subjectID)
    REFERENCES Subject(subjectID)
    ON UPDATE CASCADE
);
  
--12)  StudentFaceExams
CREATE TABLE StudentFaceExams (
    userID VARCHAR(10),
    subjectID VARCHAR(10),
    examID VARCHAR(10),
    result INT,

    PRIMARY KEY(userID,subjectID,examID),

    FOREIGN KEY(userID,subjectID)
    REFERENCES StudentHasSubjects(userID,subjectID)
    ON UPDATE CASCADE,

    FOREIGN KEY(examID)
    REFERENCES Exam(examID)
    ON UPDATE CASCADE
);

--13)  StudentYear
CREATE TABLE StudentYear (
    userID VARCHAR(10),
    grade VARCHAR(8),
    year INT,

    PRIMARY KEY(userID,grade,year),

    FOREIGN KEY(userID)
    REFERENCES User(userID)
    ON UPDATE CASCADE
);

--14) ItemCategories
CREATE TABLE ItemCategories (
    categoryID VARCHAR(10),
    category VARCHAR(30),

    PRIMARY KEY(categoryID)
);

--15) ItemUnits
CREATE TABLE ItemUnits (
    unitID VARCHAR(10),
    unit VARCHAR(30),

    PRIMARY KEY(unitID)
);

--16) BookCategories
CREATE TABLE BookCategories (
    bcategoryID VARCHAR(10),
    bcategory VARCHAR(100),

    PRIMARY KEY(bcategoryID)
);

--17) Attendance
CREATE TABLE Attendance (
    userID VARCHAR(10),
    date DATE,
    presentAbsent INT,

    PRIMARY KEY(userID,date),

    FOREIGN KEY(userID)
    REFERENCES User(userID)
    ON UPDATE CASCADE
);

--ALTER COMMANDS--
--Item status should be beside the given
ALTER TABLE Item
ADD CHECK(status IN ('NEW','Using','Repairable','Replacable','Expired','Out of Stock'));

--Exam status should be beside the given
ALTER TABLE Exam
ADD CHECK(status IN ('Ahead','Marked','Finallized','Canceled'));

--Attendance's presentAbsent should be in 1 and 0
ALTER TABLE Attendance
ADD CHECK(presentAbsent IN (0,1));

--TRIGGERS
--*CREATES A ENTRY IN StudentYear with inserted or updated student*
DELIMITER //

CREATE TRIGGER recordStuYearInsert AFTER INSERT 
ON Student 
FOR EACH ROW
BEGIN
    DECLARE yr INT;
    
    SET yr = YEAR(CURDATE());
    
    INSERT INTO StudentYear
    VALUES(NEW.userID,NEW.grade,yr);

END //

--DROP TRIGGER recordStuYearInsert;
----------------------------------------------------------

--INSERT Commands--
--User
INSERT INTO User(`userID`,`name`,`userName`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000001','Sandeep Fernando','sandeep','1987-10-16',CURDATE(),'chan@we.lk','0812400234','31-w,Alawala Road,Madawala');
INSERT INTO User(`userID`,`name`,`userName`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000002','Udith Premarathne','udith','1990-11-26',CURDATE(),'Udith123@ew.kl','0735430234','24-r,Chandra Road,Katukele');
INSERT INTO User(`userID`,`name`,`userName`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000003','Aruna Jayasooriya','aruna','1989-02-19',CURDATE(),'Aruna123@gmail.lk','0361950362','86-w,George Silva Road,Katugasthota');
INSERT INTO User(`userID`,`name`,`userName`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000004','Tharindu Welivita','tharindu','1986-03-29',CURDATE(),'mail34@Welivita.lk','0752514653','100-B,Madawala Road,Katugasthota');
INSERT INTO User(`userID`,`name`,`userName`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000005','Neranjan Dissanayaka','neranjan','1982-04-05',CURDATE(),'neranjan131@we.lk','0412420214','102-B,Madawala Road,Waththegama');
INSERT INTO User(`userID`,`name`,`userName`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000006','Mojitha Wijewardana','mojitha','1981-05-06',CURDATE(),'mojitha531@ail.com','0335430334','105-B,Samagi Mawatha,Aluthgama');
INSERT INTO User(`userID`,`name`,`userName`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000007','Itachi Sooriyabandara','itachi','1980-06-07',CURDATE(),'itachi183@gmail.com','0361950362','104-B,Katugasthota Road,Mahaiyawa');
INSERT INTO User(`userID`,`name`,`userName`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000008','Vikum Chandrasekara','vikum','1983-07-08',CURDATE(),'vikum193@mailg.com','0752514658','103-B,Kandy Road,Mathale');

INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000009','Mohommed Ayyash','2008-03-18',CURDATE(),'ayyash83@marlg.com','0752359539','303-B,Madawala Road,Waththegama');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000010','Chethana Dissanayaka','2008-02-29',CURDATE(),'chethana29@jalal.com','0754391532','65-V,Dodamwala Road,Ampitiya');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000011','Mahela Basnayeka','2008-03-30',CURDATE(),'mahela11@aliya.com','0734391532','31-G,Ambewela Road,Gohagoda');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000012','Sampath Warakagoda','2008-04-20',CURDATE(),'sampath90@yahoo.com','0412311322','82-J,Kandy Road,Malabe');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000013','Sapnaka Pathirana','1998-04-20',CURDATE(),'Sapnaka123@yahoo.com','0413351625','15-c,Mathale Road,Kudugala');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000014','Lahiru Dissanayaka','2008-01-20',CURDATE(),'lahiru321@yahoo.com','0752365874','51-A,katugasthota road,kandy');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000015','Madhusanka Dasanayaka','2008-08-2',CURDATE(),'Madhusanka225@yahoo.lcom','0712589745','52-V,Mathale Road,Wattegama');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000016','Bimsara Basnayaka','2008-11-08',CURDATE(),'Bimsara85@yahoo.com','0725698745','85-V,Getabe Road,Peradeniya');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000017','Dilsara hettiarachchi','2008-12-04',CURDATE(),'Dil21@yahoo.com','0763156985','45-R,Aruppola Road,Kandy');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000018','Shashika bandara','2008-06-25',CURDATE(),'Shashika987@yahoo.com','0712548956','88-V,Gannoruwa Road,Peradeniya');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000019','Dhananjaya herath','2008-03-01',CURDATE(),'Dhananjaya548@yahoo.com','0712365478','12-C,Polgolla Road,Wattegama');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000020','Saliya Kumara','2008-03-07',CURDATE(),'Saliya455@yahoo.com','0712569874','46-2008,Madawala Road,Wattegama');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000021','Dhanushka pathirana','2008-06-30',CURDATE(),'Dhanushka445@yahoo.com','0774895623','45-V,Gohagoda Road,Katugasthota');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000022','Saman Perera','2008-07-01',CURDATE(),'Saman444@yahoo.lcom','0714567895','55-A,Mahaiyawa Road,Kandy');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000023','Yasiru Bandara','2008-09-15',CURDATE(),'Yasiru444@yahoo.com','0764578945','12-D,Wattegama Road,Elkaduwa');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000024','Dilantha Malagamuwa','2008-04-25',CURDATE(),'Dilantha587@yahoo.com','0775896320','45-C,Madawala Road,Kandy');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000025','Rasika Gayan','2008-07-14',CURDATE(),'Rasi@yahoo.com','0712356487','22-A,Wattegama Road,Panwila');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000026','Sujeewa Bandara','2008-07-07',CURDATE(),'Sujeewa658@yahoo.com','0725898745','04-A,Mathale Road,Ukuwela');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000027','Mahendra perera','2008-09-30',CURDATE(),'Mahendra555@yahoo.com','702152558','01-C,Gohagoda Road,Katugasthota');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000028','Pesandu fernando','2008-05-05',CURDATE(),'Pesandu225@yahoo.com','0712546598','22-V,Madawala Road,Manikhinna');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000029','Shamen kumara','2008-03-21',CURDATE(),'Shamen789@yahoo.com','55-S,','14-A,Mahaiyawa Road,Kandy');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000030','Shiyam Dissanayaka','2008-07-06',CURDATE(),'Shiyam447@yahoo.com','0715456487','55-A,Polgolla Road,Katugasthota');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000031','Nadun Karunarathna','2008-09-25',CURDATE(),'Nadun455@yahoo.com','0715487985','66-C,Manikhinna Road,Digana');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000032','Viraj Warnakulasooriya','2008-04-01',CURDATE(),'Viraj669@yahoo.com','0714587452','25-A,Yatirawana Road,Wattegama');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000033','Sharin Gunasekara','2008-02-15',CURDATE(),'Sharin444@yahoo.com','0715487541','10-V,Getabe Road,Peradeniya');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000034','Tom Cruise','2008-04-30',CURDATE(),'@Cruise587yahoo.com','0715424578','12-A,Polgolla Road,Katugasthota');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000035','Lasith Manadul','2008-12-20',CURDATE(),'Lasith112@yahoo.com','0715245178','22-C,Mathale Road,Kudugala');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000036','Ishan Perera','2008-03-10',CURDATE(),'Ishan522@yahoo.com','0715487541','45-V,Kandy Road,Malabe');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000037','Supun Chandrasekara','2008-09-08',CURDATE(),'Supun587@yahoo.com','0712548963','99-C,Yatawara Road,Wattegama');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000038','Madhushan Iddamalgoda','2008-07-01',CURDATE(),'Madhushan445@yahoo.com','0715424589','25-A,Samagi Mawatha,Aluthgama');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000039','Nipun Wickamasingha','2008-04-25',CURDATE(),'Nipun856@yahoo.com','0712457895','45-V,Getabe Road,Peradeniya');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`) 
VALUES('U000000040','Kanishka Weerakoon','2008-05-07',CURDATE(),'Kanishka856@yahoo.com','0712589632','25-V,Aruppola Road,Kandy');

INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000041','Marielle Ribera','2008-04-21',CURDATE(),'Marielle@yahoo.com','0413351622','13/4, EPZ');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000042','Cristi Koser','2008-05-30',CURDATE(),'Cristi23@yahoo.com','0413351626','229, QUARRY ROAD');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000043','Harriett Roush','2008-06-20',CURDATE(),'Harriett1@yahoo.com','0413351627','42P, GROUND FLOOR');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000044','Sanda Whitson','2008-03-26',CURDATE(),'Sanda23@yahoo.com','0413351628','FREE TRADE ZONE');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000045','Gaye Babich','2008-02-21',CURDATE(),'Gaye23@yahoo.com','0413351629','11, Charles Circus');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000046','Pierre Jacobsen','2008-04-22',CURDATE(),'Jacobsen3@yahoo.com','0413351332','67A, RAJAPAKSA ESTATE');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000047','Ghislaine Brazell','2008-10-20',CURDATE(),'Ghislaine@yahoo.com','0423351625','NO.289, HENAMULLA');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000048','Wanita Rote','2008-05-23',CURDATE(),'Rote23@yahoo.com','0413351725','No 572, F 1');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000049','Jarvis Hoxie','2008-09-10',CURDATE(),'Jarvis123@yahoo.com','0615351625','41, RAJAPIHILLA MAWATHAKANDY');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000050','Adella Buswell','2008-01-26',CURDATE(),'Adella222@yahoo.com','0413351625','42, RAJAPIHILLA MAWATHAKANDY');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000051','Ivana Vella','2008-01-02',CURDATE(),'Ivana43@yahoo.com','0413355425','363/2, RAJASINGHE MAWATHA');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000052','Rocio Cosper','2008-12-20',CURDATE(),'Rocio@yahoo.com','0413351675','No 20 Rodney Place Colombo 08');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000053','Cherryl Petzold','2008-05-29',CURDATE(),'Cherryl@yahoo.com','0456735162','542, DUPLICATION ROAD');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000054','Paul Bumpus','2008-02-12',CURDATE(),'Paul99@yahoo.com','0113351625','70 main street hatton');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000055','Sam Seder','2008-08-24',CURDATE(),'Seder53@yahoo.com','0413361625','PLOT NO.27C,MIRIGAMA EXPORT MIRIGAMA');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000056','Yesenia Maland','2008-04-09',CURDATE(),'Yesenia@yahoo.com','0413991625','22, ALFRED HOUSE ROADCOLOMBO 03');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000057','Faustino Rhoten','2008-11-23',CURDATE(),'Faustino11@yahoo.com','0877351625','93-2/1, New Kelani Bridge Road');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000058','Christia Sartain','2008-01-22',CURDATE(),'Christia89@yahoo.com','0413358825','107/7, ARALIYA PLACE');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000059','Noel Thiel','2008-06-18',CURDATE(),'Noel22@yahoo.com','0413351775','61, Lake Crescent');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000060','Tad Hubble','2008-02-19',CURDATE(),'Tad123@yahoo.com','0413351795','KALAPUGAMA ROADMORANTHUDUWA 12564');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000061','Edmund Audet','2008-02-25',CURDATE(),'Edmund222@yahoo.com','0443351625','2/183 , Henpitagedara');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000062','Mirtha Line','2008-02-11',CURDATE(),'Mirtha3@yahoo.com','0413323645','52, GREEN PATH');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000063','Nakita Weese','2008-03-05',CURDATE(),'Nakita45@yahoo.com','0413344625','NO.327, CEYESTAR MANDIRAYA, GALLE ROAD');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000064','Tresa Reidhead','2008-04-12',CURDATE(),'Tresa@yahoo.com','0413352225','25, HEDGES COURTCOLOMBO 10');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000065','Chun Palomares','2008-12-07',CURDATE(),'Chun33@yahoo.com','0413311625','138/4 Minuwangoda Road, Ekala Ja-Ela');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000066','Winston Lavergne','2008-12-20',CURDATE(),'Winston12@yahoo.com','0213351625','Wijesinghe Mawatha, Kurunduwaththa');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000067','Caterina Pelfrey','2008-09-11',CURDATE(),'Caterina@yahoo.com','0333351625','85, 1/1, Ward Place');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000068','Jeanie Sasso','2008-01-22',CURDATE(),'Jeanie77@yahoo.com','0413234625','453A, Havelock Road');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000069','Kyoko Hurley','2008-08-05',CURDATE(),'Kyoko23@yahoo.com','0413367825','70 main street hatton');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000070','Rolf Herold','2008-07-12',CURDATE(),'Rolf23@yahoo.com','0413351234','70, KCC Perera Mw. Colombo 13');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000071','Patricia Munoz','2008-07-11',CURDATE(),'Patricia234@yahoo.com','0765351625','Eagle House 75 Kumaran Ratnam Road, 02');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000072','Phung Gaston','2008-04-23',CURDATE(),'Phung23@yahoo.com','0413351665','NO.16, MODARAWILA INDUSTRIAL ZONEPANADURA');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000073','Claretha Hartzell','2008-09-10',CURDATE(),'Claretha2324@yahoo.com','0414451625','9, ATHULA MAWATHA');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000074','Shanti Rardin','2008-08-09',CURDATE(),'Shanti3456@yahoo.com','0413351625','363/2, RAJASINGHE MAWATHA');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000075','Rocco Segui','2008-07-29',CURDATE(),'Rocco89@yahoo.com','0413312325','80, NAWAM MAWATHACOLOMBO 02');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000076','Suellen Rayburn','2008-08-12',CURDATE(),'Suellen@yahoo.com','0123351625','79 C W W Kannangara Mw Colombo 07');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000077','Delilah Lapan','2008-08-05',CURDATE(),'Delilah@yahoo.com','0412341625','123, FARM GARDEN');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000078','Stanley Pappas','2008-05-21',CURDATE(),'Stanley99@yahoo.com','0443251625','165/ 6 City Paradise Super Market, Main Street');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000079','Reta Bearden','2008-07-26',CURDATE(),'Reta2008@yahoo.com','0413354325','NO.116, UNION PLACE');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000080','George Kuru','2008-01-02',CURDATE(),'George987@yahoo.com','0413351555','79 C W W Kannangara Mw Colombo 07');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000081','Shanel Fleeman','2008-01-18',CURDATE(),'Shanel56@yahoo.com','0413431625','284, VAUXHALL STREETCOLOMBO 02');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000082','Jestine Sanderson','2008-02-21',CURDATE(),'Jestine56@yahoo.com','0422351625','174/1 A, GALLE ROADMOUNT LAVINIA');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000083','Willian Lovern','2008-08-16',CURDATE(),'Willianl@yahoo.com','0413512125','51, OLD KESBEWA ROAD');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000084','Errol Mewborn','2008-02-02',CURDATE(),'Errol555@yahoo.com','0419351625','B-18, YMBA SHOPPING COMPLEX');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000085','Sharri Tena','2008-03-23',CURDATE(),'Sharri53@yahoo.com','0413111625','291/50 Havelock Gardens Colombo 06');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000086','Heriberto Canino','2008-02-12',CURDATE(),'Heriberto45@yahoo.com','0422251625','29, Prince of Wales Avenue');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000087','Ophelia Mcginty','2008-01-03',CURDATE(),'Ophelia11@yahoo.com','0413398725','Lucky Plaza, No. 70, St. Anthony');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000088','Brigid Ahlstrom','2008-04-04',CURDATE(),'Brigid123@yahoo.com','0413319025','49, Olcott Mw Colombo 11');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000089','Celestine Boring','2008-04-14',CURDATE(),'Celestine8990@yahoo.com','0410001625','NO.2, 4TH FLOOR, TEMPLE LANE');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000090','Ching Cluck','2008-11-04',CURDATE(),'Ching13@yahoo.com','0413350095','171, AVALON ESTATE');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000091','Isuru Tabor','2008-04-16',CURDATE(),'Isuru11@yahoo.com','0413351105','KANDY ROAD, KALALPITIYA');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000092','Gunter Freeman','2008-05-26',CURDATE(),'gunter26@yahoo.com','0213351105','24-B, ALPITIYA ROAD, MULDENIYA');

INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000093','Dineru Chethiya','1987-01-02',CURDATE(),'Dineru@yahoo.com','0773751622','13/4, KOssinna,Gelioya');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000094','Anuja Adikshana','1987-01-04',CURDATE(),'Anuja23@yahoo.com','0812455735','229, Karamada ROAD,Gelioya');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000095','Kaveesha Nimsara','1987-02-21',CURDATE(),'Kaveesha1@yahoo.com','0453751622','42P, Godapala,Gelioya');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000096','Ravindu Lakshan','1987-04-27',CURDATE(),'Ravindu3@yahoo.com','0812454735','77P, Free Zone,gampala');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000097','Isindu Malmika','1987-07-22',CURDATE(),'Isindu823@yahoo.com','0745351622','11/7, Thusan Vila,weligalle');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000098','Ishara Kavishan','1987-04-19',CURDATE(),'Ishara8@yahoo.com','0812745735','65A, MNW Estate,Jayamalapura');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000099','Menuka Dilshan','1987-03-06',CURDATE(),'Menukae@yahoo.com','0973751622','289/B, Kalugamuwa,Nawalapitiya');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000100','Pasan Nimsara','1987-05-29',CURDATE(),'Pasan123@yahoo.com','0812465735','572/7, F1 Road,Kuruduwaththa');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000101','Praveen Ranaweera','1987-09-11',CURDATE(),'Praveen17@yahoo.com','0713501622','66/C, Raja Mawatha,Kandy');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000102','Ruvindu Kawya','1987-07-27',CURDATE(),'Ruvindu22@yahoo.com','0743751622','26, Kuru Mawatha,Agunawala');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000103','Shehan Tharaka','1987-04-14',CURDATE(),'Shehan43@yahoo.com','0953751622','363/2,Rajawaththa,Peradeniya');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000104','Pasindu Iduwara','1987-12-21',CURDATE(),'Pasinduo@yahoo.com','081245735','E20,Lenin Road,Pitawalawaththa');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000105','Jayasanka Bandara','1987-12-10',CURDATE(),'Jayasanka63@yahoo.com','0773751622','54, DS Road,Polgahaanga');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000106','Hasitha Rathnayake','1987-11-11',CURDATE(),'Hasith9@yahoo.com','081245735','75/G,Main Street,Bothalapitiya');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000107','Thushan Randika','1987-05-06',CURDATE(),'Thushanr53@yahoo.com','0773362785',' 37D,Saman road,Kahatapitiya');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000108','Nuwan Sameera','1987-02-13',CURDATE(),'Nuwan58@yahoo.com','0753991677','55/X, Alber Road,Warakapitiya');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000109','Kavishka Pathum','1987-11-22',CURDATE(),' Kavishka75@yahoo.com','0756351622','78/1, New Street ,Ulapane');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000110','Bathishka Baween','1987-07-23',CURDATE(),'Bathishka00@yahoo.com','0723351227','457/3, Rathna Place,Pallegama');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000111','Saman Kumar','1987-01-23',CURDATE(),'Saman33@yahoo.com','0703355555','365/H, Lake House Road,Pilapitiya');
INSERT INTO User(`userID`,`name`,`birthDate`,`registeredDate`,`email`,`phone`,`address`)
VALUES('U000000112','Lasith Manadul','1987-08-15',CURDATE(),'Lasith99@yahoo.com','0763361222','241/8,Rose Road,Galboda');

--NonAcademicStaff
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000001','Inventory Manager','Management');
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000002','Student Manager','Management');
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000003','Exam Manager','Management');
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000004','Attendance Manager','Management');
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000005','Result Manager','Management');
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000006','Staff Manager','Management');
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000007','Library Manager','Management');
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000008','Subject Manager','Management');

INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000093','Development Officer','Officer');
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000094','Financial Assistant','Management');
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000095','Librarian','Management');
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000096','Teacher Assistant','Assistant');
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000097','Management Assistant','Assistant');
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000098','Administrative Assistant','Administration');
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000099','Computer Assistant','Assistant');
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000100','Library Assistant','Assistant');
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000101','laboratory Assistant','Assistant');
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000102','Office supporter','support');
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000103','Cricket Coach','Coach');
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000104','Boxing Coach','Coach');
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000105','Chess Coach','Coach');
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000106','Netball Coach','Coach');
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000107','Volleyball Coach','Coach');
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000108','football Coach','Coach');
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000109','Cadet Coach','Coach');
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000110','sanitary labour','labour');
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000111','labour','labour');
INSERT INTO NonAcademicStaff(`userID`,`designation`,`qualification`) 
VALUES('U000000112','Watcher','Security');

--AcademicStaff
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000009','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000010','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000011','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000012','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000013','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000014','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000015','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000016','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000017','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000018','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000019','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000020','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000021','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000022','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000023','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000024','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000025','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000026','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000027','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000028','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000029','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000030','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000031','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000032','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000033','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000034','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000035','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000036','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000037','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000038','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000039','Teacher','Teaching',3);
INSERT INTO AcademicStaff(`userID`,`designation`,`qualification`,`availability`) 
VALUES('U000000040','Teacher','Teaching',3);

--Student
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000041','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000042','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000043','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000044','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000045','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000046','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000047','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000048','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000049','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000050','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000051','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000052','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000053','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000054','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000055','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000056','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000057','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000058','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000059','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000060','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000061','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000062','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000063','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000064','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000065','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000066','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000067','Grade 6','A','Christianity');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000068','Grade 6','A','Christianity');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000069','Grade 6','A','Christianity');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000070','Grade 6','A','Christianity');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000071','Grade 6','A','Christianity');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000072','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000073','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000074','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000075','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000076','Grade 6','A','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000077','Grade 6','A','Islam');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000078','Grade 6','A','Islam');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000079','Grade 6','A','Islam');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000080','Grade 6','A','Islam');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000081','Grade 6','B','Christianity');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000082','Grade 6','B','Christianity');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000083','Grade 6','B','Hindu');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000084','Grade 6','B','Hindu');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000085','Grade 6','B','Hindu');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000086','Grade 6','B','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000087','Grade 6','B','Buddhism');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000088','Grade 6','B','Islam');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000089','Grade 6','B','Christianity');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000090','Grade 6','B','Christianity');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000091','Grade 6','B','Christianity');
INSERT INTO Student(`userID`,`grade`,`class`,`religion`)
VALUES('U000000092','Grade 6','B','Buddhism');

--Item
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000001','MarkerPen-Blue','stationery','2019-06-20',CURDATE(),'NEW','50','unit','600.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000002','MarkerPen-Red','stationery','2019-06-20',CURDATE(),'NEW','50','unit','600.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000003','MarkerPen-Green','stationery','2019-06-20',CURDATE(),'NEW','50','unit','600.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000004','MarkerPen-Black','stationery','2019-06-20',CURDATE(),'NEW','50','unit','600.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000005','A4','stationery','2019-07-01',CURDATE(),'NEW','50','unit','100.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000006','CR-Book','stationery','2019-07-01',CURDATE(),'NEW','20','unit','2000.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000007','White-Board','stationery','2019-07-01',CURDATE(),'NEW','05','unit','20000.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000008','Envelope','stationery','2019-07-01',CURDATE(),'NEW','50','unit','.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000009','File','stationery','2019-07-01',CURDATE(),'NEW','50','unit','500.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000010','MarkerPen-Ink','stationery','2019-07-01',CURDATE(),'NEW','05','unit','1500.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000011','Ex-Books','stationery','2019-07-01',CURDATE(),'NEW','50','unit','2500.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000012','CarbonSeal','stationery','2019-07-01',CURDATE(),'NEW','02','unit','1000.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000013','BristolBoard','stationery','2019-07-01',CURDATE(),'NEW','50','unit','600.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000014','platignum','stationery','2019-07-01',CURDATE(),'NEW','30','unit','600.00','Not Specified');

INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000015','Acitic Acid','Chemicals','2019-08-20',CURDATE(),'NEW','10','units','1000.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000016','Hydrochloric acid','Chemicals','2019-08-20',CURDATE(),'NEW','05','units','1500.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000017','Potassium permanganate','Chemicals','2019-08-20',CURDATE(),'NEW','05','units','1000.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000018','Refrigerator','Home Appliences','2019-08-20',CURDATE(),'NEW','01','units','50000.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000019','toaster','Home Appliences','2019-08-20',CURDATE(),'NEW','02','units','5000.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000020','microwave','Home Appliences','2019-08-21',CURDATE(),'NEW','01','units','20000.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000021','Fan','Electronics','2019-08-20',CURDATE(),'NEW','05','units','25000.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000022','AC','Electronics','2019-08-22',CURDATE(),'NEW','02','units','30000.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000023','Telephone','Electronics','2019-08-23',CURDATE(),'NEW','01','units','2500.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000024','Desk','Furniture Academic','2019-08-24',CURDATE(),'NEW','05','units','10000.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000025','Chair','Furniture Academic','2019-08-24',CURDATE(),'NEW','10','units','14000.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000026','Bench','Furniture NonAcademic','2019-08-25',CURDATE(),'NEW','02','units','6000.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000027','Drawer','Furniture NonAcademic','2019-08-25',CURDATE(),'NEW','01','units','5000.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000028','Keyboard','Musics','2019-08-25',CURDATE(),'NEW','02','units','30000.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000029','Piano','Musics','2019-08-25',CURDATE(),'NEW','01','units','50000.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000030','Guitar','Musics','2019-08-26',CURDATE(),'NEW','02','units','15000.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000031','Bat','Sports','2019-08-26',CURDATE(),'NEW','02','units','16000.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000032','broom','Cleaning','2019-08-26',CURDATE(),'NEW','05','units','900.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000033','Vacuum Cleaner','Cleaning','2019-08-27',CURDATE(),'NEW','02','units','12000.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000034','Wood ','Materials','2019-08-27',CURDATE(),'NEW','50','units','5000.00','Not Specified');
INSERT INTO Item(`resourceID`,`description`,`category`,`supplyDate`,`updatedOn`,`status`,`quantity`,`unit`,`cost`,`expireDate`) 
VALUES('I000000035','Ball','Sports','2019-08-23',CURDATE(),'NEW','05','units','650.00','2021-09-13');

--Book
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000001','Journey to the center of the Earth','English','Sci-Fi','Jules Verne','500.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000002','The Martian','English','Sci-Fi','Andy Weir','800.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000003','Ready Player One','English','Sci-Fi','Ernest Cline','600.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000004','I, Robot','English','Sci-Fi','Isaac Asimov','650.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000005','Harry Potter and the Philosophers Stone','English','Adventure','J. K. Rowling','1200.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000006','A Game of Thrones','English','Adventure','R. R. Martin','1500.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000007','Harry Potter and the Order of the Phoenix','English','Adventure','J. K. Rowling','1200.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000008','Harry Potter and the Goblet of Fire','English','Adventure','J. K. Rowling','1200.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000009','How Children Fail','English','Educational','T.Weliwita','500.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000010','The End of Education','English','Educational','Neil Postman','200.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000011','The aims of education','English','Educational','Neil Postman','300.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000012','To Kill a Mockingbird','English','Literature','Harper Lee','420.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000013','Moby Dick or The Whale','English','Literature','Harper Lee','950.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000014','Logic','English','Logic','Graham Priest','670.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000015','A rulebook for arguments','English','Logic','Graham Priest','460.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000016','Wealth of Nations','English','Economic','Adam Smith','490.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000017','Freakonomics','English','Economic','Adam Smith','1200.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000018','How to start computer','English','ComputerScience','R.Vikum','500.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000019','How to Turn off Computer ','English','ComputerScience','R.Vikum','1200.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000020','What is Computer?','English','ComputerScience','R.Vikum','1250.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000021','Learn Keyboard','English','Art&Music','A.Dilshan','900.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000022','Learn Guitar','English','Art&Music','N.Neranjan','900.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000023','Art','English','Art&Music','A.Sandeep','550.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000024','Basic Accounting','English','Accounting','M.Mojhitha','400.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000025','The accountant','English','Accounting','T.Swift','800.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000026','What is Geography','English','Geography','G.Arther','660.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000027','How to cut a tree','English','Agriculture','A.Badurdien','1200.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000028','the Tree','English','Agriculture','A.C.S Weli','500.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000029','Junk Food','English','Health','R.R.Sandeep','950.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000030','Lets Walk','English','Health','R.R.Sandeep','1000.00',CURDATE());
INSERT INTO Book(`resourceID`,`title`,`language`,`category`,`author`,`price`,`updatedOn`) 
VALUES('B000000031','I cant walk','English','Health','P.S.Vikum','250.00',CURDATE());

--BookHasCopies
INSERT INTO BookHasCopies(`resourceID`,`copy`,`ISBN`,`supplyDate`,`status`) 
VALUES('B000000001',1,'ISBN 978-3-16-148410-0',CURDATE(),'burrowed');

--Subject
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000001','Sinhala','Grade 6');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000002','English','Grade 6');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000003','Mathematics','Grade 6');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000004','Science','Grade 6');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000005','Buddhism','Grade 6');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000006','History','Grade 6');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000007','Geography','Grade 6');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000008','Arts','Grade 6');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000009','Music','Grade 6');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000010','Dancing','Grade 6');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000011','Health','Grade 6');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000012','Practical & Technical Skills','Grade 6');

INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000013','Sinhala','Grade 7');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000014','English','Grade 7');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000015','Mathematics','Grade 7');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000016','Science','Grade 7');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000017','Buddhism','Grade 7');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000018','History','Grade 7');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000019','Geography','Grade 7');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000020','Arts','Grade 7');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000021','Music','Grade 7');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000022','Dancing','Grade 7');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000023','Health','Grade 7');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000024','Practical & Technical Skills','Grade 7');

INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000025','Sinhala','Grade 8');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000026','English','Grade 8');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000027','Mathematics','Grade 8');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000028','Science','Grade 8');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000029','Buddhism','Grade 8');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000030','History','Grade 8');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000031','Geography','Grade 8');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000032','Arts','Grade 8');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000033','Music','Grade 8');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000034','Dancing','Grade 8');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000035','Health','Grade 8');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000036','Practical & Technical Skills','Grade 8');

INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000037','Sinhala','Grade 9');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000038','English','Grade 9');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000039','Mathematics','Grade 9');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000040','Science','Grade 9');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000041','Buddhism','Grade 9');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000042','History','Grade 9');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000043','Geography','Grade 9');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000044','Arts','Grade 9');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000045','Music','Grade 9');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000046','Dancing','Grade 9');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000047','Health','Grade 9');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000048','Practical & Technical Skills','Grade 9');

INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000049','Buddhism','Grade 10');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000050','Sinhala language & Literature','Grade 10');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000051','Mathematics','Grade 10');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000052','Science','Grade 10');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000053','Business & Accounts','Grade 10');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000054','Eastern Music','Grade 10');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000055','Traditional Dancing','Grade 10');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000056','Sinhala Literature','Grade 10');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000057','Information and Communication Technology','Grade 10');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000058','Art','Grade 10');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000059','Health & Physical Education','Grade 10');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000060','English','Grade 10');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000061','History','Grade 10');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000062','Geography','Grade 10');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000063','Drama','Grade 10');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000064','Home Economics','Grade 10');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000065','Agriculture','Grade 10');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000066','Civics ','Grade 10');

INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000067','Buddhism','Grade 11');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000068','Sinhala language & Literature','Grade 11');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000069','Mathematics','Grade 11');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000070','Science','Grade 11');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000071','Business & Accounts','Grade 11');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000072','Eastern Music','Grade 11');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000073','Traditional Dancing','Grade 11');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000074','Sinhala Literature','Grade 11');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000075','Information and Communication Technology','Grade 11');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000076','Art','Grade 11');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000077','Health & Physical Education','Grade 11');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000078','English','Grade 11');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000079','History','Grade 11');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000080','Geography','Grade 11');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000081','Drama','Grade 11');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000082','Home Economics','Grade 11');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000083','Agriculture','Grade 11');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000084','Civics','Grade 11');

INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000085','Chemistry','Grade 12');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000086','Biology','Grade 12');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000087','Physics','Grade 12');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000088','Accounting','Grade 12');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000089','Economics','Grade 12');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000090','Business Studies','Grade 12');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000091','Agricultural Science','Grade 12');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000092','Drama and the Theatre','Grade 12');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000093','Art','Grade 12');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000094','Information and Communication Technology','Grade 12');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000095','Home Economics','Grade 12');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000096','Combined Mathematics','Grade 12');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000097','Geography','Grade 12');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000098','History','Grade 12');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000099','Political Science','Grade 12');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000100','General English','Grade 12');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000101','Logic & Scientific Method','Grade 12');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000102','Dancing','Grade 12');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000103','Eastern Music','Grade 12');

INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000104','Chemistry','Grade 13');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000105','Biology','Grade 13');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000106','Physics','Grade 13');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000107','Accounting','Grade 13');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000108','Economics','Grade 13');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000109','Business Studies','Grade 13');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000110','Agricultural Science','Grade 13');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000111','Drama and the Theatre','Grade 13');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000112','Art','Grade 13');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000113','Information and Communication Technology','Grade 13');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000114','Home Economics','Grade 13');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000115','Combined Mathematics','Grade 13');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000116','Geography','Grade 13');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000117','History','Grade 13');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000118','Political Science','Grade 13');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000119','General English','Grade 13');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000120','Logic & Scientific Method','Grade 13');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000121','Dancing','Grade 13');
INSERT INTO Subject(`subjectID`,`subject`,`grade`)
VALUES('S000000122','Eastern Music','Grade 13');

--StudentHasSubjects
--6A

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000041','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000041','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000041','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000041','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000041','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000041','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000041','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000041','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000041','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000041','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000041','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000041','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000042','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000042','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000042','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000042','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000042','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000042','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000042','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000042','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000042','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000042','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000042','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000042','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000043','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000043','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000043','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000043','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000043','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000043','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000043','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000043','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000043','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000043','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000043','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000043','S000000012');
	
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000044','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000044','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000044','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000044','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000044','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000044','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000044','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000044','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000044','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000044','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000044','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000044','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000045','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000045','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000045','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000045','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000045','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000045','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000045','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000045','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000045','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000045','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000045','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000045','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000046','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000046','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000046','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000046','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000046','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000046','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000046','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000046','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000046','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000046','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000046','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000046','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000047','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000047','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000047','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000047','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000047','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000047','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000047','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000047','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000047','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000047','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000047','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000047','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000048','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000048','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000048','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000048','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000048','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000048','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000048','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000048','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000048','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000048','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000048','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000048','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000049','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000049','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000049','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000049','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000049','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000049','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000049','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000049','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000049','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000049','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000049','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000049','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000050','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000050','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000050','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000050','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000050','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000050','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000050','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000050','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000050','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000050','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000050','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000050','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000051','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000051','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000051','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000051','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000051','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000051','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000051','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000051','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000051','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000051','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000051','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000051','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000052','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000052','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000052','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000052','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000052','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000052','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000052','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000052','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000052','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000052','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000052','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000052','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000053','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000053','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000053','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000053','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000053','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000053','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000053','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000053','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000053','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000053','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000053','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000053','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000054','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000054','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000054','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000054','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000054','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000054','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000054','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000054','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000054','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000054','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000054','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000054','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000055','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000055','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000055','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000055','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000055','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000055','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000055','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000055','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000055','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000055','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000055','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000055','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000056','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000056','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000056','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000056','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000056','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000056','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000056','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000056','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000056','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000056','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000056','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000056','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000057','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000057','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000057','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000057','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000057','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000057','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000057','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000057','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000057','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000057','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000057','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000057','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000058','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000058','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000058','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000058','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000058','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000058','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000058','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000058','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000058','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000058','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000058','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000058','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000059','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000059','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000059','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000059','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000059','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000059','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000059','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000059','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000059','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000059','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000059','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000059','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000060','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000060','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000060','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000060','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000060','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000060','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000060','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000060','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000060','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000060','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000060','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000060','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000061','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000061','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000061','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000061','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000061','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000061','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000061','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000061','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000061','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000061','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000061','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000061','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000062','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000062','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000062','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000062','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000062','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000062','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000062','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000062','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000062','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000062','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000062','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000062','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000063','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000063','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000063','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000063','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000063','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000063','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000063','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000063','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000063','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000063','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000063','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000063','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000064','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000064','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000064','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000064','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000064','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000064','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000064','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000064','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000064','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000064','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000064','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000064','S000000012');


INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000065','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000065','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000065','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000065','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000065','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000065','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000065','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000065','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000065','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000065','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000065','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000065','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000066','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000066','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000066','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000066','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000066','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000066','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000066','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000066','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000066','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000066','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000066','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000066','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000067','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000067','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000067','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000067','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000067','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000067','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000067','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000067','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000067','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000067','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000067','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000067','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000068','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000068','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000068','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000068','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000068','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000068','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000068','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000068','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000068','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000068','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000068','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000068','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000069','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000069','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000069','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000069','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000069','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000069','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000069','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000069','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000069','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000069','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000069','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000069','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000070','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000070','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000070','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000070','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000070','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000070','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000070','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000070','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000070','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000070','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000070','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000070','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000071','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000071','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000071','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000071','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000071','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000071','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000071','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000071','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000071','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000071','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000071','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000071','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000072','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000072','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000072','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000072','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000072','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000072','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000072','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000072','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000072','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000072','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000072','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000072','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000073','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000073','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000073','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000073','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000073','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000073','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000073','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000073','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000073','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000073','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000073','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000073','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000074','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000074','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000074','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000074','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000074','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000074','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000074','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000074','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000074','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000074','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000074','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000074','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000075','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000075','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000075','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000075','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000075','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000075','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000075','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000075','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000075','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000075','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000075','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000075','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000076','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000076','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000076','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000076','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000076','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000076','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000076','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000076','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000076','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000076','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000076','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000076','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000077','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000077','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000077','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000077','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000077','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000077','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000077','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000077','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000077','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000077','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000077','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000077','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000078','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000078','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000078','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000078','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000078','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000078','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000078','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000078','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000078','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000078','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000078','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000078','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000079','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000079','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000079','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000079','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000079','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000079','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000079','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000079','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000079','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000079','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000079','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000079','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000080','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000080','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000080','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000080','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000080','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000080','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000080','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000080','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000080','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000080','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000080','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000080','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000081','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000081','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000081','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000081','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000081','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000081','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000081','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000081','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000081','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000081','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000081','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000081','S000000012');

--6B
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000082','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000082','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000082','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000082','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000082','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000082','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000082','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000082','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000082','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000082','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000082','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000082','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000083','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000083','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000083','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000083','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000083','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000083','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000083','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000083','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000083','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000083','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000083','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000083','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000084','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000084','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000084','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000084','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000084','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000084','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000084','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000084','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000084','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000084','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000084','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000084','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000085','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000085','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000085','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000085','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000085','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000085','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000085','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000085','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000085','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000085','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000085','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000085','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000086','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000086','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000086','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000086','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000086','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000086','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000086','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000086','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000086','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000086','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000086','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000086','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000087','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000087','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000087','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000087','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000087','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000087','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000087','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000087','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000087','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000087','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000087','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000087','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000088','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000088','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000088','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000088','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000088','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000088','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000088','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000088','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000088','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000088','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000088','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000088','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000089','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000089','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000089','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000089','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000089','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000089','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000089','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000089','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000089','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000089','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000089','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000089','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000090','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000090','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000090','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000090','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000090','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000090','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000090','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000090','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000090','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000090','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000090','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000090','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000091','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000091','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000091','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000091','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000091','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000091','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000091','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000091','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000091','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000091','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000091','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000091','S000000012');

INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000092','S000000001');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000092','S000000002');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000092','S000000003');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000092','S000000004');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000092','S000000005');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000092','S000000006');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000092','S000000007');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000092','S000000008');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000092','S000000009');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000092','S000000010');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000092','S000000011');
INSERT INTO StudentHasSubjects(`userID`,`subjectID`)
VALUES('U000000092','S000000012');


--AcademicTeachSubjects
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000009','S000000001','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000009','S000000002','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000009','S000000003','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000010','S000000004','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000010','S000000005','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000010','S000000006','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000011','S000000007','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000011','S000000008','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000011','S000000009','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000012','S000000010','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000012','S000000011','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000012','S000000012','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000013','S000000013','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000013','S000000014','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000013','S000000015','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000014','S000000016','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000014','S000000017','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000014','S000000018','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000015','S000000019','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000015','S000000020','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000015','S000000021','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000016','S000000022','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000016','S000000023','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000016','S000000024','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000017','S000000025','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000017','S000000026','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000017','S000000027','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000018','S000000028','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000018','S000000029','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000018','S000000030','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000019','S000000031','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000019','S000000032','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000019','S000000033','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000020','S000000034','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000020','S000000035','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000020','S000000036','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000021','S000000037','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000021','S000000038','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000021','S000000039','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000022','S000000040','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000022','S000000041','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000022','S000000042','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000023','S000000043','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000023','S000000044','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000023','S000000045','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000024','S000000046','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000024','S000000047','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000024','S000000048','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000025','S000000049','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000025','S000000050','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000025','S000000051','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000026','S000000052','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000026','S000000053','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000026','S000000054','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000027','S000000055','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000027','S000000056','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000027','S000000057','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000028','S000000058','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000028','S000000059','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000028','S000000060','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000029','S000000061','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000029','S000000062','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000029','S000000063','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000030','S000000064','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000030','S000000065','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000030','S000000066','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000031','S000000067','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000031','S000000068','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000031','S000000069','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000032','S000000070','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000032','S000000071','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000032','S000000072','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000033','S000000073','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000033','S000000074','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000033','S000000075','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000034','S000000076','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000034','S000000077','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000034','S000000078','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000035','S000000079','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000035','S000000080','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000035','S000000081','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000036','S000000082','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000036','S000000083','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000036','S000000084','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000037','S000000085','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000037','S000000086','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000037','S000000087','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000038','S000000088','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000038','S000000089','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000038','S000000090','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000039','S000000091','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000039','S000000092','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000039','S000000093','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000040','S000000094','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000040','S000000095','2018-09-01');
INSERT INTO AcademicTeachSubjects(`userID`,`subjectID`,`assignedDate`)
VALUES('U000000040','S000000096','2018-09-01');

--Exam
------------------------------------------------------ 2 Term ---------------------------------------------------

INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000001','2019-08-01','Grade 6','S000000001','08:30:000','10:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000002','2019-08-01','Grade 6','S000000002','11:00:000','01:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000003','2019-08-02','Grade 6','S000000003','08:30:000','10:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000004','2019-08-02','Grade 6','S000000004','11:00:000','01:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000005','2019-08-03','Grade 6','S000000005','08:30:000','10:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000006','2019-08-05','Grade 6','S000000006','11:00:000','01:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000007','2019-08-05','Grade 6','S000000007','08:30:000','10:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000008','2019-08-06','Grade 6','S000000008','11:00:000','01:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000009','2019-08-07','Grade 6','S000000009','08:30:000','10:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000010','2019-08-07','Grade 6','S000000010','11:00:000','01:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000011','2019-08-08','Grade 6','S000000011','08:30:000','10:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000012','2019-08-08','Grade 6','S000000012','11:00:000','01:00:000','2019','2','Finallized');

INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000013','2019-08-05','Grade 7','S000000013','08:30:000','10:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000014','2019-08-05','Grade 7','S000000014','11:00:000','01:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000015','2019-08-06','Grade 7','S000000015','08:30:000','10:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000016','2019-08-06','Grade 7','S000000016','11:00:000','01:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000017','2019-08-07','Grade 7','S000000017','08:30:000','10:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000018','2019-08-07','Grade 7','S000000018','11:00:000','01:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000019','2019-08-08','Grade 7','S000000019','08:30:000','10:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000020','2019-08-08','Grade 7','S000000020','11:00:000','01:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000021','2019-08-09','Grade 7','S000000021','08:30:000','10:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000022','2019-08-09','Grade 7','S000000022','11:00:000','01:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000023','2019-08-12','Grade 7','S000000023','08:30:000','10:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000024','2019-08-12','Grade 7','S000000024','11:00:000','01:00:000','2019','2','Finallized');

INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000025','2019-07-29','Grade 8','S000000025','08:30:000','10:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000026','2019-07-29','Grade 8','S000000026','11:00:000','01:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000027','2019-07-30','Grade 8','S000000027','08:30:000','10:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000028','2019-07-30','Grade 8','S000000028','11:00:000','01:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000029','2019-07-31','Grade 8','S000000029','08:30:000','10:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000030','2019-07-31','Grade 8','S000000030','11:00:000','01:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000031','2019-08-01','Grade 8','S000000031','08:30:000','10:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000032','2019-08-01','Grade 8','S000000032','11:00:000','01:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000033','2019-08-02','Grade 8','S000000033','08:30:000','10:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000034','2019-08-02','Grade 8','S000000034','11:00:000','01:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000035','2019-08-05','Grade 8','S000000035','08:30:000','10:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000036','2019-08-05','Grade 8','S000000036','11:00:000','01:00:000','2019','2','Finallized');

INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000037','2019-07-22','Grade 9','S000000037','08:30:000','10:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000038','2019-07-22','Grade 9','S000000038','11:00:000','01:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000039','2019-07-23','Grade 9','S000000039','08:30:000','10:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000040','2019-07-23','Grade 9','S000000040','11:00:000','01:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000041','2019-07-24','Grade 9','S000000041','08:30:000','10:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000042','2019-07-24','Grade 9','S000000042','11:00:000','01:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000043','2019-07-25','Grade 9','S000000043','08:30:000','10:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000044','2019-07-25','Grade 9','S000000044','11:00:000','01:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000045','2019-07-26','Grade 9','S000000045','08:30:000','10:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000046','2019-07-26','Grade 9','S000000046','11:00:000','01:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000047','2019-07-27','Grade 9','S000000047','08:30:000','10:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000048','2019-08-27','Grade 9','S000000048','11:00:000','01:00:000','2019','2','Finallized');

INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000049','2019-07-08','Grade 10','S000000049','08:30:000','09:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000050','2019-07-08','Grade 10','S000000050','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000051','2019-07-09','Grade 10','S000000051','08:30:000','09:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000052','2019-07-09','Grade 10','S000000052','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000053','2019-07-10','Grade 10','S000000053','08:30:000','09:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000054','2019-07-10','Grade 10','S000000054','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000055','2019-07-11','Grade 10','S000000055','08:30:000','09:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000056','2019-07-11','Grade 10','S000000056','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000057','2019-07-12','Grade 10','S000000057','08:30:000','09:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000058','2019-07-12','Grade 10','S000000058','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000059','2019-07-15','Grade 10','S000000059','08:30:000','09:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000060','2019-07-15','Grade 10','S000000060','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000061','2019-07-16','Grade 10','S000000061','08:30:000','09:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000062','2019-07-16','Grade 10','S000000062','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000063','2019-07-17','Grade 10','S000000063','08:30:000','09:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000064','2019-07-17','Grade 10','S000000064','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000065','2019-07-18','Grade 10','S000000065','08:30:000','09:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000066','2019-07-18','Grade 10','S000000066','10:30:000','01:30:000','2019','2','Finallized');

INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000067','2019-07-08','Grade 11','S000000067','08:30:000','09:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000068','2019-07-08','Grade 11','S000000068','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000069','2019-07-09','Grade 11','S000000069','08:30:000','09:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000070','2019-07-09','Grade 11','S000000070','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000071','2019-07-10','Grade 11','S000000071','08:30:000','09:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000072','2019-07-10','Grade 11','S000000072','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000073','2019-07-11','Grade 11','S000000073','08:30:000','09:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000074','2019-07-11','Grade 11','S000000074','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000075','2019-07-12','Grade 11','S000000075','08:30:000','09:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000076','2019-07-12','Grade 11','S000000076','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000077','2019-07-15','Grade 11','S000000077','08:30:000','09:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000078','2019-07-15','Grade 11','S000000078','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000079','2019-07-16','Grade 11','S000000079','08:30:000','09:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000080','2019-07-16','Grade 11','S000000080','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000081','2019-07-17','Grade 11','S000000081','08:30:000','09:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000082','2019-07-17','Grade 11','S000000082','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000083','2019-07-18','Grade 11','S000000083','08:30:000','09:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000084','2019-07-18','Grade 11','S000000084','10:30:000','01:30:000','2019','2','Finallized');

INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000085','2019-07-08','Grade 12','S000000085','08:00:000','10:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000086','2019-07-08','Grade 12','S000000086','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000087','2019-07-09','Grade 12','S000000087','08:00:000','10:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000088','2019-07-09','Grade 12','S000000088','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000089','2019-07-10','Grade 12','S000000089','08:00:000','10:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000090','2019-07-10','Grade 12','S000000090','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000091','2019-07-11','Grade 12','S000000091','08:00:000','10:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000092','2019-07-11','Grade 12','S000000092','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000093','2019-07-12','Grade 12','S000000093','08:00:000','10:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000094','2019-07-12','Grade 12','S000000094','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000095','2019-07-15','Grade 12','S000000095','08:00:000','10:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000096','2019-07-15','Grade 12','S000000096','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000097','2019-07-16','Grade 12','S000000097','08:00:000','10:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000098','2019-07-16','Grade 12','S000000098','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000099','2019-07-17','Grade 12','S000000099','08:00:000','10:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000100','2019-07-17','Grade 12','S000000100','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000101','2019-07-18','Grade 12','S000000101','08:00:000','10:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000102','2019-07-18','Grade 12','S000000102','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000103','2019-07-19','Grade 12','S000000103','08:00:000','10:00:000','2019','2','Finallized');

INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000104','2019-07-08','Grade 13','S000000104','08:00:000','10:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000105','2019-07-08','Grade 13','S000000105','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000106','2019-07-09','Grade 13','S000000106','08:00:000','10:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000107','2019-07-09','Grade 13','S000000107','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000108','2019-07-10','Grade 13','S000000108','08:00:000','10:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000109','2019-07-10','Grade 13','S000000109','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000110','2019-07-11','Grade 13','S000000110','08:00:000','10:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000111','2019-07-11','Grade 13','S000000111','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000112','2019-07-12','Grade 13','S000000112','08:00:000','10:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000113','2019-07-12','Grade 13','S000000113','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000114','2019-07-15','Grade 13','S000000114','08:00:000','10:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000115','2019-07-15','Grade 13','S000000115','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000116','2019-07-16','Grade 13','S000000116','08:00:000','10:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000117','2019-07-16','Grade 13','S000000117','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000118','2019-07-17','Grade 13','S000000118','08:00:000','10:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000119','2019-07-17','Grade 13','S000000119','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000120','2019-07-18','Grade 13','S000000120','08:00:000','10:00:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000121','2019-07-18','Grade 13','S000000121','10:30:000','01:30:000','2019','2','Finallized');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000122','2019-07-19','Grade 13','S000000122','08:00:000','10:00:000','2019','2','Finallized');

------------------------------------------------------ 3 Term ---------------------------------------------------

INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000123','2019-11-11','Grade 6','S000000001','08:30:000','10:30:000','2019','3','Ahead');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000124','2019-11-11','Grade 6','S000000002','11:00:000','01:00:000','2019','3','Ahead');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000125','2019-11-12','Grade 6','S000000003','08:30:000','10:30:000','2019','3','Ahead');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000126','2019-11-12','Grade 6','S000000004','11:00:000','01:00:000','2019','3','Ahead');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000127','2019-11-13','Grade 6','S000000005','08:30:000','10:30:000','2019','3','Ahead');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000128','2019-11-13','Grade 6','S000000006','11:00:000','01:00:000','2019','3','Ahead');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000129','2019-11-14','Grade 6','S000000007','08:30:000','10:30:000','2019','3','Ahead');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000130','2019-11-14','Grade 6','S000000008','11:00:000','01:00:000','2019','3','Ahead');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000131','2019-11-15','Grade 6','S000000009','08:30:000','10:30:000','2019','3','Ahead');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000132','2019-11-15','Grade 6','S000000010','11:00:000','01:00:000','2019','3','Ahead');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000133','2019-11-18','Grade 6','S000000011','08:30:000','10:30:000','2019','3','Ahead');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000134','2019-11-18','Grade 6','S000000012','11:00:000','01:00:000','2019','3','Ahead');

INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000135','2019-11-11','Grade 7','S000000013','08:30:000','10:30:000','2019','3','Ahead');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000136','2019-11-11','Grade 7','S000000014','11:00:000','01:00:000','2019','3','Ahead');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000137','2019-11-12','Grade 7','S000000015','08:30:000','10:30:000','2019','3','Ahead');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000138','2019-11-12','Grade 7','S000000016','11:00:000','01:00:000','2019','3','Ahead');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000139','2019-11-13','Grade 7','S000000017','08:30:000','10:30:000','2019','3','Ahead');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000140','2019-11-13','Grade 7','S000000018','11:00:000','01:00:000','2019','3','Ahead');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000141','2019-11-14','Grade 7','S000000019','08:30:000','10:30:000','2019','3','Ahead');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000142','2019-11-14','Grade 7','S000000020','11:00:000','01:00:000','2019','3','Ahead');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000143','2019-11-15','Grade 7','S000000021','08:30:000','10:30:000','2019','3','Ahead');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000144','2019-11-15','Grade 7','S000000022','11:00:000','01:00:000','2019','3','Ahead');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000145','2019-11-18','Grade 7','S000000023','08:30:000','10:30:000','2019','3','Ahead');
INSERT INTO Exam(`examID`,`examDate`,`grade`,`subjectID`,`startTime`,`endTime`,`year`,`term`,`status`)
VALUES('E000000146','2019-11-18','Grade 7','S000000024','11:00:000','01:00:000','2019','3','Ahead');


--StudentFaceExams
--Grade 6 Class A Sinhala Term 2
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000041','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000042','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000043','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000044','S000000001','E000000001','60');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000045','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000046','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000047','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000048','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000049','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000050','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000051','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000052','S000000001','E000000001','22');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000053','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000054','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000055','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000056','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000057','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000058','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000059','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000060','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000061','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000062','S000000001','E000000001','82');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000063','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000064','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000065','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000066','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000067','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000068','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000069','S000000001','E000000001','85');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000070','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000071','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000072','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000073','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000074','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000075','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000076','S000000001','E000000001','13');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000077','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000078','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000079','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000080','S000000001','E000000001','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000081','S000000001','E000000001','80');

--Grade 6 Class A Sinhala Term 2
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000041','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000042','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000043','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000044','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000045','S000000002','E000000002','24');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000046','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000047','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000048','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000049','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000050','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000051','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000052','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000053','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000054','S000000002','E000000002','85');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000055','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000056','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000057','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000058','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000059','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000060','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000061','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000062','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000063','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000064','S000000002','E000000002','25');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000065','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000066','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000067','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000068','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000069','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000070','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000071','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000072','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000073','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000074','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000075','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000076','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000077','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000078','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000079','S000000002','E000000002','65');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000080','S000000002','E000000002','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000081','S000000002','E000000002','80');


--Grade 6 Class A Sinhala Term 2
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000041','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000042','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000043','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000044','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000045','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000046','S000000003','E000000003','78');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000047','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000048','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000049','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000050','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000051','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000052','S000000003','E000000003','25');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000053','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000054','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000055','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000056','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000057','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000058','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000059','S000000003','E000000003','85');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000060','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000061','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000062','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000063','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000064','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000065','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000066','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000067','S000000003','E000000003','98');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000068','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000069','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000070','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000071','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000072','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000073','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000074','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000075','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000076','S000000003','E000000003','45');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000077','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000078','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000079','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000080','S000000003','E000000003','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000081','S000000003','E000000003','80');

--Grade 6 Class A Sinhala Term 2
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000041','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000042','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000043','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000044','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000045','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000046','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000047','S000000004','E000000004','45');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000048','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000049','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000050','S000000004','E000000004','45');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000051','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000052','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000053','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000054','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000055','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000056','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000057','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000058','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000059','S000000004','E000000004','65');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000060','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000061','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000062','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000063','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000064','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000065','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000066','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000067','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000068','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000069','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000070','S000000004','E000000004','15');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000071','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000072','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000073','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000074','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000075','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000076','S000000004','E000000004','68');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000077','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000078','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000079','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000080','S000000004','E000000004','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000081','S000000004','E000000004','80');

--Grade 6 Class A Sinhala Term 2
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000041','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000042','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000043','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000044','S000000005','E000000005','78');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000045','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000046','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000047','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000048','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000049','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000050','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000051','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000052','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000053','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000054','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000055','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000056','S000000005','E000000005','54');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000057','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000058','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000059','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000060','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000061','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000062','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000063','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000064','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000065','S000000005','E000000005','52');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000066','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000067','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000068','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000069','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000070','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000071','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000072','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000073','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000074','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000075','S000000005','E000000005','68');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000076','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000077','S000000005','E000000005','12');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000078','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000079','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000080','S000000005','E000000005','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000081','S000000005','E000000005','80');

--Grade 6 Class A Sinhala Term 2
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000041','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000042','S000000006','E000000006','32');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000043','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000044','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000045','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000046','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000047','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000048','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000049','S000000006','E000000006','78');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000050','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000051','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000052','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000053','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000054','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000055','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000056','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000057','S000000006','E000000006','35');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000058','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000059','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000060','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000061','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000062','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000063','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000064','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000065','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000066','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000067','S000000006','E000000006','54');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000068','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000069','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000070','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000071','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000072','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000073','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000074','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000075','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000076','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000077','S000000006','E000000006','45');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000078','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000079','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000080','S000000006','E000000006','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000081','S000000006','E000000006','80');

--Grade 6 Class A Sinhala Term 2
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000041','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000042','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000043','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000044','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000045','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000046','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000047','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000048','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000049','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000050','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000051','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000052','S000000007','E000000007','46');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000053','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000054','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000055','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000056','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000057','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000058','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000059','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000060','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000061','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000062','S000000007','E000000007','46');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000063','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000064','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000065','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000066','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000067','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000068','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000069','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000070','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000071','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000072','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000073','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000074','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000075','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000076','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000077','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000078','S000000007','E000000007','65');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000079','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000080','S000000007','E000000007','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000081','S000000007','E000000007','80');

INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000041','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000042','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000043','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000044','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000045','S000000008','E000000008','45');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000046','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000047','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000048','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000049','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000050','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000051','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000052','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000053','S000000008','E000000008','54');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000054','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000055','S000000008','E000000008','45');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000056','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000057','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000058','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000059','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000060','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000061','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000062','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000063','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000064','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000065','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000066','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000067','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000068','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000069','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000070','S000000008','E000000008','57');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000071','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000072','S000000008','E000000008','46');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000073','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000074','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000075','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000076','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000077','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000078','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000079','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000080','S000000008','E000000008','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000081','S000000008','E000000008','80');

INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000041','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000042','S000000009','E000000009','54');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000043','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000044','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000045','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000046','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000047','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000048','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000049','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000050','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000051','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000052','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000053','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000054','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000055','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000056','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000057','S000000009','E000000009','65');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000058','S000000009','E000000009','78');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000059','S000000009','E000000009','67');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000060','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000061','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000062','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000063','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000064','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000065','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000066','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000067','S000000009','E000000009','12');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000068','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000069','S000000009','E000000009','21');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000070','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000071','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000072','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000073','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000074','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000075','S000000009','E000000009','54');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000076','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000077','S000000009','E000000009','45');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000078','S000000009','E000000009','12');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000079','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000080','S000000009','E000000009','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000081','S000000009','E000000009','80');

INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000041','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000042','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000043','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000044','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000045','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000046','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000047','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000048','S000000010','E000000010','75');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000049','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000050','S000000010','E000000010','12');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000051','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000052','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000053','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000054','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000055','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000056','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000057','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000058','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000059','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000060','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000061','S000000010','E000000010','72');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000062','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000063','S000000010','E000000010','72');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000064','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000065','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000066','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000067','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000068','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000069','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000070','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000071','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000072','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000073','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000074','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000075','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000076','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000077','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000078','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000079','S000000010','E000000010','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000080','S000000010','E000000010','72');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000081','S000000010','E000000010','80');

INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000041','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000042','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000043','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000044','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000045','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000046','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000047','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000048','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000049','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000050','S000000011','E000000011','31');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000051','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000052','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000053','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000054','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000055','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000056','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000057','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000058','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000059','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000060','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000061','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000062','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000063','S000000011','E000000011','53');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000064','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000065','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000066','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000067','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000068','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000069','S000000011','E000000011','78');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000070','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000071','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000072','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000073','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000074','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000075','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000076','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000077','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000078','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000079','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000080','S000000011','E000000011','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000081','S000000011','E000000011','80');

INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000041','S000000012','E000000012','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000042','S000000012','E000000012','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000043','S000000012','E000000012','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000044','S000000012','E000000012','53');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000045','S000000012','E000000012','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000046','S000000012','E000000012','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000047','S000000012','E000000012','76');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000048','S000000012','E000000012','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000049','S000000012','E000000012','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000050','S000000012','E000000012','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000051','S000000012','E000000012','76');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000052','S000000012','E000000012','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000053','S000000012','E000000012','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000054','S000000012','E000000012','21');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000055','S000000012','E000000012','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000056','S000000012','E000000012','45');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000057','S000000012','E000000012','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000058','S000000012','E000000012','75');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000059','S000000012','E000000012','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000060','S000000012','E000000012','20');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000061','S000000012','E000000012','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000062','S000000012','E000000012','64');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000063','S000000012','E000000012','58');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000064','S000000012','E000000012','12');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000065','S000000012','E000000012','73');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000066','S000000012','E000000012','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000067','S000000012','E000000012','75');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000068','S000000012','E000000012','24');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000069','S000000012','E000000012','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000070','S000000012','E000000012','75');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000071','S000000012','E000000012','46');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000072','S000000012','E000000012','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000073','S000000012','E000000012','56');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000074','S000000012','E000000012','58');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000075','S000000012','E000000012','45');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000076','S000000012','E000000012','65');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000077','S000000012','E000000012','98');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000078','S000000012','E000000012','80');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000079','S000000012','E000000012','30');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000080','S000000012','E000000012','75');
INSERT INTO StudentFaceExams(`userID`,`subjectID`,`examID`,`result`)
VALUES('U000000081','S000000012','E000000012','80');

--ItemCategories
INSERT INTO ItemCategories(`categoryID`,`category`) 
VALUES('C000000001','Stationery');
INSERT INTO ItemCategories(`categoryID`,`category`) 
VALUES('C000000002','Home Appliences');
INSERT INTO ItemCategories(`categoryID`,`category`) 
VALUES('C000000003','Chemicals');
INSERT INTO ItemCategories(`categoryID`,`category`) 
VALUES('C000000004','Electronics');
INSERT INTO ItemCategories(`categoryID`,`category`) 
VALUES('C000000005','Furniture Academic');
INSERT INTO ItemCategories(`categoryID`,`category`) 
VALUES('C000000006','Furniture NonAcademic');
INSERT INTO ItemCategories(`categoryID`,`category`) 
VALUES('C000000007','Chemicals');
INSERT INTO ItemCategories(`categoryID`,`category`) 
VALUES('C000000008','Musics');
INSERT INTO ItemCategories(`categoryID`,`category`) 
VALUES('C000000009','Sports');
INSERT INTO ItemCategories(`categoryID`,`category`) 
VALUES('C000000010','Cleaning');
INSERT INTO ItemCategories(`categoryID`,`category`) 
VALUES('C000000011','Materials');


--ItemUnits
INSERT INTO ItemUnits(`unitID`,`unit`) 
VALUES('T000000001','units');
INSERT INTO ItemUnits(`unitID`,`unit`) 
VALUES('T000000002','kilograms');
INSERT INTO ItemUnits(`unitID`,`unit`) 
VALUES('T000000003','grams');
INSERT INTO ItemUnits(`unitID`,`unit`) 
VALUES('T000000004','pounds');
INSERT INTO ItemUnits(`unitID`,`unit`) 
VALUES('T000000005','litres');
INSERT INTO ItemUnits(`unitID`,`unit`) 
VALUES('T000000006','millilitres');
INSERT INTO ItemUnits(`unitID`,`unit`) 
VALUES('T000000007','metres');
INSERT INTO ItemUnits(`unitID`,`unit`) 
VALUES('T000000008','centimetres');
INSERT INTO ItemUnits(`unitID`,`unit`) 
VALUES('T000000009','feet');
INSERT INTO ItemUnits(`unitID`,`unit`) 
VALUES('T000000010','inches');

--BookCategories
INSERT INTO BookCategories(`bcategoryID`,`bcategory`) 
VALUES('K000000001','Sci-Fi');
INSERT INTO BookCategories(`bcategoryID`,`bcategory`) 
VALUES('K000000002','Adventure');
INSERT INTO BookCategories(`bcategoryID`,`bcategory`) 
VALUES('K000000003','Educational');
INSERT INTO BookCategories(`bcategoryID`,`bcategory`) 
VALUES('K000000004','Literature');
INSERT INTO BookCategories(`bcategoryID`,`bcategory`) 
VALUES('K000000005','Mathematical');
INSERT INTO BookCategories(`bcategoryID`,`bcategory`) 
VALUES('K000000006','Logic');
INSERT INTO BookCategories(`bcategoryID`,`bcategory`) 
VALUES('K000000007','Economics');
INSERT INTO BookCategories(`bcategoryID`,`bcategory`) 
VALUES('K000000008','Politics');
INSERT INTO BookCategories(`bcategoryID`,`bcategory`) 
VALUES('K000000009','Geography');
INSERT INTO BookCategories(`bcategoryID`,`bcategory`) 
VALUES('K000000010','Historical');
INSERT INTO BookCategories(`bcategoryID`,`bcategory`) 
VALUES('K000000011','ComputerScience');
INSERT INTO BookCategories(`bcategoryID`,`bcategory`) 
VALUES('K000000012','Art&Music');
INSERT INTO BookCategories(`bcategoryID`,`bcategory`) 
VALUES('K000000013','Accounting');
INSERT INTO BookCategories(`bcategoryID`,`bcategory`) 
VALUES('K000000014','Agriculture');
INSERT INTO BookCategories(`bcategoryID`,`bcategory`) 
VALUES('K000000015','Health');

--Attendance
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-08-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-08-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-08-01',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-08-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-08-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-08-01',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-08-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-08-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-08-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-08-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-08-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-08-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-08-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-08-01',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-08-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-08-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-08-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-08-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-08-01',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-08-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-08-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-08-01',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-08-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-08-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-08-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-08-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-08-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-08-01',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-08-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-08-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-08-01',1);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-08-02',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-08-02',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-08-02',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-08-02',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-08-02',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-08-02',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-08-02',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-08-02',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-08-02',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-08-02',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-08-02',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-08-02',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-08-02',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-08-02',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-08-02',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-08-02',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-08-02',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-08-02',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-08-02',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-08-02',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-08-02',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-08-02',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-08-02',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-08-02',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-08-02',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-08-02',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-08-02',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-08-02',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-08-02',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-08-02',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-08-02',1);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-08-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-08-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-08-05',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-08-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-08-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-08-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-08-05',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-08-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-08-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-08-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-08-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-08-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-08-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-08-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-08-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-08-05',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-08-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-08-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-08-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-08-05',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-08-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-08-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-08-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-08-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-08-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-08-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-08-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-08-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-08-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-08-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-08-05',0);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-08-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-08-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-08-06',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-08-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-08-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-08-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-08-06',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-08-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-08-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-08-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-08-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-08-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-08-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-08-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-08-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-08-06',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-08-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-08-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-08-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-08-06',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-08-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-08-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-08-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-08-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-08-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-08-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-08-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-08-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-08-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-08-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-08-06',0);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-08-07',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-08-07',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-08-07',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-08-07',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-08-07',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-08-07',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-08-07',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-08-07',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-08-07',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-08-07',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-08-07',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-08-07',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-08-07',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-08-07',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-08-07',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-08-07',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-08-07',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-08-07',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-08-07',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-08-07',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-08-07',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-08-07',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-08-07',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-08-07',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-08-07',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-08-07',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-08-07',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-08-07',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-08-07',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-08-07',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-08-07',0);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-08-08',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-08-08',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-08-08',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-08-08',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-08-08',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-08-08',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-08-08',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-08-08',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-08-08',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-08-08',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-08-08',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-08-08',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-08-08',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-08-08',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-08-08',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-08-08',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-08-08',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-08-08',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-08-08',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-08-08',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-08-08',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-08-08',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-08-08',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-08-08',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-08-08',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-08-08',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-08-08',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-08-08',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-08-08',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-08-08',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-08-08',0);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-08-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-08-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-08-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-08-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-08-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-08-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-08-09',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-08-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-08-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-08-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-08-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-08-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-08-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-08-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-08-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-08-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-08-09',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-08-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-08-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-08-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-08-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-08-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-08-09',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-08-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-08-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-08-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-08-09',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-08-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-08-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-08-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-08-09',1);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-08-12',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-08-12',0);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-08-13',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-08-13',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-08-13',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-08-13',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-08-13',1);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-08-14',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-08-14',0);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-08-15',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-08-15',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-08-15',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-08-15',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-08-15',1);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-08-16',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-08-16',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-08-16',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-08-16',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-08-16',1);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-08-19',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-08-19',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-08-19',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-08-19',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-08-19',1);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-08-20',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-08-20',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-08-20',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-08-20',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-08-20',1);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-08-21',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-08-21',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-08-21',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-08-21',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-08-21',1);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-08-22',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-08-22',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-08-22',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-08-22',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-08-22',1);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-08-23',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-08-23',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-08-23',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-08-23',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-08-23',1);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-08-26',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-08-26',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-08-26',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-08-26',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-08-26',1);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-08-27',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-08-27',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-08-27',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-08-27',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-08-27',1);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-08-28',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-08-28',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-08-28',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-08-28',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-08-28',1);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-08-29',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-08-29',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-08-29',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-08-29',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-08-29',1);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-08-30',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-08-30',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-08-30',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-08-30',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-08-30',1);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-09-01',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-09-01',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-09-01',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-09-01',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-09-01',1);

INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-09-02',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-09-02',0);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-09-02',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-09-02',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-09-02',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-09-02',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-09-02',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-09-02',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-09-02',0);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-09-02',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-09-02',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-09-02',0);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-09-02',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-09-02',0);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-09-02',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-09-02',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-09-02',0);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-09-02',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-09-02',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-09-02',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-09-02',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-09-02',0);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-09-02',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-09-02',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-09-02',0);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-09-02',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-09-02',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-09-02',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-09-02',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-09-02',0);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-09-02',0);

INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-09-03',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-09-03',0);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-09-03',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-09-03',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-09-03',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-09-03',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-09-03',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-09-03',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-09-03',0);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-09-03',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-09-03',0);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-09-03',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-09-03',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-09-03',0);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-09-03',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-09-03',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-09-03',0);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-09-03',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-09-03',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-09-03',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-09-03',0);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-09-03',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-09-03',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-09-03',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-09-03',0);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-09-03',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-09-03',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-09-03',1);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-09-03',0);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-09-03',0);
INSERT INTO Attendance(`UserID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-09-03',0);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-09-04',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-09-04',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-09-04',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-09-04',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-09-04',1);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-09-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-09-05',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-09-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-09-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-09-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-09-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-09-05',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-09-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-09-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-09-05',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-09-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-09-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-09-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-09-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-09-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-09-05',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-09-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-09-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-09-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-09-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-09-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-09-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-09-05',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-09-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-09-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-09-05',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-09-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-09-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-09-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-09-05',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-09-05',0);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-09-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-09-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-09-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-09-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-09-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-09-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-09-06',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-09-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-09-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-09-06',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-09-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-09-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-09-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-09-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-09-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-09-06',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-09-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-09-06',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-09-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-09-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-09-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-09-06',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-09-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-09-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-09-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-09-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-09-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-09-06',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-09-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-09-06',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-09-06',0);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-09-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-09-09',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-09-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-09-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-09-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-09-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-09-09',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-09-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-09-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-09-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-09-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-09-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-09-09',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-09-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-09-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-09-09',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-09-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-09-09',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-09-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-09-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-09-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-09-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-09-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-09-09',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-09-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-09-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-09-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-09-09',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-09-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-09-09',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-09-09',1);

INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000041','2019-09-10',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000042','2019-09-10',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000043','2019-09-10',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000044','2019-09-10',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000045','2019-09-10',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000046','2019-09-10',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000047','2019-09-10',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000048','2019-09-10',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000049','2019-09-10',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000050','2019-09-10',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000051','2019-09-10',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000052','2019-09-10',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000053','2019-09-10',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000054','2019-09-10',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000055','2019-09-10',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000056','2019-09-10',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000057','2019-09-10',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000058','2019-09-10',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000059','2019-09-10',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000060','2019-09-10',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000061','2019-09-10',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000062','2019-09-10',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000063','2019-09-10',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000064','2019-09-10',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000065','2019-09-10',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000066','2019-09-10',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000067','2019-09-10',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000068','2019-09-10',0);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000069','2019-09-10',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000070','2019-09-10',1);
INSERT INTO Attendance(`userID`,`date`,`presentAbsent`)
VALUES('U000000071','2019-09-10',1);

--DROP TABLES--
DROP TABLE Attendance;
DROP TABLE BookCategories;
DROP TABLE ItemUnits;
DROP TABLE ItemCategories;
DROP TABLE StudentYear;
DROP TABLE StudentFaceExams;
DROP TABLE Exam;
DROP TABLE AcademicTeachSubjects;
DROP TABLE StudentHasSubjects;
DROP TABLE Subject;
DROP TABLE BookHasCopies;
DROP TABLE Book;
DROP TABLE Item;
DROP TABLE Student;
DROP TABLE NonAcademicStaff;
DROP TABLE AcademicStaff;
DROP TABLE User;

--SELECT Commands--
SELECT * FROM User;
SELECT * FROM AcademicStaff;
SELECT * FROM NonAcademicStaff;
SELECT * FROM Student;
SELECT * FROM Item;
SELECT * FROM Book;
SELECT * FROM BookHasCopies;
SELECT * FROM Subject;
SELECT * FROM StudentHasSubjects;
SELECT * FROM AcademicTeachSubjects;
SELECT * FROM Exam;
SELECT * FROM StudentFaceExams;
SELECT * FROM StudentYear;
SELECT * FROM ItemCategories;
SELECT * FROM ItemUnits;
SELECT * FROM BookCategories;
SELECT * FROM Attendance;
