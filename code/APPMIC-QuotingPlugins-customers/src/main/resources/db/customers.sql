-- ==================== TABLA CUSTOMERS
-- Create Sequence
CREATE SEQUENCE customers_id_seq
    START WITH 1
    INCREMENT BY 1;
-- Table
CREATE TABLE customers
(
    id                 INTEGER PRIMARY KEY   DEFAULT NEXTVAL('customers_id_seq'),
    company_name       VARCHAR(255) NOT NULL,
    document_type_code VARCHAR(20)  NOT NULL,
    document_number    VARCHAR(20)  NOT NULL,
    email              VARCHAR(255) NOT NULL,
    phoneNumber        VARCHAR(20)  NOT NULL,
    phoneNumber2       VARCHAR(20) NULL,
    is_active          BOOLEAN      NOT NULL DEFAULT TRUE
);
-- Unique Values
ALTER TABLE customers
    ADD CONSTRAINT customers_company_name UNIQUE (company_name);
ALTER TABLE customers
    ADD CONSTRAINT customers_document_number UNIQUE (document_number);
-- Comments
COMMENT
ON TABLE customers IS 'Tabla para almacenar información de los clientes';
COMMENT
ON COLUMN customers.company_name IS 'Nombre de la compañia, negocio';
COMMENT
ON COLUMN customers.document_type_code IS 'Código del tipo documento de identidad';
COMMENT
ON COLUMN customers.document_number IS 'Numero de documento';
COMMENT
ON COLUMN customers.is_active IS 'Campo para verificar si el cliente está activo';
-- Insert
INSERT INTO customers (company_name, document_type_code, document_number, email, phoneNumber, phoneNumber2, is_active)
VALUES ('Detech Peru', 'RUC', '20963258741', 'detechperu@gmail.com', '963258741', null, TRUE),
       ('Codesoft Company', 'RUC', '20654789321', 'codesoft@live.com', '917456893', '963258753', TRUE);
-- Query
SELECT *
FROM customers;