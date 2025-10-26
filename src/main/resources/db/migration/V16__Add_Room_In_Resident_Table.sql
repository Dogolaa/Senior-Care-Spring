ALTER TABLE residents
    ADD COLUMN room          VARCHAR(20) NOT NULL,
    ADD COLUMN responsibleId UUID        NOT NULL;