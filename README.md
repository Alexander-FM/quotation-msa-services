# Proyecto APPMIC - QuotingPlugins

Resumen
- Proyecto modular (Maven / Spring Boot) para el microservicio de cotizaciones y módulos relacionados (catalogs, materials, modules, quotations, customers, employees, commons).
- Cada módulo es un submódulo Maven con su propio `src/main/java` y `src/main/resources`.
- Conexión a PostgreSQL configurada vía `application.yml` usando variables de entorno (con valores por defecto).

Cómo ejecutar (local, Windows cmd.exe)
- Comando:
  - `mvnw.cmd spring-boot:run`

Estructura de carpetas (alto nivel)
- `pom.xml` (workspace / aggregator)
- `APPMIC-QuotingPlugins-*/` (módulos Maven)
  - `APPMIC-QuotingPlugins-catalogs/`
  - `APPMIC-QuotingPlugins-materials/`
  - `APPMIC-QuotingPlugins-modules/`
  - `APPMIC-QuotingPlugins-quotations/`
  - `APPMIC-QuotingPlugins-customers/`
  - `APPMIC-QuotingPlugins-employees/`
  - `APPMIC-QuotingPlugins-modules` (info de módulos)
  - `APPMIC-QuotingPlugins-commons` (contratos compartidos)

Detalle por módulo y función de carpetas/archivos

- `APPMIC-QuotingPlugins-commons/`
  - Propósito: DTOs y excepciones compartidas entre microservicios.
  - Rutas clave:
    - `src/main/java/.../commons/dto/ErrorResponse.java` \- contrato de error.
    - `src/main/java/.../commons/exception/ApiException.java` \- excepción de dominio.
  - Uso: `@RestControllerAdvice` de cada servicio consume `ErrorResponse` y `ApiException`.

- `APPMIC-QuotingPlugins-catalogs/`
  - Propósito: CRUD de catálogos: `catalog_item`, `unit_of_measurement`, `adjustment_factor`.
  - Estructura típica:
    - `src/main/java/.../model/entity/` \- entidades JPA (`CatalogItem`, `UnitOfMeasurement`, `AdjustmentFactor`).
    - `src/main/java/.../dto/request` y `dto/response` \- DTOs con validación Jakarta.
    - `src/main/java/.../repository/` \- interfaces `JpaRepository`.
    - `src/main/java/.../mapper/` \- MapStruct mappers.
    - `src/main/java/.../service/` y `service/impl` \- lógica de negocio.
    - `src/main/java/.../controller/` \- `@RestController` con endpoints CRUD.
    - `src/main/java/.../advice/GlobalExceptionHandler.java` \- `@RestControllerAdvice` local usando `ErrorResponse`.
  - Recursos SQL:
    - `src/main/resources/db/catalogs.sql` \- script de creación e inserts (schema `catalogs`).

- `APPMIC-QuotingPlugins-materials/`
  - Propósito: Tabla `materials` que referencia `unit_of_measurement` y `adjustment_factor`.
  - Recursos:
    - `src/main/resources/db/materials.sql` \- script, inserts y consultas de ejemplo.
  - Estructura Java similar a `catalogs` cuando exista lógica CRUD.

- `APPMIC-QuotingPlugins-modules/`
  - Propósito: Definición de `module`, `module_concepts`, `material_module`.
  - Recursos:
    - `src/main/resources/db/modules.sql`

- `APPMIC-QuotingPlugins-quotations/`
  - Propósito: Lógica de cotizaciones y detalle (quotation, quotation_detail, quotation_detail_subitem).
  - Recursos:
    - `src/main/resources/db/quotations.sql`

- `APPMIC-QuotingPlugins-customers/`
  - Propósito: Gestión de clientes (`customers`).
  - Recursos:
    - `src/main/resources/db/customers.sql`

- `APPMIC-QuotingPlugins-employees/`
  - Propósito: Gestión de empleados (`employees`).
  - Recursos:
    - `src/main/resources/db/employees.sql`

Fin del documento.

