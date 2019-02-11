-- Run as 
-- psql -U postgres -f ~\personal\code\milind\git\tracker\services\src\test\resources\dml.sql  
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 11.1
-- Dumped by pg_dump version 11.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Data for Name: book; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.book (id, create_date, create_user, enabled, update_date, update_user, version, author, authorid, editor, genre, isbn, publication_year, publisher, title) VALUES (1, '2019-02-08', 'test', 1, '2019-02-08', 'test', 1, 'Douglas Adams', 1, '', '', '1535185554', 2016, 'Createspace Independent Publishing Platform', 'The Hitchhiker''s Guide to the Galaxy');
INSERT INTO public.book (id, create_date, create_user, enabled, update_date, update_user, version, author, authorid, editor, genre, isbn, publication_year, publisher, title) VALUES (4, NULL, NULL, 1, NULL, NULL, 0, 'Tara Westover;', NULL, NULL, NULL, '9780099511021', 2018, 'Windmill Books', 'Educated');
INSERT INTO public.book (id, create_date, create_user, enabled, update_date, update_user, version, author, authorid, editor, genre, isbn, publication_year, publisher, title) VALUES (6, NULL, NULL, 1, NULL, NULL, 0, 'Carl Sagan;', NULL, NULL, NULL, '9780345346292', 1977, 'Random House Digital, Inc.', 'The Dragons of Eden');
INSERT INTO public.book (id, create_date, create_user, enabled, update_date, update_user, version, author, authorid, editor, genre, isbn, publication_year, publisher, title) VALUES (7, NULL, NULL, 1, NULL, NULL, 0, 'Peter Bussey;', NULL, NULL, NULL, '9780830851492', 2016, 'InterVarsity Press', 'Signposts to God');


--
-- Name: book_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.book_id_seq', 7, true);



--
-- Data for Name: checkout; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.checkout (id, code, check_out_date, check_in_date, create_date, create_user, update_date, update_user, version, book_id, student_id) VALUES (1, 'adfad', NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, 1);


--
-- Name: checkout_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.checkout_id_seq', 1, false);



--
-- Data for Name: student; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.student (id, student_id, create_date, create_user, enabled, update_date, update_user, version, academic_year, age, email, first_name, gender, last_name, middle_name, phone1, phone2) VALUES (1, '123', NULL, NULL, 1, NULL, NULL, 0, 2020, 17, NULL, 'Mil', NULL, 'Cha', NULL, '123123123', '12312311');


--
-- Name: student_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.student_id_seq', 3, true);


--
-- PostgreSQL database dump complete
--

commit;