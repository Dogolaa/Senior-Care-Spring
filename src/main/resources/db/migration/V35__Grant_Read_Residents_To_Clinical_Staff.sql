INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'DOCTOR'),
       (SELECT id FROM permissions WHERE permission_name = 'MANAGE_RESIDENTS')
ON CONFLICT (role_id, permission_id) DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'NURSE'),
       (SELECT id FROM permissions WHERE permission_name = 'MANAGE_RESIDENTS')
ON CONFLICT (role_id, permission_id) DO NOTHING;
