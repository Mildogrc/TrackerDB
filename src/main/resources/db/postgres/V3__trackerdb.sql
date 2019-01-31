CREATE TABLE redemption_code(
id SERIAL NOT NULL,
code VARCHAR(255) NULL,
create_date DATE NULL,
create_user VARCHAR(255) NULL,
update_date DATE NULL,
update_user VARCHAR(255) NULL,
version INTEGER NOT NULL,
book_id BIGINT NOT NULL REFERENCES book(id), 
student_id BIGINT NOT NULL REFERENCES student(id));