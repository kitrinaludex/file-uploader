DROP TABLE IF EXISTS users,files,folders;

CREATE TABLE users(
id SERIAL PRIMARY KEY,
username TEXT,
password TEXT);

CREATE TABLE files(
uuid TEXT PRIMARY KEY,
name TEXT,
owner TEXT,
folder TEXT);

CREATE TABLE folders(
uuid TEXT PRIMARY KEY,
name TEXT,
owner TEXT,
parent_uuid TEXT);