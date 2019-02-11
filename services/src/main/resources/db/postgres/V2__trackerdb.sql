CREATE TABLE student (
id SERIAL PRIMARY KEY,
student_id VARCHAR(255),
create_date DATE NULL,
create_user VARCHAR(255) NULL,
enabled INTEGER NOT NULL,
update_date DATE NULL,
update_user VARCHAR(255) NULL,
version INTEGER NOT NULL,
academic_year INTEGER NOT NULL,
age INTEGER NOT NULL,
email VARCHAR(255) NULL,
first_name VARCHAR(255) NULL,
gender VARCHAR(255) NULL,
last_name VARCHAR(255) NULL,
middle_name VARCHAR(255) NULL,
phone1 VARCHAR(255) NULL,
phone2 VARCHAR(255) NULL,
CONSTRAINT ndx_stdnt_id UNIQUE (student_id)
);


CREATE TABLE book (
id SERIAL PRIMARY KEY,
create_date DATE NULL,
create_user VARCHAR(255) NULL,
enabled INTEGER NOT NULL,
update_date DATE NULL,
update_user VARCHAR(255) NULL,
version INTEGER NOT NULL,
author VARCHAR(255) NULL,
authorid INTEGER NULL,
editor VARCHAR(255) NULL,
genre VARCHAR(255) NULL,
isbn VARCHAR(255) NOT NULL,
publication_year INTEGER NULL,
publisher VARCHAR(255) NULL,
title VARCHAR(255) NULL,
CONSTRAINT ndx_bok_isbn UNIQUE (isbn)
);

CREATE TABLE author (
id SERIAL  PRIMARY KEY,
create_date DATE NULL,
create_user VARCHAR(255) NULL,
enabled INTEGER NOT NULL,
update_date DATE NULL,
update_user VARCHAR(255) NULL,
version INTEGER NOT NULL,
email VARCHAR(255) NULL,
first_name VARCHAR(255) NULL,
gender VARCHAR(255) NULL,
last_name VARCHAR(255) NULL,
middle_name VARCHAR(255) NULL,
first_publication_year INTEGER NOT NULL,
last_publication_year INTEGER NULL
); 


CREATE TABLE checkout (
id SERIAL NOT NULL,
redemption_code VARCHAR(255) NOT NULL,
check_out_date timestamp,
check_in_date timestamp,
create_date DATE NULL,
create_user VARCHAR(255) NULL,
enabled INTEGER NOT NULL,
update_date DATE NULL,
update_user VARCHAR(255) NULL,
version INTEGER NOT NULL,
book_id BIGINT NOT NULL REFERENCES book(id), 
student_id BIGINT NOT NULL REFERENCES student(id),
CONSTRAINT ndx_redmpt_code UNIQUE (redemption_code)
);


