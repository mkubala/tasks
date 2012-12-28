# Tasks schema
 
# --- !Ups

ALTER TABLE task ADD COLUMN done boolean;
 
# --- !Downs
 
DROP TABLE task DROP COLUMN done RESTRICT;
