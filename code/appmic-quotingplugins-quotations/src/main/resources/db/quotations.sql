-- ==========================================
-- 1. TABLA MÓDULOS (La Cabecera de la Receta)
-- ==========================================
DROP TABLE IF EXISTS module_materials;
DROP TABLE IF EXISTS modules;

CREATE TABLE modules
(
    id                        INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Identificador único del módulo',
    name                      VARCHAR(100) NOT NULL UNIQUE COMMENT 'Nombre comercial del módulo (ej: Basico 8N100)',
    description               VARCHAR(255) COMMENT 'Descripción técnica o comercial para mostrar en cotizaciones',
    dimensions                VARCHAR(100) COMMENT 'Dimensiones generales del mueble completo (Alto x Ancho x Fondo)',
    is_active                 TINYINT(1)    DEFAULT 1 COMMENT '1 = Activo/Visible para cotizar, 0 = Obsoleto',
    overheads_cost_percentage DECIMAL(5, 2) DEFAULT 0.00 COMMENT 'Porcentaje de Gastos Generales (ej: 3.00)',
    fee_percentage            DECIMAL(5, 2) DEFAULT 0.00 COMMENT 'Porcentaje de FEE administrativo',
    rebate_percentage         DECIMAL(5, 2) DEFAULT 0.00 COMMENT 'Porcentaje de Rebate/Devolución',
    profit_margin_percentage  DECIMAL(5, 2) DEFAULT 0.00 COMMENT 'Margen de utilidad esperado (ej: 10.00 o 12.00)',
    created_at                TIMESTAMP     DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de creación del registro'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='Catálogo principal de módulos fabricables';

-- ==========================================
-- 2. TABLA MATERIALES DEL MÓDULO (La Receta)
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

DROP TABLE IF EXISTS quotation_detail_subitem;
DROP TABLE IF EXISTS quotation_detail;
DROP TABLE IF EXISTS quotation;

-- ==========================================
-- 3. TABLA COTIZACIÓN
-- ==========================================
CREATE TABLE quotation
(
    id                       INT AUTO_INCREMENT PRIMARY KEY,
    -- DATOS ADMINISTRATIVOS
    customer_document_number VARCHAR(20)    NOT NULL COMMENT 'Número de documento del cliente al que se hizo la cotización',
    employee_document_number VARCHAR(20)    NOT NULL COMMENT 'Número de documento del empleado que hizo la cotización',
    date                     DATETIME                DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha en la que se registro la cotización',
    state                    VARCHAR(20)    NOT NULL COMMENT 'BORRADOR, EN REVISIÓN, APROBADO, RECHAZADO',
    -- TOTALES DE LA VENTA (Suma de todos los módulos)
    total_production_cost    DECIMAL(10, 2) NULL DEFAULT 0.00 COMMENT 'Costo real para la empresa',
    total_final_price        DECIMAL(10, 2) NULL DEFAULT 0.00 COMMENT 'Precio final al cliente (con IGV/Impuestos si aplica)'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- ==========================================
-- 4. TABLA DETALLE DE COTIZACIÓN
-- ==========================================
CREATE TABLE quotation_detail
(
    id                       INT AUTO_INCREMENT PRIMARY KEY,
    quotation_id             INT            NOT NULL,
    module_id                INT            NOT NULL,
    quantity                 INT            NOT NULL,
    -- 1. COSTOS DIRECTOS (Base)
    transportation_cost      DECIMAL(10, 2)          DEFAULT 0.00,
    labor_cost               DECIMAL(10, 2)          DEFAULT 0.00,
    packing_cost             DECIMAL(10, 2)          DEFAULT 0.00,
    -- 2. SNAPSHOT FINANCIERO - GASTOS GENERALES
    overheads_percentage     DECIMAL(5, 2)  NOT NULL DEFAULT 0.00 COMMENT 'Ej: 3.00%',
    overheads_amount         DECIMAL(10, 2) NOT NULL DEFAULT 0.00 COMMENT 'Ej: S/. 8.84',
    -- 3. SNAPSHOT FINANCIERO - FEE
    fee_percentage           DECIMAL(5, 2)  NOT NULL DEFAULT 0.00,
    fee_amount               DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    -- 4. SNAPSHOT FINANCIERO - REBATE
    rebate_percentage        DECIMAL(5, 2)  NOT NULL DEFAULT 0.00,
    rebate_amount            DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    -- 5. SNAPSHOT FINANCIERO - UTILIDAD
    profit_margin_percentage DECIMAL(5, 2)  NOT NULL DEFAULT 0.00 COMMENT 'Ej: 12.00%',
    profit_margin_amount     DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    -- 6. TOTALES DE LÍNEA
    unit_production_cost     DECIMAL(10, 2) NULL COMMENT 'Costo real base',
    unit_final_price         DECIMAL(10, 2) NULL COMMENT 'Precio venta unitario',
    total_line_price         DECIMAL(10, 2) NULL COMMENT 'Precio total (Unit * Cantidad)',

    CONSTRAINT fk_quotation_details FOREIGN KEY (quotation_id) REFERENCES quotation (id) ON DELETE CASCADE,
    CONSTRAINT fk_quotation_details_module FOREIGN KEY (module_id) REFERENCES moduleS (id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- ==========================================
-- 5. TABLA DETALLE DE COTIZACIÓN SUB-ITEM
-- ==========================================

CREATE TABLE quotation_detail_subitem
(
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    quotation_detail_id INT            NOT NULL COMMENT 'ID del detalle de la cotización',
    material_id         INT            NOT NULL COMMENT 'ID del material',
    -- CANTIDAD (DECIMAL OBLIGATORIO)
    -- Ej: 20.63 kg (Si usas SMALLINT se guardará 20 y perderás dinero)
    quantity            DECIMAL(12, 4) NOT NULL COMMENT 'Cantidad de material',
    -- COSTO MATERIA PRIMA (VITAL)
    -- Aquí guardas los S/. 89.00 de la Plancha o los S/. 5.00 del Alambre
    raw_material_cost   DECIMAL(10, 4) NOT NULL COMMENT 'Costo de materia prima',
    -- DATOS DE RENDIMIENTO (Para calcular el precio)
    pieces              INT            NULL DEFAULT 1 COMMENT 'Divisor. Para Plancha es 35, para Alambre es 1',
    -- PRECIOS CALCULADOS
    -- unit_price = raw_material_cost / pieces
    unit_price          DECIMAL(10, 4) NOT NULL COMMENT 'El costo real por pieza (Ej: 2.54). Para hacer el cálculo unit_price = raw_material_cost / pieces',
    -- price = unit_price * quantity
    total_price         DECIMAL(10, 2) NOT NULL COMMENT 'Costo total de este material para 1 módulo',
    CONSTRAINT fk_detail FOREIGN KEY (quotation_detail_id) REFERENCES quotation_detail (id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;