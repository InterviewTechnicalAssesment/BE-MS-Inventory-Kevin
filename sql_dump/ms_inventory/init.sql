create table inventory
(
	id bigint not null
		primary key,
	created_at datetime(6) null,
	updated_at datetime(6) null,
	currency varchar(255) not null,
	price decimal(19,2) not null,
	product_name varchar(255) not null,
	quantity int not null
);

create table hibernate_sequence
(
	next_val bigint null
);

INSERT INTO `ms-inventory`.hibernate_sequence (next_val) VALUES (6);

INSERT INTO `ms-inventory`.inventory (id, created_at, updated_at, currency, price, product_name, quantity) VALUES (1, '2020-10-12 19:21:02', null, 'IDR', 50000.00, 'Baju', 20);
INSERT INTO `ms-inventory`.inventory (id, created_at, updated_at, currency, price, product_name, quantity) VALUES (2, '2020-10-12 19:21:02', null, 'IDR', 150000.00, 'Celana Jeans', 30);
INSERT INTO `ms-inventory`.inventory (id, created_at, updated_at, currency, price, product_name, quantity) VALUES (3, '2020-10-12 19:21:02', null, 'IDR', 50000.00, 'Celana Katun', 25);
INSERT INTO `ms-inventory`.inventory (id, created_at, updated_at, currency, price, product_name, quantity) VALUES (4, '2020-10-12 19:21:02', null, 'IDR', 150000.00, 'Set Piyama', 50);
INSERT INTO `ms-inventory`.inventory (id, created_at, updated_at, currency, price, product_name, quantity) VALUES (5, '2020-10-12 19:21:02', null, 'IDR', 30000.00, 'Topi', 100);