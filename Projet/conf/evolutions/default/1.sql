# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table contact (
  id                            bigint auto_increment not null,
  pseudo                        varchar(255),
  mail                          varchar(255),
  objet                         varchar(255),
  message                       varchar(255),
  constraint pk_contact primary key (id)
);


# --- !Downs

drop table if exists contact;

