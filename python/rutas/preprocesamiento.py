from fastapi import APIRouter, UploadFile, File, HTTPException
from fastapi.responses import Response
from servicios.imagenes import bytes_a_imagen, imagen_a_png
from servicios import procesamiento

router = APIRouter(prefix="/preprocesamiento", tags=["Preprocesamiento"])

@router.post("/gris")
async def gris(archivo: UploadFile = File(...)):
    imagen = bytes_a_imagen(await archivo.read())
    if imagen is None:
        raise HTTPException(status_code=400, detail="El archivo no es una imagen válida")
    resultado = procesamiento.gris(imagen)
    return Response(content=imagen_a_png(resultado), media_type="image/png")


async def leer_imagen(archivo: UploadFile):
    imagen = bytes_a_imagen(await archivo.read())
    if imagen is None:
        raise HTTPException(status_code=400, detail="El archivo no es una imagen válida")
    return imagen

def responder_png(imagen):
    return Response(content=imagen_a_png(imagen), media_type="image/png")

@router.post("/gris")
async def gris(archivo: UploadFile = File(...)):
    return responder_png(procesamiento.gris(await leer_imagen(archivo)))

@router.post("/hsv")
async def hsv(archivo: UploadFile = File(...)):
    return responder_png(procesamiento.hsv(await leer_imagen(archivo)))

@router.post("/negativa")
async def negativa(archivo: UploadFile = File(...)):
    return responder_png(procesamiento.negativa(await leer_imagen(archivo)))

@router.post("/destacar-rojo")
async def destacar_rojo(archivo: UploadFile = File(...)):
    return responder_png(procesamiento.destacar_rojo(await leer_imagen(archivo)))

@router.post("/destacar-verde")
async def destacar_verde(archivo: UploadFile = File(...)):
    return responder_png(procesamiento.destacar_verde(await leer_imagen(archivo)))

@router.post("/destacar-azul")
async def destacar_azul(archivo: UploadFile = File(...)):
    return responder_png(procesamiento.destacar_azul(await leer_imagen(archivo)))