
CALL sourcelead.AlterTableAddColumn ('sourcelead','countries','currency','VARCHAR(45) NULL AFTER `Code2`');

UPDATE `sourcelead`.`countries` SET `currency`='AUD' WHERE `ID`='15';
UPDATE `sourcelead`.`countries` SET `currency`='INR' WHERE `ID`='100';
UPDATE `sourcelead`.`countries` SET `currency`='USD' WHERE `ID`='224';
UPDATE `sourcelead`.`countries` SET `currency`='GBP' WHERE `ID`='77';
UPDATE `sourcelead`.`countries` SET `currency`='SGD' WHERE `ID`='187';
UPDATE `sourcelead`.`countries` SET `currency`='EUR' WHERE `ID`='73';

