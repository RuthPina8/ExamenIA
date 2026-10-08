from fastapi import APIRouter, UploadFile, File, Query
from fastapi.concurrency import run_in_threadpool
from datos.imagenes_repo import guardar_imagen
from rutas.preprocesamiento import leer_imagen

router = APIRouter(prefix="/imagenes", tags=["Imágenes"])

@router.post("/guardar")
async def guardar(archivo: UploadFile = File(...), tipo: str = Query("original", max_length=30)):
    imagen = await leer_imagen(archivo)
    nuevo_id = await run_in_threadpool(guardar_imagen, imagen, tipo)
    return {"mensaje": "Imagen guardada", "id": nuevo_id}