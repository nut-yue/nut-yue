/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/4/12 11:46:54                           */
/*==============================================================*/


drop table if exists t_leavel;

drop table if exists t_user;

/*==============================================================*/
/* Table: t_leavel                                              */
/*==============================================================*/
create table t_leavel
(
   id                   int not null auto_increment,
   leavelName           varchar(20),
   primary key (id)
);

alter table t_leavel comment '用户等级表';

/*==============================================================*/
/* Table: t_user                                                */
/*==============================================================*/
create table t_user
(
   userId               int not null auto_increment,
   leavelId             int,
   userName             varchar(20) not null,
   pwd                  varchar(64) not null,
   realName             varchar(50),
   primary key (userId)
);

alter table t_user comment '用户管理表';

alter table t_user add constraint FK_Reference_1 foreign key (leavelId)
      references t_leavel (id) on delete restrict on update restrict;

