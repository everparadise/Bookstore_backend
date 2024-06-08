create table books
(
    price   int          not null,
    sales   int          null,
    stock   int          null,
    bid     bigint auto_increment
        primary key,
    author  varchar(255) not null,
    comment varchar(255) null,
    isbn    varchar(255) not null,
    name    varchar(255) not null,
    pic     varchar(255) not null,
    tag     varchar(255) null
);

create table users
(
    remain_money int                    not null,
    uid          bigint auto_increment
        primary key,
    avatar       varchar(255)           not null,
    email        varchar(255)           not null,
    password     varchar(255)           not null,
    slogan       varchar(255)           null,
    username     varchar(255)           not null,
    role         enum ('USER', 'ADMIN') not null,
    constraint UK_r43af9ap4edm43mmtq01oddj6
        unique (username)
);

create table cartbooks
(
    number    int         not null,
    bid       bigint      null,
    cart_time datetime(6) not null,
    cid       bigint auto_increment
        primary key,
    uid       bigint      null,
    constraint FKkscap7b12a33ftn66d3htp2mf
        foreign key (uid) references users (uid),
    constraint FKqnyt8j65c8j8ahgw8vsoj9iky
        foreign key (bid) references books (bid)
);

create table orders
(
    order_price    int          not null,
    oid            bigint auto_increment
        primary key,
    order_number   datetime(6)  not null,
    uid            bigint       null,
    order_address  varchar(255) not null,
    order_phone    varchar(255) not null,
    order_receiver varchar(255) not null,
    constraint FKbdolj6vr67tqh6wgsl44mur9y
        foreign key (uid) references users (uid)
);

create table orderbooks
(
    number   int    not null,
    book_bid bigint null,
    oid      bigint auto_increment
        primary key,
    order_id bigint null,
    constraint FKlc5d8i7le849h1cne23fb88iw
        foreign key (book_bid) references books (bid),
    constraint FKm8nhivt8p02524cpxaqrolc1r
        foreign key (order_id) references orders (oid)
);

