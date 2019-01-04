create table User
(
    user_id number(10) primary key,
    firstName varchar2(255) not null,
    lastName varchar2(255) not null,
    dob varchar2(255) not null,
    email varchar2(255) not null unique,
    streetNumber varchar2(255) not null,
    streetName varchar2(255) not null,
    country varchar2(255) not null,
    city varchar2(255) not null,
    states varchar2(255) not null,
    age number(10) not null,
    gender varchar2(255) not null
);