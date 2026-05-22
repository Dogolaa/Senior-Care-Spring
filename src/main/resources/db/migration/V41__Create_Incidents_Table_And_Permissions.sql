CREATE TABLE incidents (
    id UUID PRIMARY KEY,
    resident_id UUID NOT NULL,
    reported_by_id UUID NOT NULL,
    incident_type VARCHAR(50) NOT NULL,
    severity VARCHAR(20) NOT NULL,
    description TEXT NOT NULL,
    action_taken TEXT,
    occurred_at TIMESTAMP NOT NULL,
    room VARCHAR(50),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

INSERT INTO permissions (id, permission_name, created_at, updated_at)
VALUES (gen_random_uuid(), 'MANAGE_INCIDENTS', NOW(), NOW()),
       (gen_random_uuid(), 'VIEW_INCIDENTS', NOW(), NOW())
ON CONFLICT (permission_name) DO NOTHING;

-- MANAGE_INCIDENTS: ADMIN, MANAGER, DOCTOR, NURSE
INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'ADMIN'),
       (SELECT id FROM permissions WHERE permission_name = 'MANAGE_INCIDENTS')
ON CONFLICT (role_id, permission_id) DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'MANAGER'),
       (SELECT id FROM permissions WHERE permission_name = 'MANAGE_INCIDENTS')
ON CONFLICT (role_id, permission_id) DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'DOCTOR'),
       (SELECT id FROM permissions WHERE permission_name = 'MANAGE_INCIDENTS')
ON CONFLICT (role_id, permission_id) DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'NURSE'),
       (SELECT id FROM permissions WHERE permission_name = 'MANAGE_INCIDENTS')
ON CONFLICT (role_id, permission_id) DO NOTHING;

-- VIEW_INCIDENTS: FAMILY_MEMBER, ADMIN
INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'FAMILY_MEMBER'),
       (SELECT id FROM permissions WHERE permission_name = 'VIEW_INCIDENTS')
ON CONFLICT (role_id, permission_id) DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'ADMIN'),
       (SELECT id FROM permissions WHERE permission_name = 'VIEW_INCIDENTS')
ON CONFLICT (role_id, permission_id) DO NOTHING;
