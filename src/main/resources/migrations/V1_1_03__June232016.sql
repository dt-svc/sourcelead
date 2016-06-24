DROP TABLE IF EXISTS `sourcelead`.`STUDENT`;
 CREATE TABLE sourcelead.STUDENT(
      id INT UNSIGNED NOT NULL AUTO_INCREMENT ,
	  studentName VARCHAR(50)  NULL ,
	  standard VARCHAR(20) NULL ,
	  PRIMARY KEY (id),
	  INDEX(`id`) 
  );


DROP TABLE IF EXISTS `sourcelead`.`SUBJECTS`;
CREATE TABLE `sourcelead`.`SUBJECTS`(
  id INT NOT NULL AUTO_INCREMENT, 
  studentId INT UNSIGNED,
  subjectName VARCHAR(30) NULL ,
  marks INT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY(studentId) REFERENCES `sourcelead`.`STUDENT`(id),
  INDEX(`id`)  
);


DROP TABLE IF EXISTS `sourcelead`.`COURSES`;
CREATE TABLE `sourcelead`.`COURSES`(
  id INT UNSIGNED NOT NULL AUTO_INCREMENT, 
  subjectId INT ,
  courseName VARCHAR(30) NULL ,
  duration VARCHAR(20) NULL ,
  PRIMARY KEY (id),
  FOREIGN KEY(subjectId) REFERENCES `sourcelead`.`SUBJECTS`(id),
  INDEX(`id`)  
);


INSERT INTO `sourcelead`.`STUDENT` (`id`, `studentName`, `standard`) VALUES ('1', 'Balaji', '10th');
INSERT INTO `sourcelead`.`STUDENT` (`id`, `studentName`, `standard`) VALUES ('2', 'Goutham', '8th');

INSERT INTO `sourcelead`.`SUBJECTS` (`id`, `studentId`, `subjectName`, `marks`) VALUES ('1', '1', 'English', '80');
INSERT INTO `sourcelead`.`SUBJECTS` (`id`, `studentId`, `subjectName`, `marks`) VALUES ('2', '1', 'Maths', '90');
INSERT INTO `sourcelead`.`SUBJECTS` (`id`, `studentId`, `subjectName`, `marks`) VALUES ('3', '2', 'Science', '75');
INSERT INTO `sourcelead`.`SUBJECTS` (`id`, `studentId`, `subjectName`, `marks`) VALUES ('4', '2', 'Social', '85');



INSERT INTO `sourcelead`.`COURSES` (`id`,`subjectId`,`courseName`,`duration`) VALUES (1,1,'Main Course','1 Year');
INSERT INTO `sourcelead`.`COURSES` (`id`,`subjectId`,`courseName`,`duration`) VALUES (2,1,'Non Detail','6 Months');
INSERT INTO `sourcelead`.`COURSES` (`id`,`subjectId`,`courseName`,`duration`) VALUES (3,2,'Maths 1','6 Months');
INSERT INTO `sourcelead`.`COURSES` (`id`,`subjectId`,`courseName`,`duration`) VALUES (4,2,'Maths 2','6 Months');
INSERT INTO `sourcelead`.`COURSES` (`id`,`subjectId`,`courseName`,`duration`) VALUES (5,3,'Physics','6 Months');
INSERT INTO `sourcelead`.`COURSES` (`id`,`subjectId`,`courseName`,`duration`) VALUES (6,3,'Chemistry','6 Months');
INSERT INTO `sourcelead`.`COURSES` (`id`,`subjectId`,`courseName`,`duration`) VALUES (7,4,'Geography','6 Months');
INSERT INTO `sourcelead`.`COURSES` (`id`,`subjectId`,`courseName`,`duration`) VALUES (8,4,'History','6 Months');
