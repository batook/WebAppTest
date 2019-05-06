drop table if exists v$version;

create table v$version (
  banner varchar(25) not null
);

insert into v$version values('H2 Database');
insert into v$version values('Version 1.4.197');
insert into v$version values('In-Memory mode');
commit;