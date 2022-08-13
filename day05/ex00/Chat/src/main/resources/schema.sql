drop schema if exists chat cascade;

create schema if not exists chat;

create table if not exists chat.users (
    id bigserial primary key,
    login text unique not null,
    password text not null
);

create table if not exists chat.rooms (
    id bigserial primary key,
    name text unique not null,
    owner bigint references chat.users(id) not null
);

create table if not exists chat.messages (
    id bigserial primary key,
    author bigint references chat.users(id) not null,
    room bigint references chat.rooms(id) not null,
    textmsg text not null,
    datetime timestamp not null
);
