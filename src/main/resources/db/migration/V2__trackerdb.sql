CREATE TABLE student (
id BIGINT NOT NULL DEFAULT unique_rowid(),
create_date DATE NULL,
create_user STRING(255) NULL,
enabled INTEGER NOT NULL,
update_date DATE NULL,
update_user STRING(255) NULL,
version INTEGER NOT NULL,
academic_year INTEGER NOT NULL,
age INTEGER NOT NULL,
email STRING(255) NULL,
first_name STRING(255) NULL,
gender STRING(255) NULL,
last_name STRING(255) NULL,
middle_name STRING(255) NULL,
phone1 STRING(255) NULL,
phone2 STRING(255) NULL,
CONSTRAINT "primary" PRIMARY KEY (id ASC)
);


CREATE TABLE book (
id BIGINT NOT NULL DEFAULT unique_rowid(),
create_date DATE NULL,
create_user STRING(255) NULL,
enabled INTEGER NOT NULL,
update_date DATE NULL,
update_user STRING(255) NULL,
version INTEGER NOT NULL,
author STRING(255) NULL,
authorid INTEGER NULL,
editor STRING(255) NULL,
genre STRING(255) NULL,
isbn BIGINT NULL,
publication_year INTEGER NULL,
publisher STRING(255) NULL,
title STRING(255) NULL,
CONSTRAINT "primary" PRIMARY KEY (id ASC),
UNIQUE INDEX ndx_bok_isbn(isbn ASC)
);

CREATE TABLE author (
id BIGINT NOT NULL DEFAULT unique_rowid(),
create_date DATE NULL,
create_user STRING(255) NULL,
enabled INTEGER NOT NULL,
update_date DATE NULL,
update_user STRING(255) NULL,
version INTEGER NOT NULL,
email STRING(255) NULL,
first_name STRING(255) NULL,
gender STRING(255) NULL,
last_name STRING(255) NULL,
middle_name STRING(255) NULL,
first_publication_year INTEGER NOT NULL,
last_publication_year INTEGER NULL,
CONSTRAINT "primary" PRIMARY KEY (id ASC)
); 

CREATE TABLE book_copy ( 
id BIGINT NOT NULL, 
create_date DATE NULL, 
create_user STRING(255) NULL, 
enabled INTEGER NOT NULL, 
update_date DATE NULL, 
update_user STRING(255) NULL, 
version INTEGER NOT NULL, 
price DOUBLE PRECISION NULL, 
redemption_code STRING(255) NULL, 
book_id BIGINT NOT NULL REFERENCES book(id), 
student_id BIGINT NOT NULL REFERENCES student(id),  
CONSTRAINT "primary" PRIMARY KEY (id ASC), 
UNIQUE INDEX ndx_bkcp_rdmp(redemption_code ASC)
); 


