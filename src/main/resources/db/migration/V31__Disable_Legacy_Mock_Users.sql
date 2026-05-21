-- Soft-delete dos usuários do seed V3 (dados fictícios com senhas em texto
-- plano incompatíveis com o VO HashedPassword). Senhas BCrypt começam com '$2'.
UPDATE users
SET deleted_at = NOW()
WHERE password NOT LIKE '$2%'
  AND deleted_at IS NULL;
