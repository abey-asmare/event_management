create table if not exists users
(
    id         uuid not null
        primary key,
    created_at timestamp(6),
    updated_at timestamp(6),
    email      varchar(255)
        constraint uk6dotkott2kjsp8vw4d0m25fb7
            unique,
    name       varchar(255),
    role       varchar(255)
        constraint users_role_check
            check ((role)::text = ANY
                   ((ARRAY ['USER'::character varying, 'ORGANIZER'::character varying, 'ADMIN'::character varying])::text[]))
);

alter table users
    owner to postgres;

create table if not exists organizers
(
    user_id           uuid    not null
        primary key
        constraint fk7kpidig5uothy6p9ttywnif6l
            references users,
    created_at        timestamp(6),
    updated_at        timestamp(6),
    is_verified       boolean not null,
    organization_name varchar(255)
);

alter table organizers
    owner to postgres;

create table if not exists venues
(
    id          uuid    not null
        primary key,
    created_at  timestamp(6),
    updated_at  timestamp(6),
    city        varchar(255),
    country     varchar(255),
    postal_code varchar(255),
    street      varchar(255),
    capacity    integer not null,
    name        varchar(255)
);

alter table venues
    owner to postgres;

create table if not exists events
(
    id           uuid    not null
        primary key,
    created_at   timestamp(6),
    updated_at   timestamp(6),
    capacity     integer,
    description  text,
    end_time     timestamp(6),
    seat_based   boolean not null,
    start_time   timestamp(6),
    status       varchar(255)
        constraint events_status_check
            check ((status)::text = ANY
                   ((ARRAY ['DRAFT'::character varying, 'PUBLISHED'::character varying, 'CANCELLED'::character varying, 'COMPLETED'::character varying])::text[])),
    ticket_price double precision,
    title        varchar(255),
    organizer_id uuid
        constraint fkmied7el0kcl27ul1mn6384hki
            references organizers,
    venue_id     uuid
        constraint fkqdxygdernwwt74hdvix9u5nr3
            references venues
);

alter table events
    owner to postgres;

create table if not exists bookings
(
    id         uuid    not null
        primary key,
    created_at timestamp(6),
    updated_at timestamp(6),
    quantity   integer not null,
    status     varchar(255)
        constraint bookings_status_check
            check ((status)::text = ANY
                   ((ARRAY ['PENDING_PAYMENT'::character varying, 'CONFIRMED'::character varying, 'CANCELLED'::character varying])::text[])),
    event_id   uuid
        constraint fk2ww82bk3npaiyu9oeehwtt2q3
            references events,
    user_id    uuid
        constraint fkeyog2oic85xg7hsu2je2lx3s6
            references users
);

alter table bookings
    owner to postgres;

create table if not exists payments
(
    id              uuid not null
        primary key,
    created_at      timestamp(6),
    updated_at      timestamp(6),
    amount          numeric(38, 2),
    payment_method  varchar(255)
        constraint payments_payment_method_check
            check ((payment_method)::text = ANY
                   ((ARRAY ['CHAPA'::character varying, 'TELEBIRR'::character varying])::text[])),
    receipt_url     varchar(255),
    status          varchar(255)
        constraint payments_status_check
            check ((status)::text = ANY
                   ((ARRAY ['INITIATED'::character varying, 'PENDING'::character varying, 'COMPLETED'::character varying, 'FAILED'::character varying, 'CANCELLED'::character varying])::text[])),
    transaction_ref varchar(255),
    booking_id      uuid not null
        constraint uknuscjm6x127hkb15kcb8n56wo
            unique
        constraint fkc52o2b1jkxttngufqp3t7jr3h
            references bookings
);

alter table payments
    owner to postgres;

create table if not exists seats
(
    id         uuid    not null
        primary key,
    created_at timestamp(6),
    updated_at timestamp(6),
    reserved   boolean not null,
    row_label  varchar(255),
    seat_code  integer,
    event_id   uuid
        constraint fkn8dwqflg9k82ygrbsseghd7ca
            references events
);

alter table seats
    owner to postgres;

create table if not exists tickets
(
    id             uuid not null
        primary key,
    created_at     timestamp(6),
    updated_at     timestamp(6),
    claim_token    varchar(255),
    holder_name    varchar(255),
    status         varchar(255)
        constraint tickets_status_check
            check ((status)::text = ANY
                   ((ARRAY ['AVAILABLE'::character varying, 'CLAIMED'::character varying, 'USED'::character varying])::text[])),
    booking_id     uuid
        constraint fkefja4avuu7g29t78mxifrsynb
            references bookings,
    event_id       uuid
        constraint fk3utafe14rupaypjocldjaj4ol
            references events,
    holder_user_id uuid
        constraint fkib1ho6adkvyr202ry3mpyextc
            references users,
    seat_id        uuid
        constraint ukdvmspsturfwlx8cf8f3ewn87c
            unique
        constraint fk1f6n3pv4b80wl6gj4ra32ctxk
            references seats
);

alter table tickets
    owner to postgres;

create table if not exists booking_reserved_seats
(
    booking_id uuid not null
        constraint fkvydcnd1pupjmppn7qytrkdme
            references bookings,
    seat_id    uuid
);

alter table booking_reserved_seats
    owner to postgres;

