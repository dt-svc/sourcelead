set sql_safe_updates=0;
UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Opening Job position for  {{ShareRecomendedApplicant.jobName}}' WHERE `UNIQUE_SCENARIO_NAME`='Share Recomended Applicant';
set sql_safe_updates=1;