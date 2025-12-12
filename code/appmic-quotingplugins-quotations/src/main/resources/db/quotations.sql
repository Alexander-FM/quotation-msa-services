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
    overheads_cost DECIMAL(5, 2) NOT NULL,
    fee            DECIMAL(5, 2) NULL,
    rebate         DECIMAL(5, 2) NULL,
    profit_margin  DECIMAL(5, 2) NULL,
    module_id      INTEGER NOT NULL,
    CONSTRAINT fk_module_concepts_module FOREIGN KEY (module_id) REFERENCES module (id)
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
COMMENT
ON COLUMN module_concepts.module_id IS 'Referencia al módulo';
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
    -- Referencia al otro micro servicio (Solo guardamos el ID)
    module_id     INTEGER NOT NULL,
    material_id   INTEGER NULL,
    -- CANTIDAD BASE
    quantity         DECIMAL(10, 2) NOT NULL, -- Ej.: 8.00 (cenefas) o 20.63 (kg)
    -- PARÁMETROS DE CONFIGURACIÓN POR DEFECTO (La "Receta")
    -- Estos valores se copiarán a la cotización cuando se elija este módulo
    description_ref  VARCHAR(150),      -- Ej.: "105.0 x 5.0 - cenefas"
    -- Opcionales (Dependen del tipo de material)
    default_yield    INTEGER NULL,      -- Ej.: 35 (Para Planchas)
    default_width    DECIMAL(10, 2) NULL, -- Ej: 75.0 (Para Poliestireno)
    default_length   DECIMAL(10, 2) NULL  -- Ej: 30.0 (Para Poliestireno)
    CONSTRAINT fk_material_module_module FOREIGN KEY (module_id) REFERENCES module (id)
);
-- Query
SELECT *
FROM material_module;

-- ==================== TABLE QUOTATION
CREATE TABLE quotation
(
    id                       INTEGER PRIMARY KEY AUTO_INCREMENT,
    customer_document_number VARCHAR(20)    NOT NULL,
    employee_document_number VARCHAR(20)    NOT NULL,
    date                     DATE NULL,
    registration_date        DATE NULL,
    state                    SMALLINT       NOT NULL,
    overheads_cost           DECIMAL(10, 2) NOT NULL,
    fee                      DECIMAL(10, 2) NOT NULL,
    rebate                   DECIMAL(10, 2) NOT NULL,
    unit_total               DECIMAL(10, 2) NOT NULL,
    suggested_total          DECIMAL(10, 2) NOT NULL,
    total                    DECIMAL(10, 2) NOT NULL
);
-- Query
SELECT *
FROM quotation;

-- ==================== TABLE QUOTATION DETAILS
CREATE TABLE quotation_detail
(
    id              INTEGER PRIMARY KEY AUTO_INCREMENT,
    quotation_id    INTEGER        NOT NULL,
    module_id       INTEGER        NOT NULL,
    quantity        SMALLINT       NOT NULL,
    overheads_cost  DECIMAL(10, 2) NOT NULL,
    estimate_time   SMALLINT       NOT NULL,
    fee             DECIMAL(10, 2) NOT NULL,
    rebate          DECIMAL(10, 2) NOT NULL,
    transportation  DECIMAL(10, 2) NOT NULL,
    labor           DECIMAL(10, 2) NOT NULL,
    packing         DECIMAL(10, 2) NOT NULL,
    unit_total      DECIMAL(10, 2) NOT NULL,
    suggested_total DECIMAL(10, 2) NOT NULL,
    total           DECIMAL(10, 2) NOT NULL
);
-- Query
SELECT *
FROM quotation_detail;

-- ==================== TABLE QUOTATION DETAILS SUB ITEM
CREATE TABLE quotation_detail_subitem
(
    id                  INTEGER PRIMARY KEY AUTO_INCREMENT,
    quotation_detail_id INTEGER        NOT NULL,
    material_id         INTEGER        NOT NULL,
    quantity            SMALLINT       NOT NULL,
    unit_price          DECIMAL(10, 2) NOT NULL,
    price               DECIMAL(10, 2) NOT NULL,
    pieces              SMALLINT       NOT NULL,
    raw_material_cost   DECIMAL(10, 2) NOT NULL
);
-- Query
SELECT *
FROM quotation_detail_subitem;