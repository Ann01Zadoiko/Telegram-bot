create table if not exists users(
    id bigint auto_increment primary key,
    chat_id int not null,
    full_name varchar(150),
    departure varchar(50),
    password varchar(20)
);

create table if not exists messages(
	id int auto_increment primary key,
    description text not null
);

create table if not exists list_of_employees(
	id int auto_increment primary key,
    date date not null,
    id_user bigint,
-- at_word boolean default false,
 --   number_of_list bigint not null,
    foreign key (id_user) references users(id)
);