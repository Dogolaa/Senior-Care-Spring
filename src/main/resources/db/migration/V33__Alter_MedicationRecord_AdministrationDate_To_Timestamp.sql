ALTER TABLE medication_records
    ALTER COLUMN administration_date TYPE TIMESTAMP
        USING administration_date::TIMESTAMP;
