from typing import Literal
from fastapi import APIRouter, UploadFile, File, Query, HTTPException
from fastapi.concurrency import run_in_threadpool
from psycopg2 import errors
from datos.imagenes_repo import guardar_imagen
from rutas.preprocesamiento import leer_imagen

router = APIRouter(prefix="/imagenes", tags=["Imágenes"])

TipoImagen = Literal["original", "gris", "hsv", "negativa", "rojo", "verde", "azul", "gamma"]

@router.post("/guardar")
async def guardar(
    archivo: UploadFile = File(...),
    usuario_id: int = Query(...),
    tipo: TipoImagen = Query("original"),
    valor_gamma: float | None = Query(None, ge=0, le=2),
):
    imagen = await leer_imagen(archivo)
    try:
        nuevo_id = await run_in_threadpool(guardar_imagen, imagen, tipo, usuario_id, valor_gamma)
    except errors.ForeignKeyViolation:
        raise HTTPException(status_code=400, detail="El usuario no existe")
    return {"mensaje": "Imagen guardada", "id": nuevo_id}