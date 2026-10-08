from fastapi import FastAPI
from rutas.preprocesamiento import router as preprocesamiento_router

app = FastAPI(title="ExamenIA - Visión artificial")
app.include_router(preprocesamiento_router)

@app.get("/")
def estado():
    return {"mensaje": "Servidor de visión funcionando"}