-- Insérer les rôles s'ils n'existent pas déjà
INSERT INTO roles (role_name)
SELECT 'ROLE_ADMIN'
    WHERE NOT EXISTS (SELECT 1 FROM roles WHERE role_name = 'ROLE_ADMIN');

INSERT INTO roles (role_name)
SELECT 'ROLE_USER'
    WHERE NOT EXISTS (SELECT 1 FROM roles WHERE role_name = 'ROLE_USER');

-- Insérer l'utilisateur par défaut s'il n'existe pas déjà
INSERT INTO users (user_name, email, password, user_status, create_date, update_date)
SELECT 'admin', 'admin@example.com', '$2a$12$sttewXdyV.KK31ny73oNB.0Uxk5DR7pK8d93pSLw9t.mzXEpyznxu', 'ACTIVE', NOW(), NOW()
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@example.com');

-- Assigner les rôles à l'utilisateur par défaut
INSERT INTO users_roles (users_id, roles_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.email = 'admin@example.com' AND r.role_name IN ('ROLE_ADMIN', 'ROLE_USER')
  AND NOT EXISTS (SELECT 1 FROM users_roles WHERE users_id = u.id AND roles_id = r.id);