
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{ShareRecomendedApplicant.candidateName}},\n\n  We have an excellent opportunity that matches your skills. Please find the details below.\n\n  Job Name : {{ShareRecomendedApplicant.jobName}} \n\n  Job Location : {{ShareRecomendedApplicant.jobLocation}} \n\n  Experience : {{ShareRecomendedApplicant.jobExperience}} \r \r  Rate : {{ShareRecomendedApplicant.jobRate}} \n\n  Job Description : {{ShareRecomendedApplicant.jobDescription}} \n\n   {{ShareRecomendedApplicant.viewJobLink}} \n ' WHERE `ID`='314';

CALL AlterTableAddColumn ('ASSET','ISSUE_DATE','DATE NULL AFTER `DESCRIPTION`');

