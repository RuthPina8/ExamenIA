import json
from datos.conexion import obtener_conexion

def guardar_imagen(imagen, tipo):
    alto, ancho = imagen.shape[:2]
    pixeles = json.dumps(imagen.reshape(-1, 3).tolist())
    conexion = obtener_conexion()
    try:
        with conexion:
            with conexion.cursor() as cursor:
                cursor.execute(
                    "INSERT INTO imagenes (tipo, ancho, alto, pixeles) VALUES (%s, %s, %s, %s) RETURNING id",
                    (tipo, ancho, alto, pixeles),
                )
                return cursor.fetchone()[0]
    finally:
        conexion.close()