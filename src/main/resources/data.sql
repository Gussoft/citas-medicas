INSERT INTO usuarios (username, password, rol, activo) VALUES 
('admin', '$2a$10$w81o91s6c3n6i6W4u5SxeOx6n5vR/3vQ01yq1U5H5s6T0A0D1e3mK', 'ROLE_ADMIN', true),
('usuario1', '$2a$10$w81o91s6c3n6i6W4u5SxeOx6n5vR/3vQ01yq1U5H5s6T0A0D1e3mK', 'ROLE_USER', true);

INSERT INTO pacientes (numero_documento, nombres, apellidos, fecha_nacimiento, genero, telefono, direccion, estado) VALUES 
('72345678', 'Carlos', 'Mendoza Perez', '1990-05-15', 'MASCULINO', '987654321', 'Av. Arequipa 1234, Lima', 'ACTIVO'),
('45678901', 'Maria', 'Rodriguez Silva', '1985-08-22', 'FEMENINO', '912345678', 'Calle Las Flores 456, San Isidro', 'ACTIVO'),
('10293847', 'Juan', 'Gomez Fernandez', '2000-11-03', 'MASCULINO', '934567890', 'Jr. Junin 789, Lima', 'INACTIVO');

INSERT INTO seguros_trabajo (paciente_id, numero_afiliacion, empresa, tipo_seguro, fecha_vigencia_inicio, fecha_vigencia_fin, estado) VALUES 
(1, 'ESS-72345678-01', 'Tecnologias del Peru SAC', 'ESSALUD', '2026-01-01', '2026-12-31', 'APTO'),
(2, 'SIS-45678901-02', 'Independiente', 'SIS', '2026-01-01', '2026-12-31', 'APTO'),
(3, 'ESS-10293847-03', 'Comercial Lima EIRL', 'ESSALUD', '2024-01-01', '2025-01-01', 'NO_APTO');
