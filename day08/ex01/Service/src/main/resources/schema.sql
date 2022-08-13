drop schema if exists spring cascade;

create schema if not exists spring;

create table if not exists spring.users (
    id bigserial primary key,
    email text not null
);

insert into spring.users (email)
values ('firstUser@email.com'),
       ('secondUser@email.com'),
       ('thirdUser@email.com'),
       ('fourUser@email.com'),
       ('moooreUser@email.eu');
