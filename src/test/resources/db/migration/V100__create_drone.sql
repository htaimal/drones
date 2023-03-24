drop table if exists "drone";

CREATE TABLE "drone" (
    "id" integer primary key AUTOINCREMENT,
    "serial" varchar(255) not null,
    "model" varchar(255) not null,
    "max_load_capacity" bigint not null,
    "battery" bigint not null,
    "state" varchar(255) not null
);