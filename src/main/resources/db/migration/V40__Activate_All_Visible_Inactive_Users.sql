-- Ativa todos os usuários que estão visíveis (deleted_at IS NULL) mas
-- marcados como inativos (is_active = false).
-- Corrige dados legados do seed V3 que não foram capturados pelo V31.

UPDATE users
SET is_active  = true,
    updated_at = NOW()
WHERE is_active = false
  AND deleted_at IS NULL;
