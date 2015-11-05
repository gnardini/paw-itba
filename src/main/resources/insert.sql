insert into neighbourhood values(1, 'Caballito');
insert into neighbourhood values(2, 'Palermo');
insert into neighbourhood values(3, 'Puerto Madero');
insert into neighbourhood values(4, 'Parque Patricios');
insert into neighbourhood values(5, 'Retiro');
insert into neighbourhood values(6, 'Belgrano');
insert into users values(1, 'admin address', 2, 10, 1999, 'admin@admin.admin', 'admin', 'admin', 'admin', 2, 1);
insert into users values(2, 'manager one address', 2, 10, 1999, 'manager@one.com', 'Manager', 'One', 'manager', 1, 2);
insert into users values(5, 'user one address', 2, 10, 1999, 'user@one.com', 'User', 'One', 'user', 0, 3);
insert into users values(10, '1 Hacker Way', 10, 11, 1990, 'mdesanti@itba.edu.ar', 'Matias', 'De Santi', '123123123', 0, null);
insert into users values(11, '123123123', 10, 11, 1990, 'mdesanti+1@itba.edu.ar', 'Matias', 'B', '123123123', 0, null);
insert into users values(12, '123', 10, 11, 1990, 'mdesanti+3@itba.edu.ar', 'Matias', 'De Santi', '123123123', 0, null);
ALTER SEQUENCE users_id_seq restart with 13;
insert into restaurant values(2, 'Corrientes y Florida', 8, 10, 'Dueños de la hamburguesa más rica del planeta', 'Comida Rápida', 100, 'Burger King', 8);
insert into restaurant values(1, 'Corrientes y Alem', 0, 15, 'El mejor lugar de comidas rápidas', 'Comida Rápida', 120, 'McDonalds', 6);
insert into restaurant values(3, '9 de Julio y Córdoba', 10, 30, 'Pizza al instante', 'Pizzeria', 120, 'Lista la Pizza', 22);
insert into restaurant values(9, 'Av Siempreviva 742', 15, 0, 'Prueba', 'Comida Rápida', 100, 'Prueba', 12);
ALTER SEQUENCE restaurant_id_seq restart with 10;
 