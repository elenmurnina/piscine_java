SET DATABASE SQL SYNTAX PGS TRUE;

drop schema if exists product cascade;

create schema if not exists product;

create table if not exists product.product (
    id bigserial primary key,
    name text unique not null,
    price int not null
);
