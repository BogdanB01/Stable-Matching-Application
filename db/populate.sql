
-- inseram rolurile in aplicatie
insert into roles(id, name) values (1, 'ROLE_ADMIN');
insert into roles(id, name) values (2, 'ROLE_LECTURER');
insert into roles(id, name) values (3, 'ROLE_STUDENT');

-- inseram admin
insert into users(id, name, email, password) values (1, 'admin', 'admin@yahoo.com', '$2a$10$2ChnmL4oNOX.C1mLFz18gesl9YUJKpE3Kfjxz99jtUYTuNYOHzP3K');

-- inseram profesori
insert into users(id, name, email, password) values (2, 'Profesorul X', 'profesorx@yahoo.com', '$2a$10$2ChnmL4oNOX.C1mLFz18gesl9YUJKpE3Kfjxz99jtUYTuNYOHzP3K');
insert into users(id, name, email, password) values (3, 'Profesorul Y', 'profesory@yahoo.com', '$2a$10$2ChnmL4oNOX.C1mLFz18gesl9YUJKpE3Kfjxz99jtUYTuNYOHzP3K');
insert into users(id, name, email, password) values (4, 'Profesorul Z', 'profesorz@yahoo.com', '$2a$10$2ChnmL4oNOX.C1mLFz18gesl9YUJKpE3Kfjxz99jtUYTuNYOHzP3K');

insert into lecturers(cabinet_number, lecturer_id, id) values ('C309', '', 2);
insert into lecturers(cabinet_number, lecturer_id, id) values ('C308', '', 3);
insert into lecturers(cabinet_number, lecturer_id, id) values ('C307', '', 4);

-- inseram studenti
insert into users(id, name, email, password) values (5, 'Boca Ioan-Bogdan', 'bocabogdan2010@yahoo.com', '$2a$10$2ChnmL4oNOX.C1mLFz18gesl9YUJKpE3Kfjxz99jtUYTuNYOHzP3K');
insert into users(id, name, email, password) values (6, 'Olaru Marius-George', 'student2@yahoo.com', '$2a$10$2ChnmL4oNOX.C1mLFz18gesl9YUJKpE3Kfjxz99jtUYTuNYOHzP3K');
insert into users(id, name, email, password) values (7, 'Danila Marius Cristian', 'student3@yahoo.com', '$2a$10$2ChnmL4oNOX.C1mLFz18gesl9YUJKpE3Kfjxz99jtUYTuNYOHzP3K');
insert into users(id, name, email, password) values (8, 'Tinca Alexandru Claudiu', 'student4@yahoo.com', '$2a$10$2ChnmL4oNOX.C1mLFz18gesl9YUJKpE3Kfjxz99jtUYTuNYOHzP3K');
insert into users(id, name, email, password) values (9, 'Romanescu Razvan', 'student5@yahoo.com', '$2a$10$2ChnmL4oNOX.C1mLFz18gesl9YUJKpE3Kfjxz99jtUYTuNYOHzP3K');
insert into users(id, name, email, password) values (10, 'Benchea Vlad', 'student6@yahoo.com', '$2a$10$2ChnmL4oNOX.C1mLFz18gesl9YUJKpE3Kfjxz99jtUYTuNYOHzP3K');
insert into users(id, name, email, password) values (11, 'Costan Catalin', 'student7@yahoo.com', '$2a$10$2ChnmL4oNOX.C1mLFz18gesl9YUJKpE3Kfjxz99jtUYTuNYOHzP3K');
insert into users(id, name, email, password) values (12, 'Leonte Codrin', 'student8', '$2a$10$2ChnmL4oNOX.C1mLFz18gesl9YUJKpE3Kfjxz99jtUYTuNYOHzP3K');

insert into students values (registration_number, id, project_id) values ('1001', 5, null);
insert into students values (registration_number, id, project_id) values ('1002', 6, null);
insert into students values (registration_number, id, project_id) values ('1003', 7, null);
insert into students values (registration_number, id, project_id) values ('1004', 8, null);
insert into students values (registration_number, id, project_id) values ('1005', 9, null);
insert into students values (registration_number, id, project_id) values ('1006', 10, null);
insert into students values (registration_number, id, project_id) values ('1007', 11, null);
insert into students values (registration_number, id, project_id) values ('1008', 12, null);

-- inseram rolurile utilizatorilor
insert into user_roles(user_id, role_id) values (1, 1);
insert into user_roles(user_id, role_id) values (2, 2);
insert into user_roles(user_id, role_id) values (3, 2);
insert into user_roles(user_id, role_id) values (4, 2);
insert into user_roles(user_id, role_id) values (5, 3);
insert into user_roles(user_id, role_id) values (6, 3);
insert into user_roles(user_id, role_id) values (7, 3);
insert into user_roles(user_id, role_id) values (8, 3);
insert into user_roles(user_id, role_id) values (9, 3);
insert into user_roles(user_id, role_id) values (10, 3);
insert into user_roles(user_id, role_id) values (11, 3);
insert into user_roles(user_id, role_id) values (12, 3);


-- inseram cursurile
insert into courses(id, name, abbreviation, semester, year, code) values (1, 'Structuri de date', 'SD', 1, 1, 'CS1101');
insert into courses(id, name, abbreviation, semester, year, code) values (2, 'Arhitectura calculatoarelor si sisteme de operare', 'ACSO', 1, 1, 'CS1102');
insert into courses(id, name, abbreviation, semester, year, code) values (3 'Logica pentru informatica', 'LOGICA', 1, 1, 'CS1103');
insert into courses(id, name, abbreviation, semester, year, code) values (4, 'Matematica', 'MATE', 1, 1, 'CS1104');
insert into courses(id, name, abbreviation, semester, year, code) values (5, 'Introducere in programare', 'IP', 1, 1, 'CS1105');


-- adaugam proiecte
insert into projects(id, title, description, lecturer_id, capacity, active) values (1, 'SQL in Google Tables', 'Se va realiza un framework prin care se va oferi acces programatic la anumite inregistrari din tabelul, asemanator SQL', 2, 1, true);
insert into projects(id, title, description, lecturer_id, capacity, active) values (2, 'Plotter vertical', 'Aplicatie Arduino', 4, 1, true);
insert into projects(id, title, description, lecturer_id, capacity, active) values (3, 'Dreseaza-l pe cutzu virtual', 'Realitate augmentata', 3, 1, true);
insert into projects(id, title, description, lecturer_id, capacity, active) values (4, 'BD app', 'Aplicatie de evaluare pentru disciplina baze de date', 3, 1, true);
insert into projects(id, title, description, lecturer_id, capacity, active) values (5, 'Recunoasterea automata a semnelor de circulatie', 'Aplicatie android ce va realiza recunoasterea automata a semnelor de circulatie', 2, 1, true);
insert into projects(id, title, description, lecturer_id, capacity, active) values (6, 'Image infill', 'Procesare de imagini', 4, 1, true);
insert into projects(id, title, description, lecturer_id, capacity, active) values (7, 'Serverless media sharing', 'Peer2Peet network programming', 2, 2, false);

-- adaugam note
insert into grades(id, value, course_id, stud_id) values (1, 10, 1, 5);
insert into grades(id, value, course_id, stud_id) values (2,  9, 2, 5);
insert into grades(id, value, course_id, stud_id) values (3,  8, 3, 5);
insert into grades(id, value, course_id, stud_id) values (4,  7, 4, 5);
insert into grades(id, value, course_id, stud_id) values (5,  9, 5, 5);


insert into grades(id, value, course_id, stud_id) values (6, 8, 1, 6);
insert into grades(id, value, course_id, stud_id) values (7,  6, 2, 6);
insert into grades(id, value, course_id, stud_id) values (8,  5, 3, 6);
insert into grades(id, value, course_id, stud_id) values (9,  8, 4, 6);
insert into grades(id, value, course_id, stud_id) values (10,  5, 5, 6);


insert into grades(id, value, course_id, stud_id) values (11, 9, 1, 7);
insert into grades(id, value, course_id, stud_id) values (12,  9, 2, 7);
insert into grades(id, value, course_id, stud_id) values (13,  8, 3, 7);
insert into grades(id, value, course_id, stud_id) values (14,  7, 4, 7);
insert into grades(id, value, course_id, stud_id) values (15,  9, 5, 7);


insert into grades(id, value, course_id, stud_id) values (16, 6, 1, 8);
insert into grades(id, value, course_id, stud_id) values (17,  9, 2, 8);
insert into grades(id, value, course_id, stud_id) values (18,  9, 3, 8);
insert into grades(id, value, course_id, stud_id) values (19,  10, 4, 8);
insert into grades(id, value, course_id, stud_id) values (20,  8, 5, 8);


insert into grades(id, value, course_id, stud_id) values (21, 10, 1, 9);
insert into grades(id, value, course_id, stud_id) values (22,  6, 2, 9);
insert into grades(id, value, course_id, stud_id) values (23,  7, 3, 9);
insert into grades(id, value, course_id, stud_id) values (24,  10, 4, 9);
insert into grades(id, value, course_id, stud_id) values (25,  9, 5, 9);


insert into grades(id, value, course_id, stud_id) values (26, 8, 1, 10);
insert into grades(id, value, course_id, stud_id) values (27,  5, 2, 10);
insert into grades(id, value, course_id, stud_id) values (28,  8, 3, 10);
insert into grades(id, value, course_id, stud_id) values (29,  5, 4, 10);
insert into grades(id, value, course_id, stud_id) values (30,  10, 5, 10);


insert into grades(id, value, course_id, stud_id) values (31, 7, 1, 11);
insert into grades(id, value, course_id, stud_id) values (32,  6, 2, 11);
insert into grades(id, value, course_id, stud_id) values (33,  5, 3, 11);
insert into grades(id, value, course_id, stud_id) values (34,  7, 4, 11);
insert into grades(id, value, course_id, stud_id) values (35,  8, 5, 11);

insert into grades(id, value, course_id, stud_id) values (36, 10, 1, 12);
insert into grades(id, value, course_id, stud_id) values (37,  9, 2, 12);
insert into grades(id, value, course_id, stud_id) values (38,  9, 3, 12);
insert into grades(id, value, course_id, stud_id) values (39,  10, 4, 12);
insert into grades(id, value, course_id, stud_id) values (40,  10, 5, 12);


-- inseram taguri

insert into tags(id, name) values (1, 'JAVA');
insert into tags(id, name) values (2, 'WEB');
insert into tags(id, name) values (3, 'ARDUINO');
insert into tags(id, name) values (4, 'NETWORKING');
insert into tags(id, name) values (5, 'DB');
insert into tags(id, name) values (6, 'SQL');
insert into tags(id, name) values (7, 'AR/VR');
insert into tags(id, name) values (8, 'ANDROID');
insert into tags(id, name) values (9, 'C++');

-- adaugam taguri proiectelor
select * from project_tags;

insert into project_tags(project_id, tag_id) values (1, 5);
insert into project_tags(project_id, tag_id) values (1, 6);

insert into project_tags(project_id, tag_id) values (2, 3);

insert into project_tags(project_id, tag_id) values (3, 7);
insert into project_tags(project_id, tag_id) values (3, 8);

insert into project_tags(project_id, tag_id) values (4, 5);
insert into project_tags(project_id, tag_id) values (4, 6);

insert into project_tags(project_id, tag_id) values (5, 8);

insert into project_tags(project_id, tag_id) values (6, 9);

insert into project_tags(project_id, tag_id) values (7, 4);


select * from questions;
insert into questions(id, question, project_id) values ();
insert into questions(id, question, project_id) values ();
insert into questions(id, question, project_id) values ();
insert into questions(id, question, project_id) values ();



insert into history(id, student_name, project_title, year) values (1, 'Florescu Razvan',  'Aplicatie Web', 2015);
insert into history(id, student_name, project_title, year) values (2, 'Milut Camelia',    'Amazon Echo', 2015);

insert into history(id, student_name, project_title, year) values (3, 'Plugariu Raluca',  'Spring Boot minions', 2016);
insert into history(id, student_name, project_title, year) values (4, 'Motrescu Tudor',   'Android app', 2016);

insert into history(id, student_name, project_title, year) values (5, 'Ciornei Giani',    'Automotive', 2017);
insert into history(id, student_name, project_title, year) values (6, 'Cojocariu Bogdan', 'Scrum framework', 2017);


delete from history where year = 2018