DROP TABLE IF EXISTS users,files;

CREATE TABLE users(
id SERIAL PRIMARY KEY,
username TEXT,
password TEXT);

CREATE TABLE files(
uuid TEXT PRIMARY KEY,
name TEXT,
owner TEXT,
folder TEXT);