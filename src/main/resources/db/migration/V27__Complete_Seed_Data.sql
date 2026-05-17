-- =============================================================================
-- V27: Carga completa de dados para demonstração e testes de todos os módulos.
-- Cobre: usuários (staff + familiares), funcionários, residentes, alergias,
--        vínculos familiares, prontuários, prescrições, registros de
--        medicamentos e registros de atividades.
--
-- Senha de todos os usuários seed: admin123
-- =============================================================================

DO
$$
    DECLARE
        -- ── Roles ──────────────────────────────────────────────────────────────
        role_admin    UUID;
        role_doctor   UUID;
        role_nurse    UUID;
        role_manager  UUID;
        role_family   UUID;

        -- ── Usuários – Staff ───────────────────────────────────────────────────
        user_dr_ana      UUID := '00000001-0000-0000-0000-000000000001';
        user_dr_carlos   UUID := '00000001-0000-0000-0000-000000000002';
        user_enf_maria   UUID := '00000001-0000-0000-0000-000000000003';
        user_enf_joao    UUID := '00000001-0000-0000-0000-000000000004';
        user_ger_roberto UUID := '00000001-0000-0000-0000-000000000005';

        -- ── Usuários – Familiares ──────────────────────────────────────────────
        user_fernanda UUID := '00000001-0000-0000-0000-000000000006';
        user_paulo    UUID := '00000001-0000-0000-0000-000000000007';
        user_carolina UUID := '00000001-0000-0000-0000-000000000008';
        user_marcos   UUID := '00000001-0000-0000-0000-000000000009';
        user_ana_fam  UUID := '00000001-0000-0000-0000-000000000010';

        -- ── Funcionários ──────────────────────────────────────────────────────
        emp_dr_ana      UUID := '00000002-0000-0000-0000-000000000001';
        emp_dr_carlos   UUID := '00000002-0000-0000-0000-000000000002';
        emp_enf_maria   UUID := '00000002-0000-0000-0000-000000000003';
        emp_enf_joao    UUID := '00000002-0000-0000-0000-000000000004';
        emp_ger_roberto UUID := '00000002-0000-0000-0000-000000000005';

        -- ── Residentes ────────────────────────────────────────────────────────
        res_maria   UUID := '00000003-0000-0000-0000-000000000001';
        res_jose    UUID := '00000003-0000-0000-0000-000000000002';
        res_rosa    UUID := '00000003-0000-0000-0000-000000000003';
        res_antonio UUID := '00000003-0000-0000-0000-000000000004';
        res_luiza   UUID := '00000003-0000-0000-0000-000000000005';

        -- ── Prontuários ───────────────────────────────────────────────────────
        hrec_maria   UUID := '00000004-0000-0000-0000-000000000001';
        hrec_jose    UUID := '00000004-0000-0000-0000-000000000002';
        hrec_rosa    UUID := '00000004-0000-0000-0000-000000000003';
        hrec_antonio UUID := '00000004-0000-0000-0000-000000000004';
        hrec_luiza   UUID := '00000004-0000-0000-0000-000000000005';

        -- ── Histórico de prontuário ────────────────────────────────────────────
        hh_maria_1   UUID := '00000005-0000-0000-0000-000000000001';
        hh_maria_2   UUID := '00000005-0000-0000-0000-000000000002';
        hh_jose_1    UUID := '00000005-0000-0000-0000-000000000003';
        hh_rosa_1    UUID := '00000005-0000-0000-0000-000000000004';
        hh_antonio_1 UUID := '00000005-0000-0000-0000-000000000005';
        hh_luiza_1   UUID := '00000005-0000-0000-0000-000000000006';

        -- ── Registros de atividade ─────────────────────────────────────────────
        act_maria   UUID := '00000006-0000-0000-0000-000000000001';
        act_jose    UUID := '00000006-0000-0000-0000-000000000002';
        act_rosa    UUID := '00000006-0000-0000-0000-000000000003';
        act_antonio UUID := '00000006-0000-0000-0000-000000000004';
        act_luiza   UUID := '00000006-0000-0000-0000-000000000005';

        -- ── Histórico de atividades ────────────────────────────────────────────
        ach_maria_1   UUID := '00000007-0000-0000-0000-000000000001';
        ach_maria_2   UUID := '00000007-0000-0000-0000-000000000002';
        ach_jose_1    UUID := '00000007-0000-0000-0000-000000000003';
        ach_rosa_1    UUID := '00000007-0000-0000-0000-000000000004';
        ach_antonio_1 UUID := '00000007-0000-0000-0000-000000000005';
        ach_luiza_1   UUID := '00000007-0000-0000-0000-000000000006';

        -- ── Prescrições ───────────────────────────────────────────────────────
        presc_maria_1   UUID := '00000008-0000-0000-0000-000000000001';
        presc_maria_2   UUID := '00000008-0000-0000-0000-000000000002';
        presc_jose_1    UUID := '00000008-0000-0000-0000-000000000003';
        presc_rosa_1    UUID := '00000008-0000-0000-0000-000000000004';
        presc_antonio_1 UUID := '00000008-0000-0000-0000-000000000005';
        presc_antonio_2 UUID := '00000008-0000-0000-0000-000000000006';
        presc_luiza_1   UUID := '00000008-0000-0000-0000-000000000007';

        -- ── Registros de medicamentos ──────────────────────────────────────────
        mrec_1 UUID := '00000009-0000-0000-0000-000000000001';
        mrec_2 UUID := '00000009-0000-0000-0000-000000000002';
        mrec_3 UUID := '00000009-0000-0000-0000-000000000003';
        mrec_4 UUID := '00000009-0000-0000-0000-000000000004';
        mrec_5 UUID := '00000009-0000-0000-0000-000000000005';
        mrec_6 UUID := '00000009-0000-0000-0000-000000000006';

        -- ── Vínculos familiares ────────────────────────────────────────────────
        fl_maria_fern    UUID := '00000010-0000-0000-0000-000000000001';
        fl_maria_paulo   UUID := '00000010-0000-0000-0000-000000000002';
        fl_jose_paulo    UUID := '00000010-0000-0000-0000-000000000003';
        fl_rosa_carol    UUID := '00000010-0000-0000-0000-000000000004';
        fl_antonio_marcos UUID := '00000010-0000-0000-0000-000000000005';
        fl_luiza_ana     UUID := '00000010-0000-0000-0000-000000000006';

        hashed_pw VARCHAR := '$2a$10$f8.vY1mVBCR3r6h.QYLYpuSSN0v8j6k4t41s1I.E.S/W.2o.sPZgq';

    BEGIN
        -- ── Resolver IDs dos roles ─────────────────────────────────────────────
        SELECT id INTO role_admin   FROM roles WHERE name = 'ADMIN';
        SELECT id INTO role_doctor  FROM roles WHERE name = 'DOCTOR';
        SELECT id INTO role_nurse   FROM roles WHERE name = 'NURSE';
        SELECT id INTO role_manager FROM roles WHERE name = 'MANAGER';
        SELECT id INTO role_family  FROM roles WHERE name = 'FAMILY_MEMBER';

        -- ══════════════════════════════════════════════════════════════════════
        -- 1. USUÁRIOS – EQUIPE CLÍNICA E GESTÃO
        -- ══════════════════════════════════════════════════════════════════════
        INSERT INTO users (id, name, email, phone, is_active, address_id, password, role_id, created_at, updated_at, deleted_at)
        VALUES
            (user_dr_ana,      'Dra. Ana Carolina Oliveira',    'ana.oliveira@seniorcare.com',    '11987654321', true, null, hashed_pw, role_doctor,  NOW(), NOW(), null),
            (user_dr_carlos,   'Dr. Carlos Eduardo Medeiros',   'carlos.medeiros@seniorcare.com', '11976543210', true, null, hashed_pw, role_doctor,  NOW(), NOW(), null),
            (user_enf_maria,   'Enf. Maria das Graças Silva',   'maria.silva@seniorcare.com',     '11965432109', true, null, hashed_pw, role_nurse,   NOW(), NOW(), null),
            (user_enf_joao,    'Enf. João Pedro Santos',        'joao.santos@seniorcare.com',     '11954321098', true, null, hashed_pw, role_nurse,   NOW(), NOW(), null),
            (user_ger_roberto, 'Roberto Carlos Alves',          'roberto.alves@seniorcare.com',   '11943210987', true, null, hashed_pw, role_manager, NOW(), NOW(), null)
        ON CONFLICT (id) DO NOTHING;

        -- ══════════════════════════════════════════════════════════════════════
        -- 2. USUÁRIOS – FAMILIARES
        -- ══════════════════════════════════════════════════════════════════════
        INSERT INTO users (id, name, email, phone, is_active, address_id, password, role_id, created_at, updated_at, deleted_at)
        VALUES
            (user_fernanda, 'Fernanda Lima Costa',      'fernanda.lima@gmail.com',         '11932109876', true, null, hashed_pw, role_family, NOW(), NOW(), null),
            (user_paulo,    'Paulo Henrique Mendes',    'paulo.mendes@gmail.com',           '11921098765', true, null, hashed_pw, role_family, NOW(), NOW(), null),
            (user_carolina, 'Carolina Gomes Ferreira',  'carolina.gomes@gmail.com',         '11910987654', true, null, hashed_pw, role_family, NOW(), NOW(), null),
            (user_marcos,   'Marcos Antônio Neves',     'marcos.neves@gmail.com',           '11909876543', true, null, hashed_pw, role_family, NOW(), NOW(), null),
            (user_ana_fam,  'Ana Beatriz Carvalho',     'anabeatriz.carvalho@gmail.com',    '11898765432', true, null, hashed_pw, role_family, NOW(), NOW(), null)
        ON CONFLICT (id) DO NOTHING;

        -- ══════════════════════════════════════════════════════════════════════
        -- 3. FUNCIONÁRIOS (tabela employees)
        -- ══════════════════════════════════════════════════════════════════════
        INSERT INTO employees (id, user_id, admission_date, created_at, updated_at)
        VALUES
            (emp_dr_ana,      user_dr_ana,      '2022-03-01', NOW(), NOW()),
            (emp_dr_carlos,   user_dr_carlos,   '2021-06-15', NOW(), NOW()),
            (emp_enf_maria,   user_enf_maria,   '2020-01-10', NOW(), NOW()),
            (emp_enf_joao,    user_enf_joao,    '2023-08-01', NOW(), NOW()),
            (emp_ger_roberto, user_ger_roberto, '2019-11-05', NOW(), NOW())
        ON CONFLICT (id) DO NOTHING;

        -- ══════════════════════════════════════════════════════════════════════
        -- 4. MÉDICOS
        -- ══════════════════════════════════════════════════════════════════════
        INSERT INTO doctors (employee_id, crm, specialization)
        VALUES
            (emp_dr_ana,    'CRM-SP-123456', 'Geriatria'),
            (emp_dr_carlos, 'CRM-SP-789012', 'Clínica Médica')
        ON CONFLICT (employee_id) DO NOTHING;

        -- ══════════════════════════════════════════════════════════════════════
        -- 5. ENFERMEIROS
        -- ══════════════════════════════════════════════════════════════════════
        INSERT INTO nurses (employee_id, coren, specialization, shift)
        VALUES
            (emp_enf_maria, 'COREN-SP-112233', 'Gerontologia', 'MANHA'),
            (emp_enf_joao,  'COREN-SP-445566', 'Saúde do Idoso', 'TARDE')
        ON CONFLICT (employee_id) DO NOTHING;

        -- ══════════════════════════════════════════════════════════════════════
        -- 6. GERENTES
        -- ══════════════════════════════════════════════════════════════════════
        INSERT INTO managers (employee_id, department, shift)
        VALUES
            (emp_ger_roberto, 'Operações', 'INTEGRAL')
        ON CONFLICT (employee_id) DO NOTHING;

        -- ══════════════════════════════════════════════════════════════════════
        -- 7. RESIDENTES
        -- CPFs gerados e validados conforme algoritmo da Receita Federal.
        -- ══════════════════════════════════════════════════════════════════════
        INSERT INTO residents (id, name, cpf, rg, date_of_birth, gender, blood_type, is_active, admission_date, room, created_at, updated_at, deleted_at)
        VALUES
            -- Maria: 78 anos, hipertensão e alergia a medicamentos
            (res_maria,   'Maria Aparecida Costa',    '52998224725', 'MG-12345678', '1946-03-15', 'FEMININO',  'O_POSITIVE',  true, '2024-01-10', 'Quarto 101', NOW(), NOW(), null),
            -- José: 82 anos, diabetes e doença cardiovascular
            (res_jose,    'José Fernando Rodrigues',  '11144477735', 'SP-98765432', '1942-07-22', 'MASCULINO', 'A_POSITIVE',  true, '2024-02-05', 'Quarto 102', NOW(), NOW(), null),
            -- Rosa: 75 anos, colesterol alto e alergia a látex
            (res_rosa,    'Rosa Benedita Ferreira',   '71428793860', 'RJ-54321098', '1949-11-08', 'FEMININO',  'B_NEGATIVE',  true, '2024-03-12', 'Quarto 103', NOW(), NOW(), null),
            -- Antônio: 88 anos, hipertensão grave e diabetes insulino-dependente
            (res_antonio, 'Antônio Carlos Neves',     '12345678909', 'BA-21098765', '1936-05-30', 'MASCULINO', 'AB_POSITIVE', true, '2023-11-20', 'Quarto 201', NOW(), NOW(), null),
            -- Luíza: 91 anos, insuficiência cardíaca leve
            (res_luiza,   'Luíza Pedrosa Carvalho',  '45678912364', 'PE-10987654', '1933-09-14', 'FEMININO',  'O_NEGATIVE',  true, '2023-09-01', 'Quarto 202', NOW(), NOW(), null)
        ON CONFLICT (id) DO NOTHING;

        -- ══════════════════════════════════════════════════════════════════════
        -- 8. ALERGIAS DOS RESIDENTES
        -- ══════════════════════════════════════════════════════════════════════
        INSERT INTO resident_allergies (resident_id, allergy_description)
        VALUES
            (res_maria,   'PENICILINA'),
            (res_maria,   'DIPIRONA'),
            (res_jose,    'AAS'),
            (res_jose,    'IBUPROFENO'),
            (res_rosa,    'LATEX'),
            (res_antonio, 'SULFA'),
            (res_antonio, 'CONTRASTE IODADO'),
            (res_luiza,   'FRUTOS DO MAR')
        ON CONFLICT DO NOTHING;

        -- ══════════════════════════════════════════════════════════════════════
        -- 9. VÍNCULOS FAMILIARES
        -- ══════════════════════════════════════════════════════════════════════
        INSERT INTO resident_family_links (id, resident_id, user_id, relationship, is_primary_contact, created_at, updated_at)
        VALUES
            -- Maria tem dois vínculos: filha (primário) e sobrinho
            (fl_maria_fern,    res_maria,   user_fernanda, 'Filha',    true,  NOW(), NOW()),
            (fl_maria_paulo,   res_maria,   user_paulo,    'Sobrinho', false, NOW(), NOW()),
            -- José: filho único como contato primário
            (fl_jose_paulo,    res_jose,    user_paulo,    'Filho',    true,  NOW(), NOW()),
            -- Rosa: neta como contato primário
            (fl_rosa_carol,    res_rosa,    user_carolina, 'Neta',     true,  NOW(), NOW()),
            -- Antônio: filho
            (fl_antonio_marcos, res_antonio, user_marcos,  'Filho',    true,  NOW(), NOW()),
            -- Luíza: filha
            (fl_luiza_ana,     res_luiza,   user_ana_fam,  'Filha',    true,  NOW(), NOW())
        ON CONFLICT (id) DO NOTHING;

        -- ══════════════════════════════════════════════════════════════════════
        -- 10. PRONTUÁRIOS DE SAÚDE (snapshot atual)
        -- ══════════════════════════════════════════════════════════════════════
        INSERT INTO health_records (id, resident_id, updated_by_id, height, weight, blood_pressure, heart_rate, temperature, saturation, imc, last_updated)
        VALUES
            (hrec_maria,   res_maria,   user_dr_ana,    1.58, 62.5, '130/85', 72, 36.5, 97.0, 25.1, '2025-05-10'),
            (hrec_jose,    res_jose,    user_dr_carlos, 1.72, 78.0, '145/92', 68, 36.8, 96.0, 26.4, '2025-05-12'),
            (hrec_rosa,    res_rosa,    user_dr_ana,    1.55, 55.0, '120/80', 75, 36.3, 98.0, 22.9, '2025-05-14'),
            (hrec_antonio, res_antonio, user_dr_carlos, 1.65, 70.0, '160/95', 80, 37.0, 95.0, 25.7, '2025-05-08'),
            (hrec_luiza,   res_luiza,   user_dr_ana,    1.52, 48.5, '125/82', 70, 36.6, 96.5, 21.0, '2025-05-15')
        ON CONFLICT (id) DO NOTHING;

        -- ══════════════════════════════════════════════════════════════════════
        -- 11. HISTÓRICO DE PRONTUÁRIOS (série temporal de medições)
        -- ══════════════════════════════════════════════════════════════════════
        INSERT INTO health_record_histories (id, health_record_id, height, weight, blood_pressure, heart_rate, temperature, saturation, imc, update_date, source)
        VALUES
            -- Maria: medição anterior e atual
            (hh_maria_1,   hrec_maria,   1.58, 63.0, '135/88', 74, 36.7, 96.5, 25.2, '2025-04-10', 'MANUAL'),
            (hh_maria_2,   hrec_maria,   1.58, 62.5, '130/85', 72, 36.5, 97.0, 25.1, '2025-05-10', 'MANUAL'),
            -- José: uma medição anterior
            (hh_jose_1,    hrec_jose,    1.72, 78.5, '148/94', 70, 36.9, 95.5, 26.5, '2025-04-12', 'MANUAL'),
            -- Rosa
            (hh_rosa_1,    hrec_rosa,    1.55, 55.5, '122/81', 76, 36.4, 97.5, 23.1, '2025-04-14', 'MANUAL'),
            -- Antônio: pressão arterial elevada no histórico
            (hh_antonio_1, hrec_antonio, 1.65, 71.0, '162/97', 82, 37.1, 94.5, 26.1, '2025-04-08', 'MANUAL'),
            -- Luíza
            (hh_luiza_1,   hrec_luiza,   1.52, 49.0, '127/83', 71, 36.7, 96.0, 21.2, '2025-04-15', 'MANUAL')
        ON CONFLICT (id) DO NOTHING;

        -- ══════════════════════════════════════════════════════════════════════
        -- 12. PRESCRIÇÕES MÉDICAS
        -- ══════════════════════════════════════════════════════════════════════
        INSERT INTO prescriptions (id, medical_record_id, medication_id, dosage, start_date, end_date)
        VALUES
            -- Maria: Losartana (hipertensão) + AAS 100mg (antiagregante)
            (presc_maria_1,   hrec_maria,   'a1000001-0000-0000-0000-000000000001', '50mg – 1x ao dia – via oral – manhã',                           '2024-01-10', null),
            (presc_maria_2,   hrec_maria,   'a2000002-0000-0000-0000-000000000001', '100mg – 1x ao dia – via oral – após o almoço',                  '2024-01-10', null),
            -- José: Enalapril 10mg (hipertensão)
            (presc_jose_1,    hrec_jose,    'a1000001-0000-0000-0000-000000000004', '10mg – 2x ao dia – via oral – manhã e noite',                   '2024-02-05', null),
            -- Rosa: Atorvastatina 20mg (colesterol)
            (presc_rosa_1,    hrec_rosa,    'a3000003-0000-0000-0000-000000000001', '20mg – 1x ao dia – via oral – à noite',                         '2024-03-12', null),
            -- Antônio: Amlodipina (hipertensão) + Insulina NPH (diabetes)
            (presc_antonio_1, hrec_antonio, 'a1000001-0000-0000-0000-000000000005', '5mg – 1x ao dia – via oral – manhã',                            '2023-11-20', null),
            (presc_antonio_2, hrec_antonio, 'a4000004-0000-0000-0000-000000000004', '10UI – subcutâneo – 2x ao dia (manhã e antes do jantar)',        '2023-11-20', null),
            -- Luíza: Metoprolol (insuficiência cardíaca)
            (presc_luiza_1,   hrec_luiza,   'a1000001-0000-0000-0000-000000000008', '50mg – 1x ao dia – via oral – manhã',                           '2023-09-01', null)
        ON CONFLICT (id) DO NOTHING;

        -- ══════════════════════════════════════════════════════════════════════
        -- 13. REGISTROS DE ADMINISTRAÇÃO DE MEDICAMENTOS
        -- ══════════════════════════════════════════════════════════════════════
        INSERT INTO medication_records (id, resident_id, medication_id, administration_date, administered_by_id, dose)
        VALUES
            (mrec_1, res_maria,   'a1000001-0000-0000-0000-000000000001', '2025-05-16', user_enf_maria, '50mg'),
            (mrec_2, res_maria,   'a2000002-0000-0000-0000-000000000001', '2025-05-16', user_enf_maria, '100mg'),
            (mrec_3, res_jose,    'a1000001-0000-0000-0000-000000000004', '2025-05-16', user_enf_joao,  '10mg'),
            (mrec_4, res_antonio, 'a1000001-0000-0000-0000-000000000005', '2025-05-16', user_enf_maria, '5mg'),
            (mrec_5, res_antonio, 'a4000004-0000-0000-0000-000000000004', '2025-05-16', user_enf_joao,  '10UI'),
            (mrec_6, res_luiza,   'a1000001-0000-0000-0000-000000000008', '2025-05-16', user_enf_maria, '50mg')
        ON CONFLICT (id) DO NOTHING;

        -- ══════════════════════════════════════════════════════════════════════
        -- 14. REGISTROS DE ATIVIDADES (um por residente)
        -- ══════════════════════════════════════════════════════════════════════
        INSERT INTO activity_records (id, resident_id, conducted_by_id, last_activity_date)
        VALUES
            (act_maria,   res_maria,   user_enf_maria, '2025-05-16'),
            (act_jose,    res_jose,    user_enf_joao,  '2025-05-15'),
            (act_rosa,    res_rosa,    user_enf_maria, '2025-05-16'),
            (act_antonio, res_antonio, user_enf_joao,  '2025-05-14'),
            (act_luiza,   res_luiza,   user_enf_maria, '2025-05-16')
        ON CONFLICT (id) DO NOTHING;

        -- ══════════════════════════════════════════════════════════════════════
        -- 15. HISTÓRICO DE ATIVIDADES
        -- ══════════════════════════════════════════════════════════════════════
        INSERT INTO activity_record_histories (id, activity_record_id, activity_name, description, start_date_time, end_date_time, conducted_by_id, notes, recorded_at)
        VALUES
            (ach_maria_1,
             act_maria,
             'Fisioterapia Motora',
             'Mobilização dos membros inferiores para prevenção de trombose e manutenção da amplitude articular.',
             '2025-05-16 09:00:00',
             '2025-05-16 09:45:00',
             user_enf_maria,
             'Residente participou com boa disposição. Amplitude de movimento preservada. Sem intercorrências.',
             '2025-05-16'),

            (ach_maria_2,
             act_maria,
             'Terapia Ocupacional',
             'Atividades manuais com argila para estimulação cognitiva e coordenação motora fina.',
             '2025-05-15 14:00:00',
             '2025-05-15 15:00:00',
             user_enf_maria,
             'Boa adesão. Residente relatou prazer na atividade. Coordenação fina mantida.',
             '2025-05-15'),

            (ach_jose_1,
             act_jose,
             'Caminhada Supervisionada',
             'Caminhada no jardim interno com assistência de enfermeiro para manutenção da mobilidade.',
             '2025-05-15 08:30:00',
             '2025-05-15 09:00:00',
             user_enf_joao,
             'Completou 200m sem intercorrências. Cansaço leve ao final. FC dentro dos limites tolerados.',
             '2025-05-15'),

            (ach_rosa_1,
             act_rosa,
             'Musicoterapia',
             'Sessão de musicalização com instrumentos de percussão simples e canto coral.',
             '2025-05-16 10:00:00',
             '2025-05-16 11:00:00',
             user_enf_maria,
             'Excelente engajamento. Memória afetiva positiva evocada pelas músicas da juventude. Humor elevado após a sessão.',
             '2025-05-16'),

            (ach_antonio_1,
             act_antonio,
             'Exercícios Respiratórios',
             'Técnicas de respiração diafragmática e expansão pulmonar para manutenção da capacidade respiratória.',
             '2025-05-14 09:00:00',
             '2025-05-14 09:30:00',
             user_enf_joao,
             'Saturação mantida acima de 95% durante todo o exercício. Residente colaborativo.',
             '2025-05-14'),

            (ach_luiza_1,
             act_luiza,
             'Estimulação Cognitiva',
             'Leitura de jornal, discussão de eventos atuais e jogos de memória para manutenção das funções cognitivas.',
             '2025-05-16 11:00:00',
             '2025-05-16 11:30:00',
             user_enf_maria,
             'Residente orientada em tempo e espaço. Memória recente levemente comprometida. Excelente humor e cooperação.',
             '2025-05-16')
        ON CONFLICT (id) DO NOTHING;

    END
$$;
