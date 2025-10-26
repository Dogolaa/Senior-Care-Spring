ALTER TABLE residents
    DROP COLUMN IF EXISTS allergy;

ALTER TABLE residents
    RENAME COLUMN responsibleid TO primary_responsible_id;

ALTER TABLE residents
    ADD CONSTRAINT fk_primary_responsible FOREIGN KEY (primary_responsible_id) REFERENCES users (id) ON DELETE RESTRICT;

ALTER TABLE residents
    ALTER COLUMN rg TYPE VARCHAR(20);
ALTER TABLE residents
    ALTER COLUMN blood_type TYPE VARCHAR(10);

ALTER TABLE residents
    DROP CONSTRAINT IF EXISTS residents_rg_key;
