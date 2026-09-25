# ejemplo-autores

Proyecto de practica para aprender la convencion de las skills `backend-spring-boot`
(Clean Architecture / Hexagonal + DDD). Stack: Spring Boot 4.1.1, Java 21, Gradle,
MapStruct 1.6.3, Lombok, JPA, Flyway, Postgres.

## Como trabajamos
- Modo aprendizaje: **mostrar el codigo en el chat con explicacion detallada; no escribir
  archivos** salvo que se pida explicitamente. El usuario lo copia a mano.
- **No generar tests** salvo que se pidan.
- Un vertical slice (un endpoint) a la vez, de afuera hacia adentro:
  Input + contratos minimos -> Application + Domain -> Output. No adelantar otro slice
  (no agregar metodos de otro endpoint al mismo adapter/repository "ya que se esta ahi").
- Antes de codificar cada etapa, exponer un plan breve. Al terminar cada etapa, pedir que
  compile (`.\gradlew compileJava`) antes de seguir.
- Indentacion del proyecto: 4 espacios.

## Convencion
- Paquete base `com.ariel.ejemplo`; clase `EjemploApplication` en esa raiz.
- Subdominio primero, capa adentro: `com.ariel.ejemplo.autor.{domain, application,
  infrastructure.input, infrastructure.output.persistence}`.
- Value Objects como `record` con validacion en el constructor compacto (`NombreAutor`,
  `EmailAutor`; se accede con `nombre.valor()`). Todas las propiedades de la entidad son
  Value Objects salvo `UUID id`. El id lo manda el cliente; el servidor no lo genera.
- Nombres: sin filtro `{Entidad}{Accion}`, con filtro `{Entidad}By{Filtro}{Accion}`.
  Sufijos: `Command`, `Result`, `UseCase`, `UseCaseMapper`, `OutputPort`, `Adapter`,
  `AdapterMapper`, `OutputAdapterMapper`, `Entity`, `Repository`, `Request`, `Response`.
  Adapters de Input: `{Entidad}PostAdapter` / `{Entidad}By{Filtro}GetAdapter`.
- Un solo metodo publico `perform(...)` por use case y por adapter (los `@ExceptionHandler`
  del adapter son `private`).
- Use case: `@Component` (no `@Service`), campos `private final` + `@RequiredArgsConstructor`.
  Los mappers de MapStruct traducen entre capas; los `default` convierten VOs
  (`String -> NombreAutor` en el mapper del use case, `NombreAutor -> String` en el de output).
- Las excepciones de negocio viven en domain y **solo el adapter de Input** las traduce a HTTP:
  `IllegalArgumentException` (de los VOs) -> 400, `AutorNotFoundException` -> 404.
  Todos los errores responden `ErrorResponse(int status, String message)` (record en input).
- Output: `@Transactional` en escrituras, `ddl-auto: validate`, el esquema lo crea Flyway
  (`src/main/resources/db/migration/V{n}__descripcion.sql`).

## Estado
- [x] `POST /api/autores` — completo (Input, Application, Domain, Output) y funcionando.
- [ ] `GET /api/autores/{id}` — **diseñado pero todavia sin codigo en el proyecto**.
      Empezar por la Etapa 1 (ver diseño abajo).

### Diseño del slice `GET /api/autores/{id}` (accion `Finder`, filtro `ById`)
No tocar nada del POST. `AutorRepository` ya tiene `findById` (no agregar metodos).

**Etapa 1 — Input + contratos minimos**
- `application/`: `AutorByIdFinder` (interfaz: `AutorByIdFinderResult perform(AutorByIdFinderCommand)`),
  `AutorByIdFinderCommand` (`UUID id`), `AutorByIdFinderResult` (`UUID id, String nombre, String email`).
  Command y Result: `@Data @AllArgsConstructor`.
- `domain/AutorNotFoundException`: `extends RuntimeException`, ctor `(String message)`.
  Se crea ya en la Etapa 1 porque el adapter de Input la nombra para traducirla a 404.
- `infrastructure/input/AutorByIdFinderResponse` (`id, nombre, email`; `@Data @NoArgsConstructor @AllArgsConstructor`).
- `infrastructure/input/AutorByIdFinderAdapterMapper`: `default toCommand(UUID id)` escrito a mano
  (MapStruct no mapea un UUID suelto a un objeto) + `toResponse(Result)` generado.
- `infrastructure/input/AutorByIdGetAdapter`: `@GetMapping("/{id}")` bajo `@RequestMapping("/api/autores")`,
  `@PathVariable("id") UUID id`, responde `200 OK`. Handlers: `AutorNotFoundException` -> 404 y
  `MethodArgumentTypeMismatchException` (id no UUID) -> 400, ambos con `ErrorResponse`.
- Compila pero la app **no arranca** hasta la Etapa 2 (falta un bean de `AutorByIdFinder`).

**Etapa 2 — Application + Domain**
- `domain/AutorByIdFinderOutputPort`: `Autor perform(UUID id)` (lanza `AutorNotFoundException` si no existe).
- `application/AutorByIdFinderUseCaseMapper`: `Autor -> AutorByIdFinderResult` desempaquetando VOs
  (`default String` para `NombreAutor` y `EmailAutor`).
- `application/AutorByIdFinderUseCase implements AutorByIdFinder`: `perform` llama al puerto y mapea a Result.
  No captura la excepcion de dominio.

**Etapa 3 — Output**
- `infrastructure/output/persistence/AutorByIdFinderOutputAdapterMapper`: `AutorEntity -> Autor`
  reconstruyendo los VOs (`new NombreAutor(...)`, `new EmailAutor(...)`). Es el mapeo inverso
  que el POST no necesito.
- `AutorByIdFinderAdapter implements AutorByIdFinderOutputPort`: `repository.findById(id)`;
  si esta vacio, lanza `AutorNotFoundException("No existe un autor con id " + id)`.

## Entorno
- Postgres local, base `autores` (crearla vacia: `CREATE DATABASE autores;`). Credenciales en
  `src/main/resources/application.yml`.
- Compilar: `.\gradlew compileJava`. Levantar: `.\gradlew bootRun` (puerto 8080).
- Probar el POST:
  `Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/autores -ContentType 'application/json' -Body '{"id":"3f2b8c1e-9a4d-4e7b-8c55-0d1f2a3b4c5d","nombre":"Julio Cortazar","email":"julio@example.com"}'`
- Requiere las skills `backend-spring-boot` (repo `claude-skills`, GitLab interno; puede pedir VPN).
  Instalar: `/plugin marketplace add <ruta al clon>` y `/plugin install backend-spring-boot@claude-skills`.

## Ideas para despues
- Tests de las capas (como piden las skills de cada capa), si se quieren.
- `GET` de listado paginado (skill `paginated-query`).
- Regla de duplicado en el POST para practicar la traduccion de excepciones tecnicas en Output
  (`DataIntegrityViolationException` -> excepcion de dominio -> 409).
