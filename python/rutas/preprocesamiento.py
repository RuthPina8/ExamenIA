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