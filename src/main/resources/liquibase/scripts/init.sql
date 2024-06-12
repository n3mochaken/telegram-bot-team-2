--liquibase formatted sql

--changeset n.surzhikov:init db
create table animal(
    id SERIAL primary key,
    name varchar(64),
    age integer,
    photo_pass varchar(128)
);

create table owner(
    id SERIAL primary key,
    chat_id bigint,
    animal_id integer references animal(id),
    phone_number varchar(16),
    is_owner bool,
    full_name varchar(32)
);

create table report(
    id SERIAL primary key,
    photo bytea,
    food varchar(128),
    health varchar(128),
    changes varchar(128),
    approval bool,
    owner_id integer references owner(id)
)