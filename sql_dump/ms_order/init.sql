create table hibernate_sequence
(
	next_val bigint null
);

INSERT INTO `ms-order`.hibernate_sequence (next_val) VALUES (3);
INSERT INTO `ms-order`.hibernate_sequence (next_val) VALUES (3);

create table orders
(
	id bigint not null
		primary key,
	created_at datetime(6) null,
	updated_at datetime(6) null,
	currency varchar(255) null,
	status varchar(255) not null,
	total_amount decimal(19,2) null
);

INSERT INTO `ms-order`.orders (id, created_at, updated_at, currency, status, total_amount) VALUES (1, '2020-10-13 10:41:36.626000', '2020-10-13 10:41:36.628000', 'IDR', 'WAITING_FOR_PAYMENT', 500000.00);

create table order_detail
(
	id bigint not null
		primary key,
	created_at datetime(6) null,
	updated_at datetime(6) null,
	base_price decimal(19,2) null,
	item_id bigint not null,
	qty int not null,
	total decimal(19,2) null,
	order_id bigint null,
	constraint FKrws2q0si6oyd6il8gqe2aennc
		foreign key (order_id) references orders (id)
);


INSERT INTO `ms-order`.order_detail (id, created_at, updated_at, base_price, item_id, qty, total, order_id) VALUES (2, '2020-10-13 10:41:36.686000', '2020-10-13 10:41:36.686000', 50000.00, 1, 10, 500000.00, 1);