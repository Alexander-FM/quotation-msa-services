-- ==================== TABLA ROLES
-- Create Sequence
CREATE SEQUENCE roles_seq
    START WITH 1
    INCREMENT BY 1;
-- Table
CREATE TABLE roles
(
    id          INTEGER PRIMARY KEY   DEFAULT NEXTVAL('roles_seq'),
    role_name   VARCHAR(100) NOT NULL,
    description VARCHAR(255) NULL,
    is_active   BOOLEAN      NOT NULL DEFAULT TRUE
);
-- Unique Values
ALTER TABLE roles
    ADD CONSTRAINT roles_role_name UNIQUE (role_name);
-- Insert
INSERT INTO roles (role_name, description, is_active)
VALUES ('ADMIN', 'Administrador del sistema', true),
       ('USER', 'Usuario regular', true);
-- Query
SELECT *
FROM roles;

-- ==================== TABLA USUARIO
-- Create Sequence
CREATE SEQUENCE usuarios_seq
    START WITH 1
    INCREMENT BY 1;
-- Table
CREATE TABLE usuarios
(
    id        INTEGER PRIMARY KEY   DEFAULT NEXTVAL('usuarios_seq'),
    username  VARCHAR(100) NOT NULL,
    password  VARCHAR(255) NOT NULL,
    is_active BOOLEAN      NOT NULL DEFAULT TRUE
);
-- Unique Values
ALTER TABLE usuarios
    ADD CONSTRAINT usuarios_username UNIQUE (username);
-- Insert
INSERT INTO usuarios (username, password, is_active)
VALUES ('XP2345', '$2a$12$1sAlBuYjmk0B.4ZS/JQJGeVUwJmbwleirC/CBhqt9yXN3nikfj242', true),
       ('XP3453', '$2a$12$1sAlBuYjmk0B.4ZS/JQJGeVUwJmbwleirC/CBhqt9yXN3nikfj242', true);
-- Query
SELECT *
FROM usuarios;

-- ==================== TABLA USUARIO_ROLES
-- Table
CREATE TABLE usuarios_roles
(
    usuario_id INTEGER NOT NULL,
    role_id    INTEGER NOT NULL,
    PRIMARY KEY (usuario_id, role_id)
);
-- Insert
INSERT INTO usuarios_roles (usuario_id, role_id)
VALUES ('1', '1'),
       ('1', '2');
-- Query
SELECT *
FROM usuarios_roles;

-- ==================== TABLA EMPLOYEES
-- Create Sequence
CREATE SEQUENCE employees_seq
    START WITH 1
    INCREMENT BY 1;
-- Table
CREATE TABLE employees
(
    id                 INTEGER PRIMARY KEY   DEFAULT NEXTVAL('employees_seq'),
    fullName           VARCHAR(255) NOT NULL,
    document_type_code VARCHAR(20)  NOT NULL,
    document_number    VARCHAR(20)  NOT NULL,
    phone_number       VARCHAR(15)  NOT NULL,
    phone_number2      VARCHAR(15) NULL,
    street_address     VARCHAR(255) NOT NULL,
    user_id            INTEGER      NOT NULL,
    is_active          BOOLEAN      NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_employees_usuario FOREIGN KEY (user_id) REFERENCES usuarios (id)
);
-- Unique Values
ALTER TABLE employees
    ADD CONSTRAINT employees_document_number UNIQUE (document_number);
-- Comments
COMMENT
ON TABLE employees IS 'Tabla para almacenar información de los empleados';
COMMENT
ON COLUMN employees.fullName IS 'Nombre completo del empleado';
COMMENT
ON COLUMN employees.document_type_code IS 'Tipo de documento de identidad';
COMMENT
ON COLUMN employees.document_number IS 'Numero de documento';
COMMENT
ON COLUMN employees.phone_number IS 'Telefono principal del empleado';
COMMENT
ON COLUMN employees.phone_number2 IS 'Telefono secundario del empleado';
COMMENT
ON COLUMN employees.street_address IS 'Dirección de la calle de sus vivienda del empleado';
COMMENT
ON COLUMN employees.is_active IS 'Campo para verificar si el empleado está activo';
-- Insert
INSERT INTO employees (fullName, document_type_code, document_number, phone_number, phone_number2, street_address, user_id, is_active)
VALUES ('Eloy Max Huallama Perez', 'DNI', '89632541', '963258756', null, 'San Jose 423 - Lambayeque', 1);
-- Query
SELECT *
FROM employees;