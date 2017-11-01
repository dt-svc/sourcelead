DELETE FROM `sourcelead`.`ACTION_NAMES` WHERE `ID`='111';
DELETE FROM `sourcelead`.`ACTION_NAMES` WHERE `ID`='98';
DELETE FROM `sourcelead`.`ACTION_NAMES` WHERE `ID`='217';

INSERT INTO `sourcelead`.`ACTION_NAMES` (`ID`, `ACTION_NAME`, `METHOD_NAME`) VALUES ('111', 'Associate Details Updated', 'updateCandidate');
set SQL_SAFE_UPDATES=0;
UPDATE `sourcelead`.`ACTION_NAMES` SET `ACTION_NAME`='Remove Associate', `METHOD_NAME`='deleteCandidateByCandidateId' WHERE `ID`='219';
UPDATE `sourcelead`.`ACTION_NAMES` SET `ACTION_NAME`='Add Job' WHERE `ID`='52';
set SQL_SAFE_UPDATES=1;
