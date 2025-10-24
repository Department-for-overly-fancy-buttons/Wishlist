drop
database if exists tourist_Guide;
CREATE
DATABASE IF NOT EXISTS tourist_Guide
  CHARACTER SET utf8mb4;

USE
tourist_Guide;

create table tourist_attractions
(
    name        varchar(255)  not null,
    description varchar(1000) not null,
    city        varchar(255)  not null,
    ticket_price_in_DKK double not null,
    primary key (name)
);

create table tag
(
    name varchar(155) not null,
    primary key (name)
);

create table city
(
    name varchar(155) not null,
    primary key (name)
);

create table tags
(
    attraction_name varchar(255) not null,
    tag_name        varchar(155) not null,
    primary key (attraction_name, tag_name),
    foreign key (attraction_name) references tourist_attractions (name)
        on delete cascade
);