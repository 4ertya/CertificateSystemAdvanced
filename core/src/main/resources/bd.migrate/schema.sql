create sequence hibernate_sequence;

create table certificates
(
    id               bigserial not null,
    create_date      timestamp with time zone,
    description      varchar(255),
    duration         integer,
    last_update_date timestamp with time zone,
    name             varchar(255),
    price            numeric(19, 2),
    constraint certificates_pkey
        primary key (id)
);

create table tags
(
    id   bigserial not null,
    name varchar(255),
    constraint tags_pkey
        primary key (id),
    constraint uk_t48xdq560gs3gap9g7jg36kgc
        unique (name)
);

create table users
(
    id      bigserial not null,
    name    varchar(255),
    surname varchar(255),
    constraint users_pkey
        primary key (id)
);


create table orders
(
    id         bigserial not null,
    cost       numeric(19, 2),
    order_date timestamp,
    user_id    bigint,
    constraint orders_pkey
        primary key (id)
);

create table orders_certificates
(
    orders_id       bigint not null,
    certificates_id bigint not null,
    constraint fk80siohi4i1dhggs033y44gh97
        foreign key (orders_id) references orders
            on delete cascade,
    constraint fkp6i5e02pjwpiqhxgr6pacoktw
        foreign key (certificates_id) references certificates
            on delete cascade
);


create table certificates_tags
(
    certificates_id bigint not null,
    tags_id         bigint not null,
    constraint certificates_tags_pkey
        primary key (certificates_id, tags_id),
    constraint fk7rhfn769tsnnq5e0mbtnglh6w
        foreign key (certificates_id) references certificates,
    constraint fks9jnjgsosxmo7mrpqyipfag78
        foreign key (tags_id) references tags
            on delete cascade
);



create table revinfo
(
    rev      integer not null,
    revtstmp bigint,
    constraint revinfo_pkey
        primary key (rev)
);

create table certificates_audit_log
(
    id               bigint  not null,
    rev              integer not null,
    revtype          smallint,
    create_date      timestamp with time zone,
    description      varchar(255),
    duration         integer,
    last_update_date timestamp with time zone,
    name             varchar(255),
    price            numeric(19, 2),
    constraint certificates_audit_log_pkey
        primary key (id, rev),
    constraint fkqwbymh7o15dgqmsok0ssh854k
        foreign key (rev) references revinfo
);

create table certificates_tags_audit_log
(
    rev             integer not null,
    certificates_id bigint  not null,
    tags_id         bigint  not null,
    revtype         smallint,
    constraint certificates_tags_audit_log_pkey
        primary key (rev, certificates_id, tags_id),
    constraint fk94nfdnxer5aiw9oivq3f191gy
        foreign key (rev) references revinfo
);

create table orders_audit_log
(
    id         bigint  not null,
    rev        integer not null,
    revtype    smallint,
    cost       numeric(19, 2),
    order_date timestamp with time zone,
    user_id    bigint,
    constraint orders_audit_log_pkey
        primary key (id, rev),
    constraint fkbvower78ft1i7rt78rujoky5s
        foreign key (rev) references revinfo
);

create table orders_certificates_audit_log
(
    rev             integer not null,
    orders_id       bigint  not null,
    certificates_id bigint  not null,
    revtype         smallint,
    constraint orders_certificates_audit_log_pkey
        primary key (rev, orders_id, certificates_id),
    constraint fkgphj0sjolumndb3cx0kqxtodh
        foreign key (rev) references revinfo
);

create table tags_audit_log
(
    id      bigint  not null,
    rev     integer not null,
    revtype smallint,
    name    varchar(255),
    constraint tags_audit_log_pkey
        primary key (id, rev),
    constraint fki9wtlk9lxo7eiek366uku65he
        foreign key (rev) references revinfo
);

