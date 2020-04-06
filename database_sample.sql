-- Database: parking

-- DROP DATABASE parking;

CREATE DATABASE parking
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

COMMENT ON DATABASE parking
    IS 'ISD Parking db';

-- SCHEMA: parking

-- DROP SCHEMA parking ;

CREATE SCHEMA parking
    AUTHORIZATION postgres;

COMMENT ON SCHEMA parking
    IS 'parking lots schema (standart)';

GRANT ALL ON SCHEMA parking TO parking;

GRANT ALL ON SCHEMA parking TO postgres;


-- Table: parking.parking_lots

-- DROP TABLE parking.parking_lots;

CREATE TABLE parking.parking_lots
(
    id bigint NOT NULL DEFAULT nextval('parking_lots_id_seq'::regclass),
    lot_number integer,
    status integer,
    updated_at timestamp without time zone,
    CONSTRAINT parking_lots_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE parking.parking_lots
    OWNER to postgres;


-- Table: parking.statistics

-- DROP TABLE parking.statistics;

CREATE TABLE parking.statistics
(
    id bigint NOT NULL DEFAULT nextval('statistics_id_seq'::regclass),
    lot_number integer,
    parking_lot_status integer,
    updated_at timestamp without time zone,
    CONSTRAINT statistics_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE parking.statistics
    OWNER to postgres;


-- INSERT SAMPLE DATA

INSERT INTO parking_lots(id, lot_number, status, updated_at) VALUES (1, 1, 2, CURRENT_TIMESTAMP);
INSERT INTO parking_lots(id, lot_number, status, updated_at) VALUES (2, 2, 2, CURRENT_TIMESTAMP);
INSERT INTO parking_lots(id, lot_number, status, updated_at) VALUES (3, 3, 2, CURRENT_TIMESTAMP);
INSERT INTO parking_lots(id, lot_number, status, updated_at) VALUES (4, 4, 2, CURRENT_TIMESTAMP);
INSERT INTO parking_lots(id, lot_number, status, updated_at) VALUES (5, 5, 2, CURRENT_TIMESTAMP);
INSERT INTO parking_lots(id, lot_number, status, updated_at) VALUES (6, 6, 2, CURRENT_TIMESTAMP);
INSERT INTO parking_lots(id, lot_number, status, updated_at) VALUES (7, 7, 2, CURRENT_TIMESTAMP);
INSERT INTO parking_lots(id, lot_number, status, updated_at) VALUES (8, 8, 2, CURRENT_TIMESTAMP);
INSERT INTO parking_lots(id, lot_number, status, updated_at) VALUES (9, 9, 2, CURRENT_TIMESTAMP);
INSERT INTO parking_lots(id, lot_number, status, updated_at) VALUES (10, 10, 2, CURRENT_TIMESTAMP);
