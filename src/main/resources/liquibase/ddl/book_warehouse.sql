-- liquibase formatted sql

-- changeset mikelevin:create_book_warehouse_table

create table book_warehouse
(
    id      serial not null,
    book_id    serial not null,
    balance   int,
    version int8 default 0,
    primary key (id)
)