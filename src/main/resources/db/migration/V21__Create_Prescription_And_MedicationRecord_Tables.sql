CREATE TABLE prescriptions (
    id UUID PRIMARY KEY,
    medical_record_id UUID NOT NULL,
    medication_id VARCHAR(255) NOT NULL,
    dosage VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE
);

CREATE TABLE medication_records (
    id UUID PRIMARY KEY,
    resident_id UUID NOT NULL,
    medication_id VARCHAR(255) NOT NULL,
    administration_date DATE NOT NULL,
    administered_by_id UUID NOT NULL,
    dose VARCHAR(255) NOT NULL
);
