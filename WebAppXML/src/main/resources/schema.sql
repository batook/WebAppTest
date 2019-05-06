drop table if exists v$version;

create table v$version (
  banner varchar(25) not null
);

insert into v$version values('H2');
insert into v$version values('1.4.197');
insert into v$version values('In-Memory');
commit;