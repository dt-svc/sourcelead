CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE','TEAM_ID','BIGINT NULL');
ALTER TABLE `sourcelead`.`CANDIDATE` ADD FOREIGN KEY FK1_CANDIDATE_TEAM (TEAM_ID) REFERENCES `sourcelead`.TEAM(ID);

CALL sourcelead.AlterTableAddColumn ('sourcelead','CANDIDATE','IS_FAVOURITE','INT NULL AFTER `TEAM_ID`');