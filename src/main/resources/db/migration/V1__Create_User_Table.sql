CREATE TABLE users
(
    id         UUID PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    phone      VARCHAR(50),
    is_active  BOOLEAN      NOT NULL,
    address_id UUID,
    password   VARCHAR(255) NOT NULL,
    role_id    UUID         NOT NULL
);