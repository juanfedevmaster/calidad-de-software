-- ParaBank - Script de creación de base de datos y datos de prueba
-- Ejecutar contra una base de datos PostgreSQL vacía llamada "parabank"
-- (o el nombre configurado en DB_URL).
--
-- Uso:
--   psql -h localhost -U parabank_user -d parabank -f scripts/init-db.sql

CREATE TABLE IF NOT EXISTS usuarios (
    id              BIGSERIAL PRIMARY KEY,
    nombre          VARCHAR(150)   NOT NULL,
    usuario         VARCHAR(50)    NOT NULL UNIQUE,
    password_hash   VARCHAR(255)   NOT NULL,
    numero_cuenta   VARCHAR(20)    NOT NULL UNIQUE,
    saldo           NUMERIC(15, 2) NOT NULL DEFAULT 0,
    fecha_creacion  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_usuarios_usuario ON usuarios (usuario);

-- ---------------------------------------------------------------------------
-- Datos dummy para pruebas inmediatas.
-- Contraseña en texto plano para los 3 usuarios: Parabank123!
-- (el hash de abajo es un BCrypt real de esa contraseña, strength 10)
-- ---------------------------------------------------------------------------
INSERT INTO usuarios (nombre, usuario, password_hash, numero_cuenta, saldo)
VALUES
    ('Ana Torres',    'ana.torres',   '$2b$10$EbFdsdRxh0GPpm0GlNcl1eyJySlEDaMsFuC6TfXYVh.B3sgGx.yE6', '1000000001', 8500.00),
    ('Carlos Gomez',  'carlos.gomez', '$2b$10$EbFdsdRxh0GPpm0GlNcl1eyJySlEDaMsFuC6TfXYVh.B3sgGx.yE6', '1000000002', 15320.75),
    ('Juan Fernando', 'juanfe',       '$2b$10$EbFdsdRxh0GPpm0GlNcl1eyJySlEDaMsFuC6TfXYVh.B3sgGx.yE6', '1000000003', 42000.00)
ON CONFLICT (usuario) DO NOTHING;
