CREATE TABLE address
(
    id         UUID PRIMARY KEY,
    cep        VARCHAR(255)             NOT NULL,
    country    VARCHAR(255)             NOT NULL,
    state      VARCHAR(255)             NOT NULL,
    city       VARCHAR(255)             NOT NULL,
    district   VARCHAR(255)             NOT NULL,
    street     VARCHAR(255)             NOT NULL,
    number     INTEGER                  NOT NULL,
    complement VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE
);