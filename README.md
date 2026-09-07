# Sistema de Registro — Programa de Fidelidad

Arquitectura Cliente-Servidor:
- **Backend**: Java 17 + Spring Boot 3 + Spring Data JPA + H2 (en memoria).
- **Frontend**: HTML5 + CSS3 + Vanilla JavaScript (Fetch API).

## Estructura del proyecto

```
fidelidad-app/
├── backend/
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/retail/fidelidad/
│       │   ├── FidelidadApplication.java        (main)
│       │   ├── model/                           (entidades JPA)
│       │   │   ├── TipoIdentificacion.java
│       │   │   ├── Pais.java
│       │   │   ├── Departamento.java
│       │   │   ├── Ciudad.java
│       │   │   ├── Marca.java
│       │   │   └── Cliente.java
│       │   ├── repository/                      (Spring Data JPA)
│       │   ├── dto/                              (ClienteRequestDTO / ResponseDTO)
│       │   ├── controller/                       (REST Controllers)
│       │   │   ├── CatalogoController.java
│       │   │   └── ClienteController.java
│       │   └── config/
│       │       ├── DataLoader.java              (datos iniciales)
│       │       └── WebConfig.java               (CORS)
│       └── resources/
│           └── application.properties
└── frontend/
    ├── index.html
    ├── style.css
    └── script.js
```

## 1. Ejecutar el Backend

Requiere JDK 17+ y Maven 3.8+.

```bash
cd backend
mvn spring-boot:run
```

La API queda disponible en `http://localhost:8080`.

- Consola H2 (opcional, para inspeccionar datos): `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:fidelidaddb`
  - Usuario: `sa` / Password: (vacío)

### Endpoints expuestos

| Método | Endpoint                                   | Descripción                                   |
|--------|---------------------------------------------|------------------------------------------------|
| GET    | `/api/tipos-identificacion`                | Lista tipos de identificación                  |
| GET    | `/api/paises`                              | Lista países                                   |
| GET    | `/api/departamentos?paisId={id}`           | Departamentos filtrados por país               |
| GET    | `/api/ciudades?departamentoId={id}`        | Ciudades filtradas por departamento            |
| GET    | `/api/marcas`                              | Lista de marcas                                |
| POST   | `/api/clientes`                            | Registra un nuevo cliente                      |
| GET    | `/api/clientes`                            | Lista clientes registrados (uso administrativo)|

Ejemplo de payload para `POST /api/clientes`:

```json
{
  "tipoIdentificacionId": 1,
  "numeroIdentificacion": "1020304050",
  "nombres": "Laura Andrea",
  "apellidos": "Gómez Ruiz",
  "fechaNacimiento": "1995-04-12",
  "direccion": "Cra 45 # 12-30",
  "paisId": 1,
  "departamentoId": 1,
  "ciudadId": 1,
  "marcaId": 3
}
```

Los datos de catálogo (tipos de identificación, marcas, país/departamentos/ciudades de prueba) se insertan automáticamente al arrancar la aplicación mediante `DataLoader` (`CommandLineRunner`), ya que la base de datos H2 es en memoria y se reinicia en cada ejecución.

## 2. Ejecutar el Frontend

El frontend es estático (no requiere build). Simplemente:

1. Abre `frontend/index.html` directamente en el navegador, **o**
2. Sírvelo con un servidor simple para evitar restricciones de `file://`:

```bash
cd frontend
python3 -m http.server 5500
```

Luego visita `http://localhost:5500`. El JavaScript apunta por defecto a `http://localhost:8080` (variable `API_BASE_URL` en `script.js`); ajústala si cambias el puerto del backend.

> El backend ya incluye `@CrossOrigin` y una configuración CORS global, por lo que puede consumirse desde cualquier origen durante el desarrollo.
