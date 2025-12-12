-- ==========================================
-- 1. TABLA MÓDULOS (La Cabecera de la Receta)
-- ==========================================
DROP TABLE IF EXISTS module_materials;
DROP TABLE IF EXISTS module_concepts;
DROP TABLE IF EXISTS modules;

CREATE TABLE modules
(
    id          INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Identificador único del módulo',
    name        VARCHAR(100) NOT NULL UNIQUE COMMENT 'Nombre comercial del módulo (ej: Basico 8N100)',
    description VARCHAR(255) COMMENT 'Descripción técnica o comercial para mostrar en cotizaciones',
    dimensions  VARCHAR(100) COMMENT 'Dimensiones generales del mueble completo (Alto x Ancho x Fondo)',
    is_active   TINYINT(1) DEFAULT 1 COMMENT '1 = Activo/Visible para cotizar, 0 = Obsoleto',
    created_at  TIMESTAMP  DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de creación del registro'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='Catálogo principal de módulos fabricables';

-- ==========================================
-- 2. TABLA CONCEPTOS (Configuración Financiera 1:1)
-- ==========================================
CREATE TABLE module_concepts
(
    id                        INT AUTO_INCREMENT PRIMARY KEY,
    -- Relación 1 a 1: Un concepto pertenece a UN solo módulo
    module_id                 INT NOT NULL UNIQUE COMMENT 'FK hacia modules. Debe ser única para garantizar relación 1:1',
    -- Valores Porcentuales (Guardamos 10.00 para representar 10%)
    overheads_cost_percentage DECIMAL(5, 2) DEFAULT 0.00 COMMENT 'Porcentaje de Gastos Generales (ej: 3.00)',
    fee_percentage            DECIMAL(5, 2) DEFAULT 0.00 COMMENT 'Porcentaje de FEE administrativo',
    rebate_percentage         DECIMAL(5, 2) DEFAULT 0.00 COMMENT 'Porcentaje de Rebate/Devolución',
    profit_margin_percentage  DECIMAL(5, 2) DEFAULT 0.00 COMMENT 'Margen de utilidad esperado (ej: 10.00 o 12.00)',
    CONSTRAINT fk_concepts_module FOREIGN KEY (module_id) REFERENCES modules (id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='Configuración financiera específica por módulo';

-- ==========================================
-- 3. TABLA MATERIALES DEL MÓDULO (La Receta)
-- ==========================================
CREATE TABLE module_materials
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    module_id       INT            NOT NULL COMMENT 'Módulo al que pertenece este ingrediente',
    material_id     INT            NOT NULL COMMENT 'ID del material (Referencia al microservicio de Materiales)',
    -- Cantidad base
    quantity        DECIMAL(10, 2) NOT NULL COMMENT 'Cantidad necesaria para fabricar 1 módulo',
    -- Datos para clonar a la cotización (Snapshot de configuración)
    description_ref VARCHAR(150) COMMENT 'Descripción del corte (ej: 105x5 cenefas)',
    -- Parámetros específicos según el tipo de material
    default_yield   INT            NULL COMMENT 'Solo para Planchas: Cuántas piezas salen (Divisor)',
    default_width   DECIMAL(10, 2) NULL COMMENT 'Solo Poliestireno/Impresión: Ancho en cm',
    default_length  DECIMAL(10, 2) NULL COMMENT 'Solo Poliestireno/Impresión: Largo en cm',
    CONSTRAINT fk_materials_module FOREIGN KEY (module_id) REFERENCES modules (id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='Listado de materiales (ingredientes) necesarios para un módulo';

-- ==================== TABLE QUOTATION
CREATE TABLE quotation
(
    id                       INTEGER PRIMARY KEY AUTO_INCREMENT,
    customer_document_number VARCHAR(20)    NOT NULL,
    employee_document_number VARCHAR(20)    NOT NULL,
    date                     DATE           NULL,
    registration_date        DATE           NULL,
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