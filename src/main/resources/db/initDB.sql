create table if not exists "Visit"
(
    visit_id serial not null
        constraint visits_pk
            primary key,
    visit_date date not null,
    visit_description varchar(3000) not null,
    patient_personal_id integer not null
);

alter table "Visit" owner to postgres;

create unique index if not exists visits_visit_id_uindex
    on "Visit" (visit_id);

create table if not exists clinic_address
(
    id serial not null
        constraint clinic_address_pkey
            primary key,
    city varchar(255),
    street varchar(255),
    zip_code varchar(255) not null
);

alter table clinic_address owner to postgres;

create table if not exists clinic
(
    id serial not null
        constraint clinic_pkey
            primary key,
    clinic_name varchar(250) not null,
    clinic_address_id integer
        constraint fksxfutk1ey0i2nkqrbkito890e
            references clinic_address
);

alter table clinic owner to postgres;

create table if not exists doctors
(
    id serial not null
        constraint doctors_pkey
            primary key,
    first_name varchar(25) not null,
    last_name varchar(30) not null,
    personal_id varchar(11) not null
        constraint uk_i1h27eim867s4txnwx6t6xelh
            unique,
    telephone varchar(255),
    medical_license_number integer
        constraint uk_lickujod7hiva6es7ch3drek0
            unique,
    doctor_id integer not null
        constraint fklsvfo3bo0xxdby8q3xvcrwgkt
            references clinic,
    doctors_id integer not null
        constraint fka82kosq3tyqon120490pflfbc
            references doctors
);

alter table doctors owner to postgres;

create unique index if not exists doctors_doctor_id_uindex
    on doctors (doctor_id);

create unique index if not exists doctors_medical_license_number_uindex
    on doctors (medical_license_number);

create unique index if not exists doctors_personal_id_uindex
    on doctors (personal_id);

create table if not exists patient_address
(
    id serial not null
        constraint patient_address_pkey
            primary key,
    city varchar(255) not null,
    flat_number varchar(255),
    street varchar(255) not null
);

alter table patient_address owner to postgres;

create table if not exists patients
(
    patient_id serial not null,
    id integer not null,
    first_name varchar(25) not null,
    last_name varchar(30) not null,
    personal_id varchar(11) not null
        constraint uk_n5by02wrb83du6sbkbvbq0508
            unique,
    telephone varchar(255),
    email varchar(255) not null
        constraint uk_a370hmxgv0l5c9panryr1ji7d
            unique,
    patient_address_id integer not null
        constraint uk_r8x2fnxnh8w8m9emar3hlgglo
            unique
        constraint fkecpna6p5hm1f9fdo8785rnfxw
            references patient_address,
    constraint patients_pkey
        primary key (patient_id, id),
    constraint ukqhp4an1df3425bw3ansmq0ii7
        unique (personal_id, email)
);

alter table patients owner to postgres;

create unique index if not exists patients_patient_id_uindex
    on patients (patient_id);

create table if not exists specialties
(
    specialty_id serial not null
        constraint specialties_pkey
            primary key,
    specialty_name varchar(255) not null
        constraint uk_4wrx7xnel5dw9ax6cp5m9cddn
            unique,
    medical_doctors_id integer not null
        constraint fkft4axv473i93j2aw1n3bohtgm
            references doctors
);

alter table specialties owner to postgres;

create unique index if not exists specialties_specialty_id_uindex
    on specialties (specialty_id);

create table if not exists users
(
    username varchar(255) not null
        constraint users_pkey
            primary key,
    enabled boolean,
    password varchar(12) not null
);

alter table users owner to postgres;

create table if not exists roles
(
    id serial not null
        constraint roles_pkey
            primary key,
    role varchar(255) not null,
    username varchar(255)
        constraint uki3menmmjnp0c2i4vnkwtm8fiu
            unique
        constraint fkqmykg2mabwkk1vhpc4526g44k
            references users
);

alter table roles owner to postgres;

create unique index if not exists roles_role_id_uindex
    on roles (id);

create unique index if not exists users_user_id_uindex
    on users (username);

create unique index if not exists users_username_uindex
    on users (username);

create table if not exists visit
(
    id serial not null
        constraint visit_pkey
            primary key,
    patient_personalid integer,
    visit_date date not null,
    visit_description varchar(1000) not null,
    patient_id integer,
    visit_id integer,
    constraint fkciyhxbsde6yqb52ryx8o1nl7l
        foreign key (patient_id, visit_id) references patients
);

alter table visit owner to postgres;

create table if not exists patients_visits
(
    patients_patient_id integer not null,
    patients_id integer not null,
    visits_id integer not null
        constraint uk_i9hhg62s0qpxs247bqrqrqaqd
            unique
        constraint fk65sqr5trsit0i8jg0e1milqu1
            references visit,
    constraint patients_visits_pkey
        primary key (patients_patient_id, patients_id, visits_id),
    constraint fk5c3vm5bploonjd3k2mffqemgb
        foreign key (patients_patient_id, patients_id) references patients
);

alter table patients_visits owner to postgres;

