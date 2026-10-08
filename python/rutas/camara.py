from fastapi import APIRouter, HTTPException
from fastapi.responses import Response
from servicios import camara
from servicios.imagenes import imagen_a_jpg

router = APIRouter(prefix="/camara", tags=["Cámara"])

@router.post("/encender")
def encender():
    if not camara.encender():
        raise HTTPException(status_code=500, detail="No se pudo abrir la cámara")
    return {"mensaje": "Cámara encendida"}

@router.get("/frame")
def frame():
    imagen = camara.leer_frame()
    if imagen is None:
        raise HTTPException(status_code=409, detail="La cámara no está encendida")
    return Response(content=imagen_a_jpg(imagen), media_type="image/jpeg")

@router.post("/apagar")
def apagar():
    camara.apagar()
    return {"mensaje": "Cámara apagada"}