drop database if exists testdb;
create database if not exists testdb default charset utf8mb4;

use testdb;

create table sys_user (
  id bigint unsigned not null auto_increment,
  name varchar(8) not null comment '用户名',
  nick_name varchar (8) not null comment '昵称',
  age tinyint unsigned not null comment '年龄',
  address varchar (32) not null  comment '家庭地址',
  password varchar (32) not null comment '密码',
  version bigint unsigned not null default 0 comment '用于乐观锁字段',
  delete_flag varchar (1) NOT NULL default '0' comment '删除标志',
  create_time datetime not null default current_timestamp comment '创建时间',
  update_time datetime not null default current_timestamp comment '修改时间',
  primary key (id) using btree,
  unique key `idx_name` (`name`) using btree,
  key `idx_nick_name` (nick_name) using btree
)engine=Innodb auto_increment=10 default charset=utf8mb4;
