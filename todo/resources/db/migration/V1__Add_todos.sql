create table todos (
  id        serial,
  title     varchar not null,
  ordering  integer not null,
  completed boolean not null
)
