create table if not exists address
(
    address_id    serial      not null
        constraint pk_address
            primary key,
    city          varchar(25) not null,
    street        varchar(30) not null,
    street_number numeric(4)  not null,
    flat_number   numeric(3)
);

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
            primary key
        constraint doctors_specialties_specialty_id_fk
            references specialties
            on update cascade on delete cascade,
    first_name varchar(25) not null,
    "last_name " varchar(30) not null,
    personal_id varchar not null,
    medical_license_number integer not null,
    email varchar not null,
    telephone varchar not null
);

alter table doctors owner to postgres;

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
            primary key
        constraint patients_address_address_id_fk
            references address
            on update cascade on delete cascade
        constraint patients_doctors_doctor_id_fk
            references doctors
            on update cascade on delete cascade,
    first_name varchar(25) not null,
    last_name varchar(30) not null,
    personal_id varchar(11) not null,
    email varchar not null,
    telephone varchar not null,
    clinic_name varchar not null
);



alter table doctors
    add constraint doctors_patients_patient_id_fk
        foreign key (doctor_id) references patients
            on update cascade;

create table if not exists clinic
(
    clinic_id serial not null
        constraint clinic_pk
            primary key
        constraint clinic_address_address_id_fk
            references address
            on update cascade on delete cascade
        constraint clinic_doctors_doctor_id_fk
            references doctors
            on update cascade on delete cascade
        constraint clinic_patients_patient_id_fk
            references patients
            on update cascade on delete cascade,
    clinic_name varchar(2000) not null
);

alter table doctors
    add constraint doctors_clinic_clinic_id_fk
        foreign key (doctor_id) references clinic;

create unique index if not exists clinic_clinic_id_uindex
    on clinic (clinic_id);

create unique index if not exists patients_patient_id_uindex
    on patients (patient_id);

create table if not exists visits
(
    visit_id serial not null
        constraint visits_pk
            primary key
        constraint visits_patients_patient_id_fk
            references patients
            on update cascade,
    visit_date date not null,
    visit_description varchar(3000) not null
);

create unique index if not exists visits_visit_id_uindex
    on visits (visit_id);

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
    username varchar(20) not null
        constraint roles_users_username_fk
            references users (username)
            on update cascade on delete cascade,
    role varchar not null
);

create unique index if not exists roles_role_id_uindex
    on roles (role_id);

