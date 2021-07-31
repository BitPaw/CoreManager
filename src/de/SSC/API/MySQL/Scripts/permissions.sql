create table if not exists permissions
(
  Permission varchar(60) not null primary key,
  RankGroup varchar(20)
);