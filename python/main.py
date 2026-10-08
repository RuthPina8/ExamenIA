from fastapi import FastAPI
from rutas.preprocesamiento import router as preprocesamiento_router
from rutas.camara import router as camara_router
from rutas.imagenes import router as imagenes_router




app = FastAPI(title="ExamenIA - Visión artificial")
app.include_router(preprocesamiento_router)

@app.get("/")
def estado():
    return {"mensaje": "Servidor de visión funcionando"}

app.include_router(camara_router)
app.include_router(imagenes_router)