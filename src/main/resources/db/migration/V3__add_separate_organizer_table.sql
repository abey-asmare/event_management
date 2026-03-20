
--
-- create table users
-- (
--     created_at timestamp(6),
--     updated_at timestamp(6),
--     id         uuid not null
--         primary key,
--     email      varchar(255)
--         unique,
--     name       varchar(255),
--     role       varchar(255)
--         constraint users_role_check
--             check ((role)::text = ANY
--                    ((ARRAY ['USER'::character varying, 'ORGANIZER'::character varying, 'ADMIN'::character varying])::text[]))
-- );
--
-- alter table users
--     owner to postgres;

create table organizers
(
    created_at        timestamp(6),
    updated_at        timestamp(6),
    id                uuid    not null
        primary key,
    user_id           uuid    not null
        unique
        constraint fk7kpidig5uothy6p9ttywnif6l
            references users,
    organization_name varchar(255),
    is_verified       boolean not null
);

alter table organizers
    owner to postgres;

grant delete, insert, references, select, trigger, truncate, update on organizers to anon;

grant delete, insert, references, select, trigger, truncate, update on organizers to authenticated;

grant delete, insert, references, select, trigger, truncate, update on organizers to service_role;

grant delete, insert, references, select, trigger, truncate, update on users to anon;

grant delete, insert, references, select, trigger, truncate, update on users to authenticated;

grant delete, insert, references, select, trigger, truncate, update on users to service_role;
--
-- create table venues
-- (
--     capacity    integer not null,
--     created_at  timestamp(6),
--     updated_at  timestamp(6),
--     id          uuid    not null
--         primary key,
--     city        varchar(255),
--     country     varchar(255),
--     name        varchar(255),
--     postal_code varchar(255),
--     street      varchar(255)
-- );
--
-- alter table venues
--     owner to postgres;
--
-- create table events
-- (
--     capacity     integer,
--     seat_based   boolean not null,
--     ticket_price double precision,
--     created_at   timestamp(6),
--     end_time     timestamp(6),
--     start_time   timestamp(6),
--     updated_at   timestamp(6),
--     id           uuid    not null
--         primary key,
--     organizer_id uuid
--         constraint fkmied7el0kcl27ul1mn6384hki
--             references organizers,
--     venue_id     uuid
--         constraint fkqdxygdernwwt74hdvix9u5nr3
--             references venues,
--     description  text,
--     status       varchar(255)
--         constraint events_status_check
--             check ((status)::text = ANY
--                    ((ARRAY ['DRAFT'::character varying, 'PUBLISHED'::character varying, 'CANCELLED'::character varying, 'COMPLETED'::character varying])::text[])),
--     title        varchar(255)
-- );
--
-- alter table events
--     owner to postgres;
--
-- create table bookings
-- (
--     created_at timestamp(6),
--     updated_at timestamp(6),
--     event_id   uuid
--         constraint fk2ww82bk3npaiyu9oeehwtt2q3
--             references events,
--     id         uuid not null
--         primary key,
--     user_id    uuid
--         constraint fkeyog2oic85xg7hsu2je2lx3s6
--             references users,
--     status     varchar(255)
--         constraint bookings_status_check
--             check ((status)::text = ANY
--                    ((ARRAY ['PENDING_PAYMENT'::character varying, 'CONFIRMED'::character varying, 'CANCELLED'::character varying])::text[]))
-- );
--
-- alter table bookings
--     owner to postgres;
--
-- grant delete, insert, references, select, trigger, truncate, update on bookings to anon;
--
-- grant delete, insert, references, select, trigger, truncate, update on bookings to authenticated;
--
-- grant delete, insert, references, select, trigger, truncate, update on bookings to service_role;
--
-- grant delete, insert, references, select, trigger, truncate, update on events to anon;
--
-- grant delete, insert, references, select, trigger, truncate, update on events to authenticated;
--
-- grant delete, insert, references, select, trigger, truncate, update on events to service_role;
--
-- create table payments
-- (
--     amount      double precision not null,
--     created_at  timestamp(6),
--     updated_at  timestamp(6),
--     booking_id  uuid
--         unique
--         constraint fkc52o2b1jkxttngufqp3t7jr3h
--             references bookings,
--     id          uuid             not null
--         primary key,
--     receipt_url varchar(255),
--     status      varchar(255)
--         constraint payments_status_check
--             check ((status)::text = ANY
--                    ((ARRAY ['PENDING'::character varying, 'COMPLETED'::character varying, 'FAILED'::character varying])::text[]))
-- );
--
-- alter table payments
--     owner to postgres;
--
-- grant delete, insert, references, select, trigger, truncate, update on payments to anon;
--
-- grant delete, insert, references, select, trigger, truncate, update on payments to authenticated;
--
-- grant delete, insert, references, select, trigger, truncate, update on payments to service_role;
--
-- create table seats
-- (
--     reserved    boolean not null,
--     seat_number integer,
--     created_at  timestamp(6),
--     updated_at  timestamp(6),
--     event_id    uuid
--         constraint fkn8dwqflg9k82ygrbsseghd7ca
--             references events,
--     id          uuid    not null
--         primary key,
--     row_label   varchar(255)
-- );
--
-- alter table seats
--     owner to postgres;
--
-- grant delete, insert, references, select, trigger, truncate, update on seats to anon;
--
-- grant delete, insert, references, select, trigger, truncate, update on seats to authenticated;
--
-- grant delete, insert, references, select, trigger, truncate, update on seats to service_role;
--
-- create table tickets
-- (
--     created_at     timestamp(6),
--     updated_at     timestamp(6),
--     booking_id     uuid
--         constraint fkefja4avuu7g29t78mxifrsynb
--             references bookings,
--     event_id       uuid
--         constraint fk3utafe14rupaypjocldjaj4ol
--             references events,
--     holder_user_id uuid
--         constraint fkib1ho6adkvyr202ry3mpyextc
--             references users,
--     id             uuid not null
--         primary key,
--     seat_id        uuid
--         unique
--         constraint fk1f6n3pv4b80wl6gj4ra32ctxk
--             references seats,
--     claim_token    varchar(255),
--     holder_name    varchar(255),
--     status         varchar(255)
--         constraint tickets_status_check
--             check ((status)::text = ANY
--                    ((ARRAY ['AVAILABLE'::character varying, 'CLAIMED'::character varying, 'USED'::character varying])::text[]))
-- );
--
-- alter table tickets
--     owner to postgres;
--
-- grant delete, insert, references, select, trigger, truncate, update on tickets to anon;
--
-- grant delete, insert, references, select, trigger, truncate, update on tickets to authenticated;
--
-- grant delete, insert, references, select, trigger, truncate, update on tickets to service_role;
--
-- grant delete, insert, references, select, trigger, truncate, update on venues to anon;
--
-- grant delete, insert, references, select, trigger, truncate, update on venues to authenticated;
--
-- grant delete, insert, references, select, trigger, truncate, update on venues to service_role;
--
