-- ==================== TABLA MATERIALS
-- Create Sequence
CREATE SEQUENCE materials_id_seq
    START WITH 1
    INCREMENT BY 1;
-- Table
CREATE TABLE materials
(
    id                     INTEGER PRIMARY KEY  DEFAULT NEXTVAL('materials_id_seq'),
    name                   VARCHAR(50) NOT NULL,
    description            VARCHAR(255) NULL,
    unit_cost              DECIMAL     NOT NULL,
    width                  DECIMAL NULL,
    length                 DECIMAL NULL,
    height                 DECIMAL NULL,
    used_formula           BOOLEAN     NOT NULL DEFAULT FALSE,
    is_active              BOOLEAN     NOT NULL DEFAULT TRUE,
    unidad_name            VARCHAR(20) NOT NULL,
    adjustment_factor_name VARCHAR(40) NULL
);
-- Unique Values
ALTER TABLE materials
    ADD CONSTRAINT unique_materials_name UNIQUE (name);
-- Comments
COMMENT
ON TABLE materials IS 'Esta tabla contender diferentes materiales';
COMMENT
ON COLUMN materials.name IS 'Nombre del material';
COMMENT
ON COLUMN materials.description IS 'Descripción del material';
COMMENT
ON COLUMN materials.unit_cost IS 'Precio unitario base del material';
COMMENT
ON COLUMN materials.used_formula IS 'Flag para indicar si se usa los campos width, length, height para formula';
COMMENT
ON COLUMN materials.is_active IS 'Verificar si el material está disponible';
COMMENT
ON COLUMN materials.unidad_name IS 'Unidad de medida';
COMMENT
ON COLUMN materials.adjustment_factor_name IS 'Factor ajuste, usado para los materiales con unidad de medida m2';
-- Insert
INSERT INTO materials (name, description, unit_cost, width, length, height, used_formula, is_active, unidad_name, adjustment_factor_name)
VALUES ('Alambre trefilado', NULL, 103.15, NULL, NULL, NULL, FALSE, TRUE, 'kg', NULL),
       ('Poliestireno', '1000 micras', 3.54, 7.5, 3, 0.01, TRUE, TRUE, 'cm', 'Factor Poliestireno');
-- Query
SELECT *
FROM materials;
SELECT *
FROM quoting_db.catalogs.unit_of_measurement;
SELECT *
FROM quoting_db.catalogs.adjustment_factor;