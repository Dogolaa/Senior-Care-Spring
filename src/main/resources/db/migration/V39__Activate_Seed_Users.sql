-- Garante que todos os usuários seed (V27) estejam ativos e visíveis.
-- Corrige cenários onde migrações anteriores ou testes possam ter desativado
-- ou soft-deletado esses usuários.

UPDATE users
SET is_active    = true,
    deleted_at   = NULL,
    updated_at   = NOW()
WHERE id IN (
    '00000001-0000-0000-0000-000000000001',
    '00000001-0000-0000-0000-000000000002',
    '00000001-0000-0000-0000-000000000003',
    '00000001-0000-0000-0000-000000000004',
    '00000001-0000-0000-0000-000000000005',
    '00000001-0000-0000-0000-000000000006',
    '00000001-0000-0000-0000-000000000007',
    '00000001-0000-0000-0000-000000000008',
    '00000001-0000-0000-0000-000000000009',
    '00000001-0000-0000-0000-000000000010'
);
