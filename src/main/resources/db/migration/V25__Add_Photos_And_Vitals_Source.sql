-- Photos linked to individual activity occurrences
CREATE TABLE activity_record_photos
(
    id                         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    activity_record_history_id UUID                     NOT NULL,
    photo_url                  TEXT                     NOT NULL,
    uploaded_at                TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_activity_photo_history FOREIGN KEY (activity_record_history_id) REFERENCES activity_record_histories (id) ON DELETE CASCADE
);

-- Photos linked to individual health record measurements
CREATE TABLE health_record_photos
(
    id                       UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    health_record_history_id UUID                     NOT NULL,
    photo_url                TEXT                     NOT NULL,
    uploaded_at              TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_health_photo_history FOREIGN KEY (health_record_history_id) REFERENCES health_record_histories (id) ON DELETE CASCADE
);

-- Source of vital signs measurement (manual entry vs. automated device)
ALTER TABLE health_record_histories
    ADD COLUMN source VARCHAR(50) NOT NULL DEFAULT 'MANUAL';

CREATE INDEX idx_activity_record_photos_history_id ON activity_record_photos (activity_record_history_id);
CREATE INDEX idx_health_record_photos_history_id ON health_record_photos (health_record_history_id);
