-- =============== MODULE TYPE
-- Create Sequence
CREATE SEQUENCE module_id_seq
    START WITH 1
    INCREMENT BY 1;
-- Table
CREATE TABLE module
(
    id         INTEGER PRIMARY KEY   DEFAULT NEXTVAL('module_id_seq'),
    name       VARCHAR(100) NOT NULL,
    dimensions VARCHAR(500) NULL,
    is_active  BOOLEAN      NOT NULL DEFAULT TRUE
);
-- Unique Values
ALTER TABLE module
    ADD CONSTRAINT module_name UNIQUE (name);
-- Comments
COMMENT
ON TABLE module IS 'Esta tabla contiene información de los módulos por ejemplo, Basico 8N100';
COMMENT
ON COLUMN module.name IS 'Nombre del módulo';
COMMENT
ON COLUMN module.dimensions IS 'Descripción del módulo';
COMMENT
ON COLUMN module.is_active IS 'Verificar si está disponible el módulo';
-- Insert
INSERT INTO module (name, dimensions, is_active)
VALUES ('Basico 8N100', 'Tamaño 230 cm * 110.2 cm * 49.3 cm', true),
       ('Basico 8N80', 'Tamaño 230 cm * 85.8 cm * 49.3 cm', true),
       ('Basico 8N60', 'Tamaño 230 cm * 59.8 cm * 49.3 cm', true);
-- Query
SELECT *
FROM module;

-- =============== MODULE CONCEPTS
-- Create Sequence
CREATE SEQUENCE module_concepts_id_seq
    START WITH 1
    INCREMENT BY 1;
-- Table
CREATE TABLE module_concepts
(
    id             INTEGER PRIMARY KEY DEFAULT NEXTVAL('module_concepts_id_seq'),
    overheads_cost SMALLINT     NOT NULL,
    fee            SMALLINT NULL,
    rebate         SMALLINT NULL,
    profit_margin  DECIMAL NULL,
    module_name    VARCHAR(100) NOT NULL
);
-- Comments
COMMENT
ON TABLE module_concepts IS 'Esta tabla contiene información general del módulo';
COMMENT
ON COLUMN module_concepts.overheads_cost IS 'Gastos generales';
COMMENT
ON COLUMN module_concepts.fee IS 'FEE';
COMMENT
ON COLUMN module_concepts.rebate IS 'Rebate';
COMMENT
ON COLUMN module_concepts.profit_margin IS 'Margen de utilidad';
-- Query
SELECT *
FROM module_concepts;


-- ==================== TABLA MATERIALS MODULE
-- Create Sequence
CREATE SEQUENCE material_module_id_seq
    START WITH 1
    INCREMENT BY 1;
-- Table
CREATE TABLE material_module
(
    id            INTEGER PRIMARY KEY DEFAULT NEXTVAL('material_module_id_seq'),
    quantity      SMALLINT     NOT NULL,
    pieces        SMALLINT NULL,
    module_name   VARCHAR(100) NOT NULL,
    material_name VARCHAR(50) NULL
);
-- Query
SELECT *
FROM material_module;