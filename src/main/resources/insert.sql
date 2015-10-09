insert into users (id, firstname, lastname, address, email, birthday, birthmonth, birthyear, pass, type) values(1, 'admin', 'admin', 'admin address', 'admin@admin.admin', 2, 10, 1999, 'admin', 'admin');
insert into users (id, firstname, lastname, address, email, birthday, birthmonth, birthyear, pass, type) values(2, 'Manager', 'One', 'manager one address', 'manager@one.com', 2, 10, 1999, 'manager', 'manager');
insert into users (id, firstname, lastname, address, email, birthday, birthmonth, birthyear, pass, type) values(3, 'Manager', 'Two', 'manager two address', 'manager@two.com', 2, 10, 1999, 'manager', 'manager');
insert into users (id, firstname, lastname, address, email, birthday, birthmonth, birthyear, pass, type) values(4, 'Manager', 'Three', 'manager three address', 'manager@three.com', 2, 10, 1999, 'manager', 'manager');
insert into users (id, firstname, lastname, address, email, birthday, birthmonth, birthyear, pass, type) values(5, 'User', 'One', 'user one address', 'user@one.com', 2, 10, 1999, 'user', 'normal');
insert into users (id, firstname, lastname, address, email, birthday, birthmonth, birthyear, pass, type) values(6, 'User', 'Two', 'user two address', 'user@two.com', 2, 10, 1999, 'user', 'normal');
insert into users (id, firstname, lastname, address, email, birthday, birthmonth, birthyear, pass, type) values(7, 'User', 'Three', 'user three address', 'user@three.com', 2, 10, 1999, 'user', 'normal');
insert into restaurant (id, name, address, opening_hours, delivery_cost, min_cost, menu_type, description) values (1, 'McDonalds', 'Corrientes y Alem', 'De 6:00 a 23:30', 10, 120, 'Comida Rápida', 'El mejor lugar de comidas rápidas');
insert into restaurant (id, name, address, opening_hours, delivery_cost, min_cost, menu_type, description) values (2, 'Burger King', 'Corrientes y Florida', 'De 5:30 a 23:30', 0, 100, 'Comida Rápida', 'Dueños de la hamburguesa más rica del planeta');
insert into managers values (2, 1);
insert into managers values (3, 2);
insert into comments values (2, 1, 5, 'Es el mejor restoran de la historia');
insert into comments values (5, 1, 3, 'La comida llegó tibia');
insert into comments values (4, 2, 2, 'Las papas estaban húmedas');
insert into comments values (5, 2, 5, 'Increíble servicio');
insert into dish values (1, 1, 'BigMac', 'Doble hamburguesa con lechuga, tomate y pepinos', 50, 'main');
insert into dish values (2, 1, 'Papas Fritas Medianas', 'Papas fritas saladas', 20, 'entry');
insert into dish values (3, 1, 'Gaseosa Mediana', 'Bebida refrescante de la marca Coca-Cola', 20, 'drink');
insert into dish values (4, 1, 'Sundae', 'Helado de crema americana o dulce de leche', 15, 'dessert');
insert into dish values (5, 2, 'Whopper Doble', 'Doble hamburguesa con lechuga, tomate y pepinos', 40, 'main');
insert into dish values (6, 2, 'Papas Fritas Medianas', 'Papas fritas saladas', 18, 'entry');

ALTER SEQUENCE users_id_seq restart with 10;
ALTER SEQUENCE restaurant_id_seq restart with 10;
ALTER SEQUENCE dish_id_seq restart with 10;
	