INSERT INTO permissions (id, permission_name, created_at, updated_at)
VALUES (gen_random_uuid(), 'MANAGE_EMPLOYEES', NOW(), NOW()),
       (gen_random_uuid(), 'PRESCRIBE_MEDICATION', NOW(), NOW()),
       (gen_random_uuid(), 'VIEW_REPORTS', NOW(), NOW());

INSERT INTO roles (id, name, created_at, updated_at)
VALUES (gen_random_uuid(), 'DOCTOR', NOW(), NOW()),
       (gen_random_uuid(), 'MANAGER', NOW(), NOW());

INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'DOCTOR'),
       p.id
FROM permissions p
WHERE p.permission_name IN ('READ_USER', 'PRESCRIBE_MEDICATION');

INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'MANAGER'),
       p.id
FROM permissions p
WHERE p.permission_name IN ('READ_USER', 'UPDATE_USER', 'MANAGE_EMPLOYEES',
                            'VIEW_REPORTS');