CREATE TABLE redemption_code(
id BIGINT NOT NULL DEFAULT unique_rowid(),
code STRING(255) NULL,
create_date DATE NULL,
create_user STRING(255) NULL,
update_date DATE NULL,
update_user STRING(255) NULL,
version INTEGER NOT NULL,
book_id BIGINT NOT NULL REFERENCES book(id), 
student_id BIGINT NOT NULL REFERENCES student(id),
PRIMARY KEY (id));