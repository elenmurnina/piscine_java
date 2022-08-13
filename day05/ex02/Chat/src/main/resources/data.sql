insert into chat.users (login, password)
values ('Lol', 'qwerty');
insert into chat.users (login, password)
values ('Kek', 'asdfg');
insert into chat.users (login, password)
values ('Azaza', 'zxcvb');
insert into chat.users (login, password)
values ('Axaxa', '12345');
insert into chat.users (login, password)
values ('Imho', '000000');

insert into chat.rooms (name, owner)
values ('general', 1);
insert into chat.rooms (name, owner)
values ('random', 2);
insert into chat.rooms (name, owner)
values ('_channel', 3);
insert into chat.rooms (name, owner)
values ('msk_adm', 4);
insert into chat.rooms (name, owner)
values ('pedago_intra', 5);

insert into chat.messages (author, room, textmsg, datetime)
values (1, 1, 'First message', '2022-07-27 00:00:01');
insert into chat.messages (author, room, textmsg, datetime)
values (2, 3, 'Second message', '2022-07-27 00:00:01');
insert into chat.messages (author, room, textmsg, datetime)
values (5, 3, 'Thitd message', '2022-07-27 00:00:02');
insert into chat.messages (author, room, textmsg, datetime)
values (4, 4, 'Hello, world!', '2022-07-27 00:00:04');
insert into chat.messages (author, room, textmsg, datetime)
values (5, 5, 'Bye!', '2022-07-27 00:00:05');

