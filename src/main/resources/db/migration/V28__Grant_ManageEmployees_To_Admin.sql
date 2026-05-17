INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'ADMIN'),
       (SELECT id FROM permissions WHERE permission_name = 'MANAGE_EMPLOYEES')
ON CONFLICT (role_id, permission_id) DO NOTHING;
