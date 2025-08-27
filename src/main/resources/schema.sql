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
path TEXT);

CREATE TABLE folders(
uuid TEXT PRIMARY KEY,
name TEXT,
owner TEXT,
parent_uuid TEXT,
path TEXT);

CREATE TABLE folder_permissions(
id SERIAL PRIMARY KEY,
username TEXT,
folder_uuid TEXT);

CREATE TABLE invite_links(
token TEXT PRIMARY KEY,
created_by TEXT,
folder_uuid TEXT,
permission_level TEXT,
active BOOLEAN);