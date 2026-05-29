CREATE TABLE health_record_conditions (
    health_record_id UUID NOT NULL REFERENCES health_records(id) ON DELETE CASCADE,
    condition_description VARCHAR(255) NOT NULL
);
