-- Init the DB

SET statement_timeout=0;
SET client_encoding='UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET default_tablespace = '';

SET default_with_oids = false;

-- CREATE TABLES

CREATE TABLE card_set (
    id bigserial NOT NULL,
    code varchar NOT NULL,
    created_ts timestamp,
    updated_ts timestamp,
    CONSTRAINT pk_card_set_id PRIMARY KEY (id)
);

CREATE TABLE card (
    id bigserial NOT NULL,
    code varchar NOT NULL,
    name varchar NOT NULL,
    art_url varchar,
    card_type varchar,
    card_set_id integer NOT NULL,
    created_ts timestamp,
    updated_ts timestamp,
    CONSTRAINT pk_card_id PRIMARY KEY (id),
    CONSTRAINT fk_card_set_id FOREIGN KEY (card_set_id) REFERENCES card_set(id)
);

CREATE TABLE card_effect (
    id bigserial NOT NULL,
    effect_type varchar NOT NULL,
    effect_text varchar NOT NULL,
    card_id integer NOT NULL,
    created_ts timestamp,
    updated_ts timestamp,
    CONSTRAINT pk_card_effect_id PRIMARY KEY (id),
    CONSTRAINT fk_card_id FOREIGN KEY (card_id) REFERENCES card(id)
);

