create table users
(
    uid         int         not null
        primary key AUTO_INCREMENT,
    slogan      varchar(255) null,
    username    varchar(64)  not null UNIQUE,
    password    varchar(64)  null,
    remainMoney double       null,
    avatar      varchar(64)  not null
);
create table books
(
    bid     int          not null   primary key AUTO_INCREMENT,
    name    varchar(64)  not null,
    comment varchar(2048) null,
    pic     varchar(128) null,
    author  varchar(32)  not null,
    price   double       not null,
    stock   int          null,
    tag     varchar(20)  null,
    sales   int          not null,
    isbn    varchar(32)  not null
);
create table cartbooks
(
    bid    int not null,
    cid    int not null
        primary key AUTO_INCREMENT,
    uid    int not null,
    number int not null,
    selected BOOLEAN not null,
    constraint bookid
        foreign key (bid) references books (bid),
    constraint userid
        foreign key (uid) references users (uid)
);
create table orderbooks
(
    oid        int         not null
        primary key AUTO_INCREMENT,
    uid        int         not null,
    bid        int         not null,
    number     int         not null,
    dateTime   datetime    null,
    totalPrice double      null,
    address    varchar(64) null,
    telephone  varchar(20) null,
    receiver varchar(20) null,
    constraint order_user_uid
        foreign key (uid) references users (uid),
    constraint  order_books_bid
        foreign key (bid) references books (bid)
);

