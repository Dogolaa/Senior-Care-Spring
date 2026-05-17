CREATE TABLE health_records (
    id UUID PRIMARY KEY,
    resident_id UUID NOT NULL,
    updated_by_id UUID NOT NULL,
    height REAL,
    weight REAL,
    blood_pressure VARCHAR(255),
    heart_rate INTEGER,
    temperature REAL,
    saturation REAL,
    imc REAL,
    last_updated DATE
);

CREATE TABLE health_record_histories (
    id UUID PRIMARY KEY,
    health_record_id UUID REFERENCES health_records(id),
    height REAL,
    weight REAL,
    blood_pressure VARCHAR(255),
    heart_rate INTEGER,
    temperature REAL,
    saturation REAL,
    imc REAL,
    update_date DATE
);
