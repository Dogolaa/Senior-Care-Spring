-- Reassigna usuários cujo role_id aponta para um role inexistente (resíduo de
-- versões anteriores do seed V27 que usavam UUIDs diferentes para FAMILY_MEMBER).
UPDATE users
SET role_id = (SELECT id FROM roles WHERE name = 'FAMILY_MEMBER')
WHERE role_id NOT IN (SELECT id FROM roles)
  AND deleted_at IS NULL;
