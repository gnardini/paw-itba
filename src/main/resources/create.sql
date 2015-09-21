create table users
(
id serial not null primary key,
firstname varchar(50) not null,
lastname varchar(50) not null,
address varchar(250) not null,
email varchar(60) not null unique,
birthday integer not null,
birthmonth integer not null,
birthyear integer not null,
pass varchar(20) not null,
type varchar(50) not null
);

create table restaurant
(
id serial not null primary key,
name varchar(250) not null,
address varchar(250) not null,
opening_hours varchar(250) not null,
delivery_cost integer not null,
min_cost integer not null,
menu_type varchar(30) not null, 
description varchar(140) not null
);

create table dish
(
id serial not null primary key,
restaurantid integer not null,
name varchar(100) not null,
description varchar(255) not null,
price integer not null,
type varchar(50) not null,
foreign key (restaurantid) references restaurant(id) on delete cascade
);

create table orders
(
id serial not null primary key,
userid integer not null,
restaurantid integer not null,
made date not null,
foreign key (userid) references users(id) on delete cascade,
foreign key (restaurantid) references restaurant(id) on delete cascade
);

create table orderdetail
(
orderid integer not null,
name varchar(100) not null,
amount integer not null,
price integer not null,
foreign key (orderid) references orders(id) on delete cascade
);

create table managers
(
userid integer not null,
restaurantid integer not null,
foreign key (userid) references users(id) on delete cascade,
foreign key (restaurantid) references restaurant(id) on delete cascade
);

create table comments
(
userid integer not null,
restaurantid integer not null,
rating integer not null,
text varchar(255) not null,
foreign key (userid) references users(id) on delete cascade,
foreign key (restaurantid) references restaurant(id) on delete cascade
);
