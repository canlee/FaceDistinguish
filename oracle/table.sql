create table userinfo(
username varchar(20),
password varchar(20),
id number primary key
);

create table imageinfo(
id number,
image long raw,
foreign key(id) references userinfo(id)
);

create table wopt(
array long raw
);

create table project(
id number,
project long raw,
foreign key(id) references userinfo(id)
);

create sequence userid
increment by 1
start with 1
nomaxvalue
nocycle
nocache;