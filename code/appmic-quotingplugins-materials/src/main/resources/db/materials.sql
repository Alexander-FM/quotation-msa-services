-- ==================== TABLA MATERIALS
-- Create Sequence
CREATE SEQUENCE materials_id_seq
    START WITH 1
    INCREMENT BY 1;
-- Table
CREATE TABLE materials
(
    id                       INTEGER PRIMARY KEY DEFAULT NEXTVAL('materials_id_seq'),
    name                     VARCHAR(100)   NOT NULL,
    description              VARCHAR(255),
    -- PRECIO DE COMPRA (Lo que te cobra el proveedor)
    unit_cost                DECIMAL(10, 2) NOT NULL,          -- Ej.: 89.00 (Plancha), 5.00 (Alambre), Se está tomando de la Materia Prima
    unit_of_measurement_name VARCHAR(20)    NOT NULL,          -- Ej.: 'plancha', 'kg', 'm2'
    -- CEREBRO DE CÁLCULO
    -- Valores: 'SIMPLE', 'YIELD_BASED' (Plancha), 'VOLUME_DM' (Poliestireno), 'ÁREA' (Impresión)
    calculation_type         VARCHAR(30)    NOT NULL,
    -- PARAMETROS FÍSICOS DEL MATERIAL
    adjustment_factor_name   VARCHAR(100)   DEFAULT 1.00,      -- Ej.: 'Factor Poliestireno'
    adjustment_factor_value  DECIMAL(10, 2),                    -- Ej.: 1.05 (Factor Poliestireno)
    thickness_microns        INTEGER        NULL,              -- Ej.: 1000, 1500 (Solo para Poliestireno)
    is_active                BOOLEAN        DEFAULT TRUE
);
-- Unique Values
ALTER TABLE materials
    ADD CONSTRAINT unique_materials_thickness_microns UNIQUE (thickness_microns);
-- Insert
INSERT INTO materials (name, description, unit_cost, unit_of_measurement_name, calculation_type,
                       adjustment_factor_name, adjustment_factor_value, thickness_microns, is_active)
VALUES ('Alambre trefilado', NULL, 5.00, 'KG', 'SIMPLE', NULL, NULL, NULL, TRUE),
       ('Poliestireno 1000 micras', NULL, 15.00, 'KG', 'VOLUME_DM', 'Factor Poliestireno', 1.05, 1000, TRUE);
-- Query
SELECT *
FROM materials;