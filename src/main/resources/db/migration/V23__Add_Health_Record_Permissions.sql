INSERT INTO permissions (id, permission_name, created_at, updated_at)
VALUES (gen_random_uuid(), 'MANAGE_HEALTH_RECORDS', NOW(), NOW())
ON CONFLICT (permission_name) DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'DOCTOR'),
       (SELECT id FROM permissions WHERE permission_name = 'MANAGE_HEALTH_RECORDS')
ON CONFLICT (role_id, permission_id) DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'NURSE'),
       (SELECT id FROM permissions WHERE permission_name = 'MANAGE_HEALTH_RECORDS')
ON CONFLICT (role_id, permission_id) DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'MANAGER'),
       (SELECT id FROM permissions WHERE permission_name = 'MANAGE_HEALTH_RECORDS')
ON CONFLICT (role_id, permission_id) DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'ADMIN'),
       (SELECT id FROM permissions WHERE permission_name = 'MANAGE_HEALTH_RECORDS')
ON CONFLICT (role_id, permission_id) DO NOTHING;
