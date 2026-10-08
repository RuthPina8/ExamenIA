import threading
import cv2

captura = None
candado = threading.Lock()

def encender():
    global captura
    with candado:
        if captura is None or not captura.isOpened():
            captura = cv2.VideoCapture(0)
        return captura.isOpened()

def leer_frame():
    with candado:
        if captura is None or not captura.isOpened():
            return None
        ret, imagen = captura.read()
    return cv2.flip(imagen, 1) if ret else None

def apagar():
    global captura
    with candado:
        if captura is not None:
            captura.release()
            captura = None