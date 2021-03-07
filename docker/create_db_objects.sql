create schema agendas_schema;
create user agenda_app_user with login password 'schemecrud';
grant connect on database scheme_agenda_db to agenda_app_user;
grant usage on schema agendas_schema to agenda_app_user;