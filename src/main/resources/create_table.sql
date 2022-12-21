-- auto-generated definition
create table products
(
    id    integer generated always as identity
        primary key,
    name  text             not null,
    price double precision not null,
    stock boolean
);
create table discount_card
(
    id       integer generated always as identity
        primary key,
    number   integer not null,
    discount integer not null
);
