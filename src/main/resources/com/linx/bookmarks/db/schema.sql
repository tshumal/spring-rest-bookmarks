drop table if exists account;
drop table if exists bookmark;

create table account (
  id identity,
  username varchar(25) not null,
  password varchar(25) not null 
);

create table bookmark (
  id integer identity primary key,
  account integer not null,
  description varchar(2000) not null,
  uri varchar(160) not null,
  foreign key (account) references account(id)
);
