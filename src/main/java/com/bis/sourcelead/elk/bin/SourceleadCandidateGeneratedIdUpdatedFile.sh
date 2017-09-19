cd /home/Sourcelead_Db_Scripts
PROPERTYLOCATION="-DpropertyLocation=file:/home/properties/app.properties"
export PROPERTYLOCATION 
java  $PROPERTYLOCATION -classpath /home/Sourcelead_Db_Scripts:/home/Sourcelead_Db_Scripts/lib/*  com.bis.sourcelead.elk.install.SourceleadCandidateGeneratedIdUpdatedFile
