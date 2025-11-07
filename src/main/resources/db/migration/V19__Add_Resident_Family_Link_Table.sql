CREATE TABLE resident_family_links
(
    id                 UUID PRIMARY KEY,
    resident_id        UUID                     NOT NULL,
    user_id            UUID                     NOT NULL,
    relationship       VARCHAR(100)             NOT NULL,
    is_primary_contact BOOLEAN DEFAULT FALSE    NOT NULL,
    created_at         TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at         TIMESTAMP WITH TIME ZONE NOT NULL,
    deleted_at         TIMESTAMP WITH TIME ZONE,

    CONSTRAINT fk_resident_link
        FOREIGN KEY (resident_id) REFERENCES residents (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_link
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE RESTRICT,
    CONSTRAINT uk_resident_user_link UNIQUE (resident_id, user_id)
);

CREATE INDEX idx_resident_family_links_resident_id ON resident_family_links (resident_id);
CREATE INDEX idx_resident_family_links_user_id ON resident_family_links (user_id);

CREATE TABLE resident_allergies
(
    resident_id         UUID         NOT NULL,
    allergy_description VARCHAR(255) NOT NULL,

    CONSTRAINT fk_resident_allergy
        FOREIGN KEY (resident_id) REFERENCES residents (id) ON DELETE CASCADE,
    PRIMARY KEY (resident_id, allergy_description)
);

CREATE INDEX idx_resident_allergies_resident_id ON resident_allergies (resident_id);

DO
$$
    BEGIN
        IF EXISTS(SELECT 1
                  FROM information_schema.columns
                  WHERE table_name = 'residents'
                    AND column_name = 'primary_responsible_id') THEN

            INSERT INTO resident_family_links (id,
                                               resident_id,
                                               user_id,
                                               relationship,
                                               is_primary_contact,
                                               created_at,
                                               updated_at)
            SELECT gen_random_uuid(),
                   id,
                   primary_responsible_id,
                   'Responsável Principal',
                   true,
                   NOW(),
                   NOW()
            FROM residents
            WHERE primary_responsible_id IS NOT NULL
            ON CONFLICT (resident_id, user_id) DO NOTHING;

            ALTER TABLE residents
                DROP COLUMN primary_responsible_id;

        END IF;
    END
$$;