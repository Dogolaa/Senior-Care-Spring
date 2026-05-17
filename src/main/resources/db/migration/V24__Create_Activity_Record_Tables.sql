CREATE TABLE activity_records
(
    id                 UUID PRIMARY KEY,
    resident_id        UUID NOT NULL,
    conducted_by_id    UUID NOT NULL,
    last_activity_date DATE,
    CONSTRAINT fk_activity_record_resident FOREIGN KEY (resident_id) REFERENCES residents (id),
    CONSTRAINT fk_activity_record_staff    FOREIGN KEY (conducted_by_id) REFERENCES users (id),
    CONSTRAINT uk_activity_record_resident UNIQUE (resident_id)
);

CREATE TABLE activity_record_histories
(
    id                 UUID PRIMARY KEY,
    activity_record_id UUID         NOT NULL,
    activity_name      VARCHAR(255) NOT NULL,
    description        TEXT,
    start_date_time    TIMESTAMP    NOT NULL,
    end_date_time      TIMESTAMP,
    conducted_by_id    UUID         NOT NULL,
    notes              TEXT,
    recorded_at        DATE         NOT NULL,
    CONSTRAINT fk_activity_history_record FOREIGN KEY (activity_record_id) REFERENCES activity_records (id) ON DELETE CASCADE,
    CONSTRAINT fk_activity_history_staff  FOREIGN KEY (conducted_by_id) REFERENCES users (id)
);

CREATE INDEX idx_activity_records_resident_id ON activity_records (resident_id);
CREATE INDEX idx_activity_record_histories_record_id ON activity_record_histories (activity_record_id);
