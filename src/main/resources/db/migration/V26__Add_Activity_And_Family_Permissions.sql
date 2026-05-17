INSERT INTO permissions (id, permission_name, created_at, updated_at)
VALUES (gen_random_uuid(), 'MANAGE_ACTIVITIES', NOW(), NOW()),
       (gen_random_uuid(), 'VIEW_RESIDENT_RECORDS', NOW(), NOW())
ON CONFLICT (permission_name) DO NOTHING;

INSERT INTO roles (id, name, created_at, updated_at)
VALUES (gen_random_uuid(), 'FAMILY_MEMBER', NOW(), NOW())
ON CONFLICT (name) DO NOTHING;

-- MANAGE_ACTIVITIES: staff who run activities with residents
INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'DOCTOR'),
       (SELECT id FROM permissions WHERE permission_name = 'MANAGE_ACTIVITIES')
ON CONFLICT (role_id, permission_id) DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'NURSE'),
       (SELECT id FROM permissions WHERE permission_name = 'MANAGE_ACTIVITIES')
ON CONFLICT (role_id, permission_id) DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'MANAGER'),
       (SELECT id FROM permissions WHERE permission_name = 'MANAGE_ACTIVITIES')
ON CONFLICT (role_id, permission_id) DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'ADMIN'),
       (SELECT id FROM permissions WHERE permission_name = 'MANAGE_ACTIVITIES')
ON CONFLICT (role_id, permission_id) DO NOTHING;

-- VIEW_RESIDENT_RECORDS: read-only access for family members and admins
INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'FAMILY_MEMBER'),
       (SELECT id FROM permissions WHERE permission_name = 'VIEW_RESIDENT_RECORDS')
ON CONFLICT (role_id, permission_id) DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'ADMIN'),
       (SELECT id FROM permissions WHERE permission_name = 'VIEW_RESIDENT_RECORDS')
ON CONFLICT (role_id, permission_id) DO NOTHING;
