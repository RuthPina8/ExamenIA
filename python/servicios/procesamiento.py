import numpy as np
import cv2 

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