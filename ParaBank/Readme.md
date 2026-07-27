# ParaBank

Aplicación bancaria de demostración con **frontend en React** y **backend en
Java Spring Boot**, ya conectados entre sí.

## Estructura

```
ParaBank/
├── UI/         # React - login, registro, dashboard con saldo
└── BackEnd/    # Spring Boot - Clean Architecture, JWT, JPA, Swagger
```

## Estado actual

- ✅ UI en React (login, registro, dashboard, cambio de contraseña, fondo animado)
- ✅ Backend en Spring Boot (auth JWT, saldo, cambio de contraseña, Swagger, PostgreSQL)
- ✅ UI conectada al backend real (ya no simula usuarios en `localStorage`, solo guarda el token de sesión)
- ⏳ Pendiente: correr y probar todo junto localmente (backend + base de datos + UI)

## Cómo levantarlo (orden recomendado)

1. **Base de datos:** crear la BD PostgreSQL y correr `BackEnd/scripts/init-db.sql`
   (incluye usuarios de prueba, ver `BackEnd/README.md`).
2. **Backend:** copiar `BackEnd/.env.example` a `BackEnd/.env`, ajustar credenciales,
   y correr `mvn spring-boot:run` (queda en `http://localhost:8080`,
   Swagger en `/swagger-ui.html`).
3. **UI:** `cd UI && npm install && npm start` (queda en `http://localhost:3000`,
   ya permitido por CORS del backend).

Más detalle de cada parte en `UI/` y `BackEnd/README.md`.
