drop table if exists "medication";

create table  "medication" (
    "id" integer primary key AUTOINCREMENT,
    "name" varchar(255) not null,
    "weight" bigint not null,
    "code" varchar(255) not null,
    "image_url" varchar(255) not null

);