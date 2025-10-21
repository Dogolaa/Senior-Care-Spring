INSERT INTO permissions (id, permission_name, created_at, updated_at)
VALUES (gen_random_uuid(), 'READ_ALL_EMPLOYEES', NOW(), NOW()),
       (gen_random_uuid(), 'READ_EMPLOYEE_DETAILS', NOW(), NOW()),
       (gen_random_uuid(), 'READ_ALL_NURSES', NOW(), NOW()),
       (gen_random_uuid(), 'READ_NURSE_DETAILS', NOW(), NOW()),
       (gen_random_uuid(), 'READ_ALL_DOCTORS', NOW(), NOW()),
       (gen_random_uuid(), 'READ_DOCTOR_DETAILS', NOW(), NOW()),
       (gen_random_uuid(), 'READ_ALL_MANAGERS', NOW(), NOW()),
       (gen_random_uuid(), 'READ_MANAGER_DETAILS', NOW(), NOW());

INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'NURSE'),
       p.id
FROM permissions p
WHERE p.permission_name IN (
                            'READ_ALL_NURSES',
                            'READ_NURSE_DETAILS'
    );

INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'DOCTOR'),
       p.id
FROM permissions p
WHERE p.permission_name IN (
                            'READ_ALL_DOCTORS',
                            'READ_DOCTOR_DETAILS'
    );

INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'MANAGER'),
       p.id
FROM permissions p
WHERE p.permission_name IN (
                            'READ_ALL_EMPLOYEES',
                            'READ_EMPLOYEE_DETAILS',
                            'READ_ALL_NURSES',
                            'READ_NURSE_DETAILS',
                            'READ_ALL_DOCTORS',
                            'READ_DOCTOR_DETAILS',
                            'READ_ALL_MANAGERS',
                            'READ_MANAGER_DETAILS'
    );

INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'ADMIN'),
       p.id
FROM permissions p
WHERE p.permission_name IN (
                            'READ_ALL_EMPLOYEES',
                            'READ_EMPLOYEE_DETAILS',
                            'READ_ALL_NURSES',
                            'READ_NURSE_DETAILS',
                            'READ_ALL_DOCTORS',
                            'READ_DOCTOR_DETAILS',
                            'READ_ALL_MANAGERS',
                            'READ_MANAGER_DETAILS'
    );