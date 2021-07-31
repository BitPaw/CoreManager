create table if not exists warps
(
  Name  varchar(20) not null primary key,
  World varchar(20) not null,
  X     float not null,
  Y     float not null,
  Z     float not null,
  yaw   float not null,
  pitch float not null
);