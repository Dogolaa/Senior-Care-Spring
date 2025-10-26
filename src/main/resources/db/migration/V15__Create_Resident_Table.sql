CREATE TABLE residents
(
    id             UUID PRIMARY KEY,
    name           VARCHAR(255)             NOT NULL,
    cpf            VARCHAR(11)              NOT NULL UNIQUE,
    rg             VARCHAR(9)               NOT NULL UNIQUE,
    date_of_birth  DATE                     NOT NULL,
    gender         VARCHAR(50)              NOT NULL,
    blood_type     VARCHAR(2)               NOT NULL,
    allergy        VARCHAR(2)               NOT NULL,
    is_active      BOOLEAN                  NOT NULL,
    admission_date DATE                     NOT NULL,
    created_at     TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at     TIMESTAMP WITH TIME ZONE NOT NULL,
    deleted_at     TIMESTAMP WITH TIME ZONE
);