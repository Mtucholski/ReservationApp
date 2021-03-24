create table if not exists address
(

    address_id        int primary key,
    city              varchar(100) not null,
    street            varchar(30)  not null,
    street_number     varchar(30)  not null,
    zip_code          varchar(10)  not null,
    clinic_id         int          not null,
    validation_status varchar(100)
);

create table if not exists clinic
(
    clinic_id int not null  primary key,
    creation_date date not null ,
    close_date date,
    clinic_type varchar(20) not null,
    clinic_status varchar(20) not null ,
    clinic_name varchar(100) not null ,
    validation_status varchar(100) not null ,
    doctor_id int not null,
    address_id int not null ,
    visit_id int not null ,
    patient_id int not null
);

create table if not exists doctor(

    doctor_id int not null primary key ,
    specialty_name varchar(100) not null ,
    licence_number integer not null ,
    validation_status varchar(100) not null,
    clinic_id int
);

create table if not exists patient(
    id int not null primary key,
    first_name varchar(100) not null ,
    last_name varchar(100)  not null ,
    personal_id int unique not null ,
    social_security_number int not null ,
    identity_cart_type varchar(30) not null ,
    email varchar(50) unique not null ,
    validation_status varchar(50) not null ,
    clinic_id int,
    visit_id int
);

create table if not exists visit(
    visit_id int not null  primary key,
    visit_date datetime not null ,
    visit_description varchar(1000) not null ,
    validation_status varchar(30) not null ,
    patient_id int,
    doctor_id int
);
