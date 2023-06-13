DROP TABLE IF EXISTS public.bookings;
DROP TABLE IF EXISTS public.rooms;
DROP TABLE IF EXISTS public.hotels;
DROP TABLE IF EXISTS public.users;
DROP TABLE IF EXISTS public.roles_server_permissions;
DROP TABLE IF EXISTS public.server_permissions;
DROP TABLE IF EXISTS public.roles CASCADE;
DROP TABLE IF EXISTS public.countries;
DROP TABLE IF EXISTS public.countries;
DROP TABLE IF EXISTS public.roles_users;

(
    id               SERIAL       NOT NULL PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    number_of_floors INT          NOT NULL
);

CREATE TABLE public.rooms
(
    id          SERIAL NOT NULL PRIMARY KEY,
    room_number INT    NOT NULL,
    hotel_id    INT    NOT NULL,
    number_of_beds INT NOT NULL,
    base_price NUMERIC NOT NULL,
    FOREIGN KEY (hotel_id) REFERENCES public.hotels (id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE public.bookings
(
    id             SERIAL       NOT NULL PRIMARY KEY,
    room_id        INT          NOT NULL,
    arrival_date   DATE         NOT NULL,
    departure_date DATE         NOT NULL,
    user_id INT not null,
    foreign key (user_id) references public.users (id) ON DELETE RESTRICT ON UPDATE restrict,
    FOREIGN KEY (room_id) REFERENCES public.rooms (id) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE UNIQUE INDEX CONCURRENTLY u_hotel_id_room_number
    ON rooms (hotel_id, room_number);

ALTER TABLE rooms
    ADD CONSTRAINT u_hotel_id_room_number
        UNIQUE USING INDEX u_hotel_id_room_number;

CREATE TABLE public.roles (
                              id SERIAL PRIMARY KEY,
                              rolename VARCHAR(255) NOT NULL,
                              xml_client_permission VARCHAR(10485760) NOT NULL DEFAULT ('<?xml version="1.0" encoding="UTF-8"?><security></security>')
);

CREATE TABLE public.countries (
                                  id SERIAL PRIMARY KEY,
                                  name VARCHAR(255) NOT NULL
);


CREATE TABLE public.users (
  id SERIAL PRIMARY KEY,
  login_name VARCHAR(255) NOT NULL UNIQUE,
  user_name VARCHAR(255) NOT NULL,
  surname1 VARCHAR(255) NOT NULL,
  surname2 VARCHAR(255),
  id_document VARCHAR(255) NOT NULL,
  country_id INT NOT NULL,
  phone_number VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  user_password VARCHAR(255) NOT null,
  foreign key (country_id) references public.countries(id)
    on delete restrict
    on update cascade
);

CREATE TABLE public.server_permissions (
    id SERIAL PRIMARY KEY,
    method VARCHAR(255) NOT NULL
);

CREATE TABLE public.roles_server_permissions (
    role_id INT,
    server_permission_id INT,
    PRIMARY KEY (role_id, server_permission_id),
    FOREIGN KEY (role_id) references public.roles(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (server_permission_id) references public.server_permissions(id)
    ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE public.roles_users (
    id SERIAL PRIMARY KEY,
    user_id INT,
    role_id INT,
    FOREIGN KEY(login_name) references public.users(login_name) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(role_id) references public.roles(role_id) ON DELETE CASCADE ON UPDATE CASCADE
)