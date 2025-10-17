CREATE TABLE managers
(
    employee_id UUID PRIMARY KEY,
    department  VARCHAR(255),
    shift       VARCHAR(255),

    CONSTRAINT fk_managers_employee FOREIGN KEY (employee_id) REFERENCES employees (id)
);
