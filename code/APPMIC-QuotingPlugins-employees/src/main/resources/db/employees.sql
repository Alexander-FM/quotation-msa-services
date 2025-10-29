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
    type_of_employee   VARCHAR(20)  NOT NULL,
    password           VARCHAR(20)  NOT NULL,
    is_active          BOOLEAN      NOT NULL DEFAULT TRUE
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
ON COLUMN employees.type_of_employee IS 'Tipo de empleado si es un empleado ADMIN or USER';
COMMENT
ON COLUMN employees.password IS 'Contraseña encriptada para ingresar al sistema';
COMMENT
ON COLUMN employees.is_active IS 'Campo para verificar si el empleado está activo';
-- Insert
INSERT INTO employees (fullName, document_type_code, document_number, type_of_employee, password, is_active)
VALUES ('Eloy Max Huallama Perez', 'DNI', '36965212', 'USER', 'ELOYADMIN', true),
       ('Esmeralda Hernandez', 'DNI', '45632145', 'USER', 'MILUSCAUSER', true);
-- Query
SELECT *
FROM employees;