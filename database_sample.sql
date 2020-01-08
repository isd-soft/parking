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
    id uuid NOT NULL,
    lot_number integer NOT NULL,
    date_time timestamp without time zone NOT NULL,
    status character varying(20) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT primary_key PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE parking.parking_lots
    OWNER to postgres;


-- Table: parking.stats

-- DROP TABLE parking.stats;

CREATE TABLE parking.stats
(
    id uuid NOT NULL,
    park_lot_id uuid NOT NULL,
    status character varying(20) COLLATE pg_catalog."default" NOT NULL,
    date_time timestamp without time zone NOT NULL,
    CONSTRAINT stats_primary__key PRIMARY KEY (id),
    CONSTRAINT f_key FOREIGN KEY (park_lot_id)
        REFERENCES parking.parking_lots (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE parking.stats
    OWNER to postgres;


-- INSERT SAMPLE DATA

INSERT INTO parking.parking_lots(id, lot_number, date_time, status)
VALUES ('123e4567-e89b-42d3-a456-556642440000', 1, CURRENT_TIMESTAMP, 'OCCUPIED');

INSERT INTO parking.parking_lots(id, lot_number, date_time, status)
VALUES ('123e4567-e89b-42d3-a456-556642440001', 2, CURRENT_TIMESTAMP, 'FREE');

INSERT INTO parking.parking_lots(id, lot_number, date_time, status)
VALUES ('ec99292d-9f8f-4027-a355-35245c9bffb0', 3, CURRENT_TIMESTAMP, 'FREE');

INSERT INTO parking.parking_lots(id, lot_number, date_time, status)
VALUES ('b2aa019e-d5a6-474d-ab52-dcd6de2582bb', 4, CURRENT_TIMESTAMP, 'UNKNOWN');

INSERT INTO parking.parking_lots(id, lot_number, date_time, status)
VALUES ('d7270db0-43aa-44d2-8a0e-034b0a01861a', 5, CURRENT_TIMESTAMP, 'UNKNOWN');

INSERT INTO parking.parking_lots(id, lot_number, date_time, status)
VALUES ('1bfae70b-57bf-42a8-81b3-1fa2439e6372', 6, CURRENT_TIMESTAMP, 'UNKNOWN');

INSERT INTO parking.parking_lots(id, lot_number, date_time, status)
VALUES ('0a06209d-d5ec-4c15-9055-1a75009c8d38', 7, CURRENT_TIMESTAMP, 'UNKNOWN');

INSERT INTO parking.parking_lots(id, lot_number, date_time, status)
VALUES ('60190845-b908-4cdc-b4c9-ec32c6da839f', 8, CURRENT_TIMESTAMP, 'UNKNOWN');

INSERT INTO parking.parking_lots(id, lot_number, date_time, status)
VALUES ('d12772fe-ab7a-4a88-a049-9570e30c3d76', 9, CURRENT_TIMESTAMP, 'UNKNOWN');

INSERT INTO parking.parking_lots(id, lot_number, date_time, status)
VALUES ('d60d9f97-79c4-4e61-85d0-2cc4a9462bc0', 10, CURRENT_TIMESTAMP, 'UNKNOWN');
