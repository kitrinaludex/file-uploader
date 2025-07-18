DROP TABLE IF EXISTS users,files,folders;

CREATE TABLE users(
id SERIAL PRIMARY KEY,
username TEXT,
password TEXT,
root_folder_uuid TEXT);

CREATE TABLE files(
uuid TEXT PRIMARY KEY,
name TEXT,
owner TEXT,
folder TEXT,
path TEXT);

CREATE TABLE folders(
uuid TEXT PRIMARY KEY,
name TEXT,
owner TEXT,
parent_uuid TEXT,
path TEXT);