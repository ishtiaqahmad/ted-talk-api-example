INSERT INTO ted_talk(TITLE,AUTHOR,DATE,VIEWS,LIKES,LINK)
SELECT "TITLE","AUTHOR","DATE","VIEWS","LIKES","LINK"
--CAST("ID" as bigint),title,author,CAST("DATE" as varchar(255)),CAST(views as bigint),CAST(likes as bigint),link
FROM CSVREAD('classpath:data.csv' ,null);
