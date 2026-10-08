# Algoritmos de procesamiento de imagen (filtros, gamma, capas)
import numpy as np
import cv2 


rojoBajo1 = np.array([0, 100, 20], np.uint8)
rojoAlto1 = np.array([8, 255, 255], np.uint8)
rojoBajo2 = np.array([175, 100, 20], np.uint8)
rojoAlto2 = np.array([179, 255, 255], np.uint8)
verdeBajo = np.array([35, 100, 20], np.uint8)
verdeAlto = np.array([85, 255, 255], np.uint8)
azulBajo = np.array([100, 100, 20], np.uint8)
azulAlto = np.array([125, 255, 255], np.uint8)

def gris(imagen):
    
    cAzul = imagen[:, :, 0]
    cVerde = imagen[:, :, 1]
    cRojo = imagen[:, :, 2]
    grayImage = 0.2989 * cRojo + 0.5870 * cVerde + 0.1140 * cAzul
    return grayImage.astype(np.uint8)

def hsv(imagen):
    return cv2.cvtColor(imagen, cv2.COLOR_BGR2HSV)

def negativa(imagen):
    return 255 - imagen

def destacar(imagen, rangos):
    imgHSV = cv2.cvtColor(imagen, cv2.COLOR_BGR2HSV)
    mascara = np.zeros(imagen.shape[:2], np.uint8)
    for bajo, alto in rangos:
        mascara = cv2.add(mascara, cv2.inRange(imgHSV, bajo, alto))
    return cv2.bitwise_and(imagen, imagen, mask=mascara)

def destacar_rojo(imagen):
    return destacar(imagen, [(rojoBajo1, rojoAlto1), (rojoBajo2, rojoAlto2)])

def destacar_verde(imagen):
    return destacar(imagen, [(verdeBajo, verdeAlto)])

def destacar_azul(imagen):
    return destacar(imagen, [(azulBajo, azulAlto)])

def gamma(imagen, valor):
    tabla = np.array([((i / 255.0) ** valor) * 255 for i in range(256)]).astype(np.uint8)
    return cv2.LUT(imagen, tabla)


def separar_capas(imagen):
    
    canales = {
        "rojo": [2], "verde": [1], "azul": [0],
        "magenta": [2, 0], "amarillo": [2, 1], "cian": [1, 0],
    }
    capas = {}
    for nombre, indices in canales.items():
        capa = np.zeros_like(imagen)
        capa[:, :, indices] = imagen[:, :, indices]
        capas[nombre] = capa
    return capas