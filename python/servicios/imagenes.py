import cv2
import numpy as np
import base64

def bytes_a_imagen(datos: bytes):
    arreglo = np.frombuffer(datos, np.uint8)
    return cv2.imdecode(arreglo, cv2.IMREAD_COLOR)

def imagen_a_png(imagen) -> bytes:
    ok, buffer = cv2.imencode(".png", imagen)
    return buffer.tobytes()

def imagen_a_base64(imagen) -> str:
    return base64.b64encode(imagen_a_png(imagen)).decode("ascii")

def imagen_a_jpg(imagen) -> bytes:
    ok, buffer = cv2.imencode(".jpg", imagen)
    return buffer.tobytes()



