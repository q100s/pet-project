-- liquibase formatted sql

-- changeset q100s:1
CREATE TABLE ad (
    id BIGINT PRIMARY KEY,
    price INT,
    title VARCHAR,
    decription VARCHAR,
    image_url VARCHAR,
    author_id INT
);

CREATE TABLE comments (
    id BIGINT PRIMARY KEY,
    price INT,
    text VARCHAR,
    created_at INT,
    author_id INT,
    ad_id INT
);

CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    email VARCHAR,
    password VARCHAR,
    first_name VARCHAR,
    last_name VARCHAR,
    phone_number VARCHAR,
    role VARCHAR, --enum Role
    image_url VARCHAR
);