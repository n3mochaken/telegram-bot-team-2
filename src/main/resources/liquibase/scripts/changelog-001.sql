--liquibase formatted sql

--changeset n.surzhikov:changelog-001
create table animal_avatar
(
    id         SERIAL primary key,
    file_path  varchar(128),
    file_size  bigint,
    media_type varchar(32),
    preview bytea,
    animal_id bigint references animal(id)
);