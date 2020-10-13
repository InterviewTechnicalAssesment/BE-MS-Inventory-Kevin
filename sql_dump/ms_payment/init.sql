create table hibernate_sequence
(
	next_val bigint null
);

INSERT INTO `ms-payment`.hibernate_sequence (next_val) VALUES (2);

create table payment
(
	id bigint not null
		primary key,
	created_at datetime(6) null,
	updated_at datetime(6) null,
	amount decimal(19,2) not null,
	currency varchar(255) not null,
	order_id bigint not null,
	status varchar(255) not null,
	trx_id varchar(255) null
);

INSERT INTO `ms-payment`.payment (id, created_at, updated_at, amount, currency, order_id, status, trx_id) VALUES (1, '2020-10-13 10:07:47.262000', '2020-10-13 10:07:47.262000', 500000.00, 'IDR', 1, 'WAITING_FOR_PAYMENT', 'TESTTRXCODE');