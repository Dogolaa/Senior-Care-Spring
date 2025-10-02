CREATE TABLE employees
(
    id             UUID PRIMARY KEY,
    user_id        UUID                     NOT NULL UNIQUE,
    admission_date DATE                     NOT NULL,
    created_at     TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at     TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT fk_employees_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE nurses
(
    employee_id UUID PRIMARY KEY,
    coren       VARCHAR(255) NOT NULL UNIQUE,
    specialty   VARCHAR(255),
    shift       VARCHAR(255),

    CONSTRAINT fk_nurses_employee FOREIGN KEY (employee_id) REFERENCES employees (id)
);


CREATE TABLE doctors
(
    employee_id UUID PRIMARY KEY,
    crm         VARCHAR(255) NOT NULL UNIQUE,
    specialty   VARCHAR(255),

    CONSTRAINT fk_doctors_employee FOREIGN KEY (employee_id) REFERENCES employees (id)
);