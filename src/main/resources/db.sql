drop table book;
drop table student;

create table student (
        id serial not null,
        create_date date,
        create_user varchar(255),
        enabled int4 not null,
        update_date date,
        update_user varchar(255),
        version int4 not null,
        academic_year int4 not null,
        age int4 not null,
        email varchar(255),
        first_name varchar(255),
        gender varchar(255),
        last_name varchar(255),
        middle_name varchar(255),
        phone1 varchar(255),
        phone2 varchar(255),
        primary key (id)
    )
   

create table book (
       id  bigserial not null,
        create_date date,
        create_user varchar(255),
        enabled int4 not null,
        update_date date,
        update_user varchar(255),
        version int4 not null,
        author varchar(255),
        authorid int4 not null,
        editor varchar(255),
        genre varchar(255),
        isbn int8,
        publication_year int4 not null,
        publisher varchar(255),
        primary key (id)
    )
   
    Insert Into Student (first_name, last_name, middle_name, enabled, version, academic_year, age) values(“Milind”, “Chandramohan”, “”, 1, 1, 2020, 16)
    Insert Into Student (first_name, last_name, middle_name, enabled, version, academic_year, age) values(“Tanya”, “Chandramohan”, “”, 1, 1, 2029, 7)