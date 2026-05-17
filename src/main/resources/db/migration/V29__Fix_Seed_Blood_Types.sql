ALTER TABLE residents ALTER COLUMN blood_type TYPE VARCHAR(20);

UPDATE residents SET blood_type = 'O_POSITIVE'  WHERE blood_type = 'O+';
UPDATE residents SET blood_type = 'O_NEGATIVE'  WHERE blood_type = 'O-';
UPDATE residents SET blood_type = 'A_POSITIVE'  WHERE blood_type = 'A+';
UPDATE residents SET blood_type = 'A_NEGATIVE'  WHERE blood_type = 'A-';
UPDATE residents SET blood_type = 'B_POSITIVE'  WHERE blood_type = 'B+';
UPDATE residents SET blood_type = 'B_NEGATIVE'  WHERE blood_type = 'B-';
UPDATE residents SET blood_type = 'AB_POSITIVE' WHERE blood_type = 'AB+';
UPDATE residents SET blood_type = 'AB_NEGATIVE' WHERE blood_type = 'AB-';
