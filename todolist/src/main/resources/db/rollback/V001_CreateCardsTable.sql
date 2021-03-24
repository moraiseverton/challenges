DROP TABLE IF EXISTS cards;

DELETE FROM "flyway_schema_history" AS "flyway"
 WHERE "flyway"."script" = 'V001__CreateCardsTable.sql';