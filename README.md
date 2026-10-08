# ExamenIA
Sistema de visión artificial - Examen 1er parcial (Computer Vision)

Interfaz en Java (JavaFX), análisis de imágenes en Python (FastAPI + OpenCV) y base de datos en PostgreSQL.

## Cómo correrlo

1. Crear la base `ExamenIA` en pgAdmin y ejecutar `database/schema.sql` en el Query Tool.
2. Copiar `.env.example`, renombrarlo a `.env` y poner la contraseña de PostgreSQL.
3. Ejecutar `Main.java` desde IntelliJ, o desde la terminal:
   ```
   .\mvnw.cmd javafx:run
   ```

Usuario de prueba: `admin` / `admin123`

## Estructura

- `vistas/` las ventanas (login, registro, principal, preprocesamiento y capas) y las alertas
- `controladores/` lo que hace cada botón y el cambio entre ventanas
- `servicios/` conexión a la base de datos y consultas de usuarios
- `database/` script de las tablas
- `python/` API de FastAPI para el procesamiento de imágenes
- `docs/API.md` rutas de la API que usa Java
