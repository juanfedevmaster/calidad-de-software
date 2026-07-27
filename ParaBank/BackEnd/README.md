# ParaBank - BackEnd

API REST para ParaBank construida en **Java 17 + Spring Boot 3**, siguiendo
**arquitectura limpia** (domain / application / infrastructure / interfaces)
y principios **SOLID**.

## Arquitectura de paquetes

```
com.parabank.backend
├── domain/               # Núcleo: modelos y puertos, sin dependencias de framework
│   ├── model/            # User (POJO)
│   ├── repository/       # UserRepository (interfaz / puerto)
│   └── exception/        # Excepciones de negocio
├── application/          # Casos de uso
│   ├── service/          # UserService, AuthService (interfaces)
│   ├── service/impl/     # Implementaciones (dependen solo de abstracciones)
│   └── security/         # TokenProvider (puerto)
├── infrastructure/       # Detalles técnicos (adaptadores)
│   ├── persistence/      # Entidad JPA, repositorio Spring Data, mapper, adapter
│   └── security/         # JWT, filtros, Spring Security config
├── interfaces/           # Capa expuesta al mundo exterior
│   ├── rest/             # Controllers
│   ├── dto/              # DTOs de entrada/salida
│   └── exception/        # @RestControllerAdvice
└── config/               # OpenAPI/Swagger
```

**SOLID aplicado:**
- **SRP:** `UserService` (cuenta/saldo/contraseña) y `AuthService` (login) están separados a propósito.
- **OCP/DIP:** los servicios dependen de interfaces (`UserRepository`, `TokenProvider`, `PasswordEncoder`), no de sus implementaciones concretas (JPA, JWT). Se pueden sustituir sin tocar la capa de aplicación.
- **LSP:** `UserRepositoryAdapter` y `JwtTokenProvider` son sustituibles por cualquier otra implementación de sus interfaces.
- **ISP:** interfaces pequeñas y específicas (`UserService`, `AuthService`, `TokenProvider`) en vez de una sola interfaz gigante.

## Requisitos previos

- Java 17+
- Maven 3.9+
- PostgreSQL 14+

## Configuración

1. Crea la base de datos y ejecuta el script de inicialización:

   ```bash
   createdb parabank
   psql -h localhost -U parabank_user -d parabank -f scripts/init-db.sql
   ```

2. Copia `.env.example` a `.env` y ajusta los valores (especialmente
   `DB_USERNAME` / `DB_PASSWORD`):

   ```bash
   cp .env.example .env
   ```

   El `.env` se carga automáticamente al arrancar la app (vía `spring-dotenv`)
   y **no se sube al repositorio** (está en `.gitignore`).

3. Levanta la aplicación:

   ```bash
   mvn spring-boot:run
   ```

4. Documentación interactiva (Swagger UI):

   ```
   http://localhost:8080/swagger-ui.html
   ```

## Usuarios de prueba (del script `init-db.sql`)

| Usuario        | Contraseña      | Saldo inicial |
|----------------|-----------------|---------------|
| `ana.torres`   | `Parabank123!`  | 8,500.00      |
| `carlos.gomez` | `Parabank123!`  | 15,320.75     |
| `juanfe`       | `Parabank123!`  | 42,000.00     |

## Endpoints principales

| Método | Endpoint                    | Auth | Descripción                        |
|--------|------------------------------|------|-------------------------------------|
| POST   | `/api/auth/register`         | No   | Registra usuario y devuelve JWT     |
| POST   | `/api/auth/login`            | No   | Autentica y devuelve JWT            |
| GET    | `/api/users/me/balance`      | Sí   | Saldo del usuario autenticado       |
| PUT    | `/api/users/me/password`     | Sí   | Cambia la contraseña                |

Para los endpoints protegidos, enviar el header:
`Authorization: Bearer <token>`

## Nota

Este backend **aún no está conectado** con la UI en React del proyecto.
