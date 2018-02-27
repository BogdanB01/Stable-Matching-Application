--
drop table if exists courses cascade; --
drop table if exists grades cascade; --
drop table if exists lecturers cascade; -- 
drop table if exists project_tags cascade; --
drop table if exists projects cascade; --
drop table if exists roles cascade; --
drop table if exists students cascade; --
drop table if exists tags cascade; --
drop table if exists user_roles cascade; --
drop table if exists users cascade; --
drop table if exists students_preferences; 
drop table if exists assigned_projects;

drop sequence if exists user_seq;
drop sequence if exists tag_seq;
drop sequence if exists project_seq;
drop sequence if exists grade_seq;
drop sequence if exists course_seq;
drop sequence if exists stud_pref_seq;


create sequence user_seq START 1;
create sequence tag_seq START 1;
create sequence project_seq START 1;
create sequence grade_seq START 1;
create sequence course_seq START 1;
create sequence stud_pref_seq START 1;


drop extension if exists pgcrypto;

create extension pgcrypto;

create table roles(
	id bigint NOT NULL,
	name varchar(100) not null,
	primary key(id)
);

--roluri predefinite ale aplicatiei
insert into roles(id, name) values (1, 'admin');
insert into roles(id, name) values (2, 'lecturer');
insert into roles(id, name) values (3, 'student');

create table users(
	id bigint primary key,
	name varchar(100) not null,
	email varchar(100) not null, 
	password varchar(100) not null
);

insert into users(id, name, email, password) values (nextval('user_seq'), 'admin', 'ioan.boca@info.uaic.ro',  encode(digest('admin', 'sha256'), 'hex'));

--legatura dintre utilizatori si roluri
create table user_roles(
	user_id bigint NOT NULL references users(id) on delete cascade,
	role_id bigint NOT NULL references roles(id) on delete cascade
);

insert into user_roles(user_id, role_id) values (1, 1);

--tabelul pentru studenti
create table students(
	registration_number varchar(100) NOT NULL,
	id bigint NOT NULL,
	CONSTRAINT students_pkey PRIMARY KEY (id),
	CONSTRAINT fk7xqmtv7r2eb5axni3jm0a80su FOREIGN KEY (id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

--tabelul pentru profesori
create table lecturers(
	cabinet_number varchar(100),
	lecturer_id varchar(100),
	id bigint NOT NULL,
	CONSTRAINT lecturers_pkey PRIMARY KEY (id),
	CONSTRAINT fk7d4qvhqq0sr5qryod4h5tcvy5 FOREIGN KEY (id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

--tabelul pentru proiecte
create table projects(
	id bigint primary key,
	description varchar(1000),
	title varchar(100),
	lecturer_id bigint references lecturers(id) on delete cascade
);

--tabelul pentru cursuri
create table courses(
	id bigint primary key,
	abbreviation varchar(100),
	name varchar(200) NOT NULL,
	semester integer NOT NULL,
	year integer NOT NULL, 
	code varchar(10) NOT NULL
);

--tabelul pentru note
create table grades(
	id bigint primary key,
	value integer NOT NULL,
	course_id bigint references courses(id) on delete cascade,
	stud_id bigint references students(id) on delete cascade	
);


--tabelul pentru taguri
create table tags(
	id bigint primary key,
	name varchar(255)
);	

--legatura intre proiecte si taguri
create table project_tags(
	project_id bigint references projects(id) on delete cascade,
	tag_id bigint references tags(id) on delete cascade
);

--tabelul in care vom tine lista de preferinte a studentilor
create table students_preferences (
   id bigint primary key,
   student_id bigint not null references students(id) on delete restrict, 
   project_id bigint not null references projects(id) on delete restrict,
   submitted_at date not null 
);

--tabelul in care vom tine lista de studenti -> proiecte asignate
create table assigned_projects (
  id bigint primary key,
  student_id bigint not null references users(id) on delete restrict,
  project_id bigint not null references projects(id) on delete restrict,
 
);


insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Structuri de date', 'SD', 1, 1, 'CS1101');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Arhitectura calculatoarelor si sisteme de operare', 'ACSO', 1, 1, 'CS1102');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Logica pentru informatica', '', 1, 1, 'CS1103');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Matematica', '', 1, 1, 'CS1104');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Introducere in programare', 'IP', 1, 1, 'CS1105');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Limba engleza I', '', 1, 1, 'CS1106');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Programare competitiva', '', 1, 1, 'CS1113F');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Psihologia educatiei', '', 1, 1, 'CS1114F');

insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Programare orientata-obiect', 'POO', 2, 1, 'CS1207');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Sisteme de operare', 'SO', 2, 1,'CS1208');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Fundamente algebrice ale informaticii', 'FAI', 2, 1, 'CS1209');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Probabilitati si statistica', 'PS', 2, 1, 'CS1210');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Proiectarea algoritmilor', 'PA', 2, 1, 'CS1211');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Limba engleza II', '', 2, 1, 'CS1212');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Programare competitiva II', '', 2, 1, 'CS1214F');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Pedagogie I', '', 2, 1, 'CS1115F');

insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Retele de calculatoare', 'RC', 1, 2, 'CS2101');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Baze de date', 'BD', 1, 2, 'CS2102');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Limbaje formate, automate si compilatoare', 'LFAC', 1, 2, 'CS2103');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Algoritmica grafurilor', 'AG', 1, 2, 'CS2104');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Calculabilitate, decidabilitate si complexitate', 'CDC', 1, 2, 'CS2105');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Algoritmi genetici', 'GA', 1, 2, 'CS2105');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Principii ale limbajelor de programare', 'PLP', 1, 2, 'CS2105');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Limba engleza III', '', 1, 2, 'CS2106');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Programare competitva III', '', 1, 2, 'CS2114F');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Pedagogie II', '', 1, 2, 'CS2113F');

insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Tehnologii WEB', 'TW', 2, 2, 'CS2207');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Programare avansata', 'PA', 2, 2, 'CS2208');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Ingineria programarii', 'IP', 2, 2, 'CS2209');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Practica SGBD', 'PSGBD',  2, 2, 'CS2210');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Programare functionala', 'PF', 2, 2, 'CS2211');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Modele continue si Matlab', '', 2, 2, 'CS2211');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Introducere in criptografie', 'IC', 2, 2, 'CS2211');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Limba engleza IV', '', 2, 2, 'CS2212');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Programare competitiva IV', '', 2, 2, 'CS2215F');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Didactica informaticii', '', 2, 2, 'CS2214F');

insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Invatare automata', 'ML', 1, 3, 'CS3101');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Securitatea informatiei', 'SI', 1, 3, 'CS3102');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Inteligenta artificiala', 'IA', 1, 3, 'CS3103');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Introducere in .NET', '.NET', 1, 3, 'CS3104');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Dezvoltarea sistemelor fizice utilizand microprocesoare', 'DSFUM', 1, 3, 'CS3104');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Retele neuronale', 'RN', 1, 3, 'CS3104');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Animatie 3D: algoritmi si tehnici fundamentale', 'A3D', 1, 3, 'CS3104');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Programare si modelare probabilistica', 'PMP', 1, 3, 'CS3105');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Dezvoltarea aplicatiilor Web la nivel de client', 'DAWNC', 1, 3, 'CS3105');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Capitole speciale de sisteme de operare', 'CSSO', 1, 3, 'CS3105');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Practica - Programare in Python', '', 1, 3, 'CS3106');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Programare competitiva IV', '', 1, 3, 'CS3113F');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Practica pedagogica in invatamantul preuniversitar obligatoriu(1)', '', 1, 3, 'CS3114F');

insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Calcul numeric', 'CN', 2, 3, 'CS3207');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Grafica pe calculator', 'GPC', 2, 3, 'CS3208');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Programare bazate pe reguli', 'PBR', 2, 3, 'CS3209');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Tehnici de programare bazate pe platforma Android', 'TPPA',  2, 3, 'CS3209');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Analiza retelelor media sociale', 'ARMS', 2, 3, 'CS3209');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Aspecte computationale in teoria numerelor', 'ACTN', 2, 3, 'CS3209');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Psihologia comunicarii profesionale in domeniul IT-lui', '', 2, 3, 'CS3210');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Programare MS-Office', 'PMSO', 2, 3, 'CS3210');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Cloud Computing', 'CC', 2, 3, 'CS3210');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Metodologii de lucru in mediul Open Source', 'MLMOS', 2, 3, 'CS3210');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Retele Petri si aplicatii', '', 2, 3, 'CS3211');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Smart Card-uri si Aplicatii', 'SCA', 2, 3, 'CS3211');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Topici speciale de programare .NET', 'TSP.NET', 2, 3, 'CS3211');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Elaborare lucrare de licenta', '', 2, 3, 'CS3212');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Programare competitiva IV', '',  2, 3, 'CS3214F');
insert into courses(id, name, abbreviation, semester, year, code) values (nextval('course_seq'), 'Practica pedagogica in invatamantul preuniversitar obligatoriu', '', 2, 3, 'CS3216F');
