create table agendas_schema.grouping
(
    grouping_id uuid primary key not null,
    name text not null,
    user_id uuid not null,
    color text,
    unique(name, user_id)
);

create table agendas_schema.scheme
(
    scheme_id uuid primary key not null,
    grouping_id uuid references grouping(grouping_id),
    user_id uuid not null,
    name text not null,
    interval text not null,
    agendas_per_interval integer not null,
    is_countdown boolean default false,
    is_enabled boolean default true,
    unique(grouping_id, user_id, name)
);

create table agendas_schema.agenda
(
    agenda_id uuid primary key not null,
    scheme_id uuid references scheme(scheme_id),
    name text not null,
    sequence integer not null,
    iterations integer not null,
    repetitions integer not null,
    target_value integer not null,
    target_description integer not null
);

grant select, insert, update, delete on all tables in schema agendas_schema to agenda_app_user;