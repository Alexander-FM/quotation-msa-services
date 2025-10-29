-- ==================== TABLA UNIDAD DE MEDIDA
-- Create Sequence
CREATE SEQUENCE unit_of_measurement_id_seq
    START WITH 1
    INCREMENT BY 1;
-- Table
CREATE TABLE unit_of_measurement
(
    id          INTEGER PRIMARY KEY   DEFAULT NEXTVAL('unit_of_measurement_id_seq'),
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NULL,
    is_active   BOOLEAN      NOT NULL DEFAULT TRUE
);
-- Unique Values
ALTER TABLE unit_of_measurement
    ADD CONSTRAINT unique_unit_of_measurement_name UNIQUE (name);
-- Comments
COMMENT
ON TABLE unit_of_measurement IS 'Tabla para almacenar las unidades de medida utilizadas en el sistema.';
COMMENT
ON COLUMN unit_of_measurement.name IS 'Nombre de la unidad de medida.';
COMMENT
ON COLUMN unit_of_measurement.description IS 'Descripción detallada de la unidad de medida.';
-- Insert
INSERT INTO unit_of_measurement (name, description, is_active)
VALUES ('kg', 'Kilogramo', true),
       ('m2', 'Metro Cuadrado', true),
       ('in', 'Pulgadas', true),
       ('cm', 'Centimetros', true),
       ('pza', 'Unidad o Piezas', true);
-- Query
SELECT *
FROM unit_of_measurement;


-- ==================== TABLA CATALOG ITEM
-- Create Sequence
CREATE SEQUENCE catalog_item_id_seq
    START WITH 1
    INCREMENT BY 1;
-- Table
CREATE TABLE catalog_item
(
    id          INTEGER PRIMARY KEY  DEFAULT NEXTVAL('catalog_item_id_seq'),
    category    VARCHAR(50) NOT NULL,
    code        VARCHAR(20) NOT NULL,
    description VARCHAR(255) NULL,
    is_active   BOOLEAN     NOT NULL DEFAULT TRUE
);
-- Unique Values
ALTER TABLE catalog_item
    ADD CONSTRAINT unique_catalog_item_name UNIQUE (code);
-- Comments
COMMENT
ON TABLE catalog_item IS 'Tabla para almacenar datos globales como: tipos de documento, tipos de identificación utilizadas en el sistema.';
COMMENT
ON COLUMN catalog_item.category IS 'Nombre del tipo de categoría.';
COMMENT
ON COLUMN catalog_item.code IS 'Abreviatura del documento';
COMMENT
ON COLUMN catalog_item.description IS 'Descripción del documento';
-- Insert
INSERT INTO catalog_item (category, code, description)
VALUES ('document_type', 'BOL', 'Boleta'),
       ('document_type', 'FAC', 'Factura'),
       ('document_type', 'GR', 'Guía de remisión'),
       ('identification_type', 'DNI', 'Documento Nacional de Identidad'),
       ('identification_type', 'RUC', 'Registro Único de Contribuyentes'),
       ('identification_type', 'PASS', 'Pasaporte');
-- Query
SELECT *
FROM catalog_item;


-- ==================== TABLA ADJUSTMENT FACTOR
-- Create Sequence
CREATE SEQUENCE adjustment_factor_id_seq
    START WITH 1
    INCREMENT BY 1;
-- Table
CREATE TABLE adjustment_factor
(
    id        INTEGER PRIMARY KEY     DEFAULT NEXTVAL('adjustment_factor_id_seq'),
    name      VARCHAR(255)   NOT NULL,
    value     DECIMAL(10, 2) NOT NULL,
    is_active BOOLEAN        NOT NULL DEFAULT TRUE
);
-- Unique Values
ALTER TABLE adjustment_factor
    ADD CONSTRAINT unique_adjustment_factor_name UNIQUE (name);
ALTER TABLE adjustment_factor
    ADD CONSTRAINT check_adjustment_factor_value_gt_zero CHECK (value > 0);
-- Comments
COMMENT
ON TABLE adjustment_factor IS 'Esta tabla contiene los factores que se aplican a diferentes materiales, como el 1.05 para el poliestireno.';
COMMENT
ON COLUMN adjustment_factor.name IS 'Nombre del factor de ajuste (por ejemplo, "Factor Poliestireno").';
COMMENT
ON COLUMN adjustment_factor.value IS 'El valor del factor de ajuste (Por ejemplo, 1.05).';
-- Insert
INSERT INTO adjustment_factor (name, value)
VALUES ('Factor Poliestireno', 1.05);
-- Query
SELECT *
FROM adjustment_factor;