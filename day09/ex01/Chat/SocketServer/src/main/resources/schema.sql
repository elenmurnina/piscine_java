drop schema if exists chat cascade;

create schema if not exists chat;

create table if not exists chat.users (
    id bigserial primary key,
    username text unique not null,
    password text not null
);

create table if not exists chat.messages (
    id bigserial primary key,
    author_id bigint not null references chat.users (id),
    text text not null,
    creation_time timestamp not null
);
