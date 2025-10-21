DO
$$
    DECLARE
        admin_role_id UUID;
        admin_user_id UUID;
    BEGIN
        SELECT id INTO admin_role_id FROM roles WHERE name = 'ADMIN';

        IF admin_role_id IS NULL THEN
            RAISE EXCEPTION 'Role ADMIN not found. Run previous migrations first.';
        END IF;

        admin_user_id := gen_random_uuid();

        INSERT INTO users (id,
                           name,
                           email,
                           phone,
                           is_active,
                           address_id,
                           password,
                           role_id,
                           created_at,
                           updated_at,
                           deleted_at)
        VALUES (admin_user_id,
                'Administrador do Sistema',
                'admin@seniorcare.com',
                '999999999',
                true,
                null,
                   -- senha: "admin123"
                '$2a$10$f8.vY1mVBCR3r6h.QYLYpuSSN0v8j6k4t41s1I.E.S/W.2o.sPZgq',
                admin_role_id,
                NOW(),
                NOW(),
                null);
    END
$$;