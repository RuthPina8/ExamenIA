from fastapi import FastAPI

app = FastAPI(title="ExamenIA - Visión artificial")

@app.get("/")
def estado():
    return {"mensaje": "Servidor de visión funcionando"}