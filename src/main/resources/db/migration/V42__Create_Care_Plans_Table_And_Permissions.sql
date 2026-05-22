CREATE TABLE care_plans (
    id UUID PRIMARY KEY,
    resident_id UUID NOT NULL,
    responsible_id UUID NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE care_plan_goals (
    care_plan_id UUID NOT NULL REFERENCES care_plans(id) ON DELETE CASCADE,
    goal TEXT NOT NULL,
    goal_order INTEGER NOT NULL
);

CREATE TABLE care_plan_interventions (
    care_plan_id UUID NOT NULL REFERENCES care_plans(id) ON DELETE CASCADE,
    intervention TEXT NOT NULL,
    intervention_order INTEGER NOT NULL
);

INSERT INTO permissions (id, permission_name, created_at, updated_at)
VALUES (gen_random_uuid(), 'MANAGE_CARE_PLANS', NOW(), NOW()),
       (gen_random_uuid(), 'VIEW_CARE_PLANS', NOW(), NOW())
ON CONFLICT (permission_name) DO NOTHING;

-- MANAGE_CARE_PLANS: ADMIN, MANAGER, DOCTOR, NURSE
INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'ADMIN'),
       (SELECT id FROM permissions WHERE permission_name = 'MANAGE_CARE_PLANS')
ON CONFLICT (role_id, permission_id) DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'MANAGER'),
       (SELECT id FROM permissions WHERE permission_name = 'MANAGE_CARE_PLANS')
ON CONFLICT (role_id, permission_id) DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'DOCTOR'),
       (SELECT id FROM permissions WHERE permission_name = 'MANAGE_CARE_PLANS')
ON CONFLICT (role_id, permission_id) DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'NURSE'),
       (SELECT id FROM permissions WHERE permission_name = 'MANAGE_CARE_PLANS')
ON CONFLICT (role_id, permission_id) DO NOTHING;

-- VIEW_CARE_PLANS: FAMILY_MEMBER, ADMIN
INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'FAMILY_MEMBER'),
       (SELECT id FROM permissions WHERE permission_name = 'VIEW_CARE_PLANS')
ON CONFLICT (role_id, permission_id) DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT (SELECT id FROM roles WHERE name = 'ADMIN'),
       (SELECT id FROM permissions WHERE permission_name = 'VIEW_CARE_PLANS')
ON CONFLICT (role_id, permission_id) DO NOTHING;
