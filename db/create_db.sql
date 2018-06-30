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
drop table if exists preferences; 
drop table if exists bibliographies;
drop table if exists questions;
drop table if exists files;
drop table if exists answers;
drop table if exists app_state;

drop sequence if exists user_seq;
drop sequence if exists tag_seq;
drop sequence if exists project_seq;
drop sequence if exists grade_seq;
drop sequence if exists course_seq;
drop sequence if exists stud_pref_seq;
drop sequence if exists bibliography_seq;
drop sequence if exists question_seq;
drop sequence if exists file_seq;
drop sequence if exists answer_seq;
drop sequence if exists proj_pref;
drop sequence if exists history_seq;

create sequence user_seq START 1;
create sequence tag_seq START 1;
create sequence project_seq START 1;
create sequence grade_seq START 1;
create sequence course_seq START 1;
create sequence stud_pref_seq START 1;
create sequence bibliography_seq START 1;
create sequence question_seq START 1;
create sequence file_seq START 1;
create sequence answer_seq START 1;
create sequence proj_pref START 1;
create sequence history_seq START 1;

create table roles(
	id bigint NOT NULL,
	name varchar(100) not null,
	primary key(id)
);

--roluri predefinite ale aplicatiei
insert into roles(id, name) values (1, 'ROLE_ADMIN');
insert into roles(id, name) values (2, 'ROLE_LECTURER');
insert into roles(id, name) values (3, 'ROLE_STUDENT');

select * from roles;

create table users(
	id bigint primary key,
	name varchar(100) not null,
	email varchar(100) not null, 
	password varchar(100) not null,
	last_password_reset_date timestamp
);

alter table users add column last_password_reset_date timestamp;

--legatura dintre utilizatori si roluri
create table user_roles(
	user_id bigint references users(id) on delete cascade,
	role_id bigint references roles(id) on delete cascade
);

--tabelul pentru studenti
create table students(
	registration_number varchar(100) NOT NULL,
	id bigint NOT NULL,
	project_id bigint references projects(id),
	CONSTRAINT students_pkey PRIMARY KEY (id),
	CONSTRAINT user_students_fk FOREIGN KEY (id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

--tabelul pentru profesori
create table lecturers(
	cabinet_number varchar(100),
	lecturer_id varchar(100),
	id bigint NOT NULL,
	CONSTRAINT lecturers_pkey PRIMARY KEY (id),
	CONSTRAINT user_lecturers_fk FOREIGN KEY (id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

--tabelul pentru proiecte
create table projects(
	id bigint primary key,
	description varchar(1000),
	title varchar(100),
	capacity integer,
	active boolean,
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
create table preferences (
   id bigint primary key,
   student_id bigint not null references students(id) on delete cascade, 
   project_id bigint not null references projects(id) on delete cascade,
   index integer,
   avg double precision,
   pos integer,
   personal_note varchar(1024),
   submitted_at timestamp not null 
);

-- tabelul pentru bibliografiile proiectelor
create table bibliographies (
  id bigint primary key,
  name varchar(255),
  project_id bigint references projects(id) on delete cascade
);

-- tabelul pentru intrebari asociate proiectelor
create table questions (
  id bigint primary key,
  question varchar(255),
  project_id bigint references projects(id) on delete cascade
);


-- tabelul in care vom tine raspunsurile la intrebari
create table answers (
  id bigint primary key,
  answer varchar(255),
  question_id bigint references questions(id) on delete cascade,
  student_id bigint references students(id) on delete cascade
);

-- tabelul pentru fisiere asociate proiectelor
create table files (
	id bigint primary key,
	path varchar(255),
	project_id bigint references projects(id) on delete cascade
);

create table history (
	id bigint primary key,
	student_name varchar(255),
	project_title varchar(255), 
	year integer
);

create table app_state (
	id bigint primary key,
	state varchar(255)
);

