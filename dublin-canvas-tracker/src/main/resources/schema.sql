CREATE DATABASE IF NOT EXISTS dublin_canvas_db;
USE dublin_canvas_db;

CREATE TABLE IF NOT EXISTS users (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    username  VARCHAR(50)  NOT NULL UNIQUE,
    email     VARCHAR(150) NOT NULL UNIQUE,
    password  VARCHAR(255) NOT NULL,
    role      VARCHAR(20)  NOT NULL DEFAULT 'ROLE_USER'
);

CREATE TABLE IF NOT EXISTS artworks (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    council     VARCHAR(150) NOT NULL,
    artist      VARCHAR(150) NOT NULL,
    title       VARCHAR(255) NOT NULL,
    location    VARCHAR(255) NOT NULL,
    area        VARCHAR(100),
    year        INT,
    status      VARCHAR(50),
    website     VARCHAR(500),
    latitude    DECIMAL(10,7),
    longitude   DECIMAL(10,7)
);
