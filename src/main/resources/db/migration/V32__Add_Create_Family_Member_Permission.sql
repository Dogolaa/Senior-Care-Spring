INSERT INTO permissions (id, permission_name, created_at, updated_at)
VALUES (gen_random_uuid(), 'CREATE_FAMILY_MEMBER', NOW(), NOW())
ON CONFLICT (permission_name) DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'MANAGER'),
       (SELECT id FROM permissions WHERE permission_name = 'CREATE_FAMILY_MEMBER')
ON CONFLICT (role_id, permission_id) DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'ADMIN'),
       (SELECT id FROM permissions WHERE permission_name = 'CREATE_FAMILY_MEMBER')
ON CONFLICT (role_id, permission_id) DO NOTHING;
