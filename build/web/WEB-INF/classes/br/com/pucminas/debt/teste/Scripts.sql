insert into Usuario (LogUsu,EmaUsu,isHab,NomUsu,SenUsu) values ('admin','admin@gmail.com',true,'Administrador','admin');
insert into Autorizacao (NomAut) values ('ROLE_USER');
insert into Autorizacao (NomAut) values ('ROLE_ADMIN');
insert into UsuarioAutorizacao (LogUsu,NomAut) values ('admin','ROLE_ADMIN');
insert into UsuarioAutorizacao (LogUsu,NomAut) values ('admin','ROLE_USER');

