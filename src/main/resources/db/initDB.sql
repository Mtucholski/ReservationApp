create table if not exists specialties
(
    specialty_id serial not null
        constraint specialties_pk
            primary key,
    specialty_name varchar(250) not null
);

create unique index if not exists specialties_specialty_id_uindex
    on specialties (specialty_id);

create table if not exists doctors
(
    doctor_id serial not null
        constraint doctors_pk
            primary key,
    first_name varchar(25) not null,
    "last_name " varchar(30) not null,
    personal_id varchar not null,
    medical_license_number integer not null,
    email varchar not null,
    telephone varchar not null
);


create unique index if not exists doctors_doctor_id_uindex
    on doctors (doctor_id);

create unique index if not exists doctors_medical_license_number_uindex
    on doctors (medical_license_number);

create unique index if not exists doctors_personal_id_uindex
    on doctors (personal_id);

create table if not exists patients
(
    patient_id serial not null
        constraint patients_pk
            primary key,
    first_name varchar(25) not null,
    last_name varchar(30) not null,
    personal_id varchar(11) not null,
    email varchar not null,
    telephone varchar not null,
    clinic_name varchar not null
);

create unique index if not exists patients_patient_id_uindex
    on patients (patient_id);

create table if not exists "Visit"
(
    visit_id serial not null
        constraint visits_pk
            primary key,
    visit_date date not null,
    visit_description varchar(3000) not null,
    patient_personal_id integer not null
);

create unique index if not exists visits_visit_id_uindex
    on "Visit" (visit_id);

create table if not exists users
(
    user_id serial not null
        constraint users_pk
            primary key,
    username varchar not null,
    role varchar(20) not null,
    password varchar not null,
    enabled boolean default true
);

create unique index if not exists users_user_id_uindex
    on users (user_id);

create unique index if not exists users_username_uindex
    on users (username);

create table if not exists roles
(
    role_id serial not null
        constraint roles_pk
            primary key,
    username varchar(20) not null,
    role varchar not null
);

create unique index if not exists roles_role_id_uindex
    on roles (role_id);

create table if not exists patient_address
(
    patient_address_id serial not null,
    street varchar not null,
    flat_number varchar,
    city varchar not null
);

create unique index if not exists patient_address_patient_address_id_uindex
    on patient_address (patient_address_id);

create table if not exists clinic_address
(
    clinic_address_id serial not null,
    street varchar,
    zip_code varchar
);
create table if not exists clinic
(
    clinic_id serial not null
        constraint clinic_pk
            primary key,
    clinic_name varchar(2000) not null
);

create unique index if not exists clinic_clinic_id_uindex
    on clinic (clinic_id);
create unique index if not exists clinic_address_clinic_address_id_uindex
    on clinic_address (clinic_address_id);

