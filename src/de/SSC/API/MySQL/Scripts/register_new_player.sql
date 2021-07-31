insert into players values
(
  uuid(),
  false
  "BitPaw",
  null,
  200,
  "I",
  null,
  default,
  default,
  null,
  null,
  null
);


insert into players values
(
  "d4f4441a-ba31-4951-91fd-80c3cfe1df81",
  false,
  "truesub",
  null,
  default,
  "I",
  "0.0.0.0",
  default,
  default,
  null,
  null,
  null
);




select 
UUID, 
OP, 
Name, CustomName, Money, 
RankGroup, IP, Registered, LastSeen 
from players group by Name;