-- Esquema de la base de datos: tablas usuarios e imagenes + usuario de prueba

DROP TABLE IF EXISTS imagenes;
DROP TABLE IF EXISTS usuarios;

CREATE TABLE usuarios (
    id               SERIAL PRIMARY KEY,
    nombre           VARCHAR(50) NOT NULL,
    apellido_paterno VARCHAR(50) NOT NULL,
    apellido_materno VARCHAR(50) NOT NULL,
    usuario          VARCHAR(30) NOT NULL UNIQUE,
    contrasena       VARCHAR(50) NOT NULL
);


CREATE TABLE imagenes (
    id          SERIAL PRIMARY KEY,
    usuario_id  INTEGER NOT NULL REFERENCES usuarios(id),
    tipo        VARCHAR(20) NOT NULL DEFAULT 'original'
                CHECK (tipo IN ('original', 'gris', 'hsv', 'negativa', 'rojo', 'verde', 'azul', 'gamma')),
    valor_gamma NUMERIC(3,2),
    ancho       INTEGER NOT NULL,
    alto        INTEGER NOT NULL,
    pixeles     JSONB NOT NULL,
    fecha       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- usuario para probar el login
INSERT INTO usuarios (nombre, apellido_paterno, apellido_materno, usuario, contrasena)
VALUES ('Admin', 'Sistema', 'Prueba', 'admin', 'admin123');
