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