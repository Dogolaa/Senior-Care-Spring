CREATE TABLE medication_record_photos
(
    id                    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    medication_record_id  UUID                     NOT NULL,
    photo_url             TEXT                     NOT NULL,
    uploaded_at           TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_med_photo_record FOREIGN KEY (medication_record_id) REFERENCES medication_records (id) ON DELETE CASCADE
);

CREATE INDEX idx_medication_record_photos_record_id ON medication_record_photos (medication_record_id);
