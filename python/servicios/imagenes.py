import cv2
import numpy as np

def bytes_a_imagen(datos: bytes):
    arreglo = np.frombuffer(datos, np.uint8)
    return cv2.imdecode(arreglo, cv2.IMREAD_COLOR)

def imagen_a_png(imagen) -> bytes:
    ok, buffer = cv2.imencode(".png", imagen)
    return buffer.tobytes()