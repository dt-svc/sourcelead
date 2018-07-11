
SET SQL_SAFE_UPDATES = 0;
update SUBMISSION_TEMPLATE st  set JSON_STRING = '[{"type":"textarea","label":"Comment","req":1},{"type":"radio","label":"Selected for Interview","req":1,"choices":[{"label":"Yes","sel":0},{"label":"No","sel":0}]},{"type":"select","label":"Rating","req":1,"choices":[{"label":"Inadequate","sel":0},{"label":"Adequate","sel":0},{"label":"Good","sel":0},{"label":"Excellent","sel":0},{"label":"Outstanding","sel":0}]}]' where st.IS_DEFAULT = 'Y' and st.TEMPLATE_TYPE = 'Feedback';   
SET SQL_SAFE_UPDATES = 1;