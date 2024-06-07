
create sequence if not exists users_sequence;
create table if not exists users (
    id bigint not null primary key default nextval('users_sequence'),
    name varchar(150) not null,
    username varchar(100) not null unique,
    email varchar(150) not null unique,
    phone varchar(13) null,
    password varchar(255) not null,
    enabled boolean not null default true,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp null
);

create sequence if not exists role_sequence;
create table if not exists role (
    id bigint not null primary key default nextval('role_sequence'),
    name varchar(100) not null unique,
    enabled boolean not null default true
);

create table if not exists users_roles (
    users_id bigint not null,
    role_id bigint not null,
    constraint fk_users_id foreign key(users_id) references users(id),
    constraint fk_role_id foreign key(role_id) references role(id),
    primary key (users_id, role_id)
);

insert into role(id, name) values (1, 'ADMIN');
insert into role(id, name) values (2, 'EDITOR');
insert into role(id, name) values (3, 'MEMBER');

insert into users(id, name, username, email, password) values(1, 'Admin', 'admin', 'admin@pricecatalog.com', '$2y$10$jQ3ianFML4ZOITErl/2SUepqf3FMqORT6ON42HXgCiDw5kjyqpe8e');

insert into users_roles(users_id, role_id) values(1, 1);