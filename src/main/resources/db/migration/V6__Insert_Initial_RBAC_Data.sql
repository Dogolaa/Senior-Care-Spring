INSERT INTO permissions (id, permission_name, created_at, updated_at)
VALUES (gen_random_uuid(), 'CREATE_USER', NOW(), NOW()),
       (gen_random_uuid(), 'READ_USER', NOW(), NOW()),
       (gen_random_uuid(), 'UPDATE_USER', NOW(), NOW()),
       (gen_random_uuid(), 'DELETE_USER', NOW(), NOW());

INSERT INTO roles (id, name, created_at, updated_at)
VALUES (gen_random_uuid(), 'ADMIN', NOW(), NOW()),
       (gen_random_uuid(), 'DEFAULT_USER', NOW(), NOW());


INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'ADMIN'),
       p.id
FROM permissions p;


INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'DEFAULT_USER'),
       (SELECT id FROM permissions WHERE permission_name = 'READ_USER');