DROP TABLE IF EXISTS users,files,folders,folder_permissions,invite_links;

CREATE TABLE users(
uuid TEXT PRIMARY KEY,
username TEXT,
password TEXT,
root_folder_uuid TEXT);

CREATE TABLE files(
uuid TEXT PRIMARY KEY,
name TEXT,
owner TEXT,
folder TEXT,
path TEXT,
file_size DECIMAL,
creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
edit_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP);

CREATE TABLE folders(
uuid TEXT PRIMARY KEY,
name TEXT,
owner TEXT,
parent_uuid TEXT,
path TEXT,
creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
edit_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP);

CREATE TABLE folder_permissions(
id SERIAL PRIMARY KEY,
username TEXT,
folder_uuid TEXT);

CREATE TABLE invite_links(
token TEXT PRIMARY KEY,
created_by TEXT,
folder_uuid TEXT);