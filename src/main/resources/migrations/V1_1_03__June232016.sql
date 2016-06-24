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

INSERT INTO `sourcelead`.`STUDENT` (`id`, `studentName`, `standard`) VALUES ('1', 'Balaji', '10th');
INSERT INTO `sourcelead`.`STUDENT` (`id`, `studentName`, `standard`) VALUES ('2', 'Goutham', '8th');

INSERT INTO `sourcelead`.`SUBJECTS` (`id`, `studentId`, `subjectName`, `marks`) VALUES ('1', '1', 'English', '80');
INSERT INTO `sourcelead`.`SUBJECTS` (`id`, `studentId`, `subjectName`, `marks`) VALUES ('2', '1', 'Maths', '90');
INSERT INTO `sourcelead`.`SUBJECTS` (`id`, `studentId`, `subjectName`, `marks`) VALUES ('3', '2', 'Science', '75');
INSERT INTO `sourcelead`.`SUBJECTS` (`id`, `studentId`, `subjectName`, `marks`) VALUES ('4', '2', 'Social', '85');
