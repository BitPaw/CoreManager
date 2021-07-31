create table if not exists players
(
  UUID       varchar(36) not null primary key,
  Name       varchar(20) not null,
  CustomName varchar(40), 
  Money      float    not null default 150,
  RankGroup  varchar(15) not null default "I",
  IP		 varchar(15), 
  Registered DATETIME not null DEFAULT CURRENT_TIMESTAMP,
  LastSeen   DATETIME not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  HomeLocationX float, 
  HomeLocationY float,
  HomeLocationZ float
);

insert into players values
(
  uuid(),
  "BitPaw",
  "&6BitPaw",
  200,
  "V",
  null,
  default,
  default,
  null,
  null,
  null
);

