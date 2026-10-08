// Cliente HTTP que llama a la API de Python (filtros, cámara, guardar)
package com.examenia.demo.servicios;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VisionCliente {

    private static final String URL_BASE = "http://localhost:8000";
    private static final HttpClient cliente = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();

    public static byte[] filtro(String tipo, byte[] imagen) throws IOException {
        String ruta = switch (tipo) {
            case "gris", "hsv", "negativa" -> "/preprocesamiento/" + tipo;
            case "rojo", "verde", "azul" -> "/preprocesamiento/destacar-" + tipo;
            default -> throw new IllegalArgumentException("Filtro desconocido: " + tipo);
        };
        return enviarImagen(ruta, imagen);
    }

    public static byte[] gamma(byte[] imagen, double valor) throws IOException {
        return enviarImagen("/preprocesamiento/gamma?valor=" + formatear(valor), imagen);
    }

    public static Map<String, byte[]> separarCapas(byte[] imagen) throws IOException {
        byte[] respuesta = enviarImagen("/preprocesamiento/separar-capas", imagen);
        String json = new String(respuesta, StandardCharsets.UTF_8);

        Pattern patron = Pattern.compile("\"(\\w+)\"\\s*:\\s*\"([A-Za-z0-9+/=]+)\"");
        Matcher m = patron.matcher(json);

        Map<String, byte[]> capas = new LinkedHashMap<>();
        while (m.find()) {
            capas.put(m.group(1), Base64.getDecoder().decode(m.group(2)));
        }
        return capas;
    }

    public static void encenderCamara() throws IOException {
        enviar(HttpRequest.newBuilder(uri("/camara/encender"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build());
    }

    public static byte[] frame() throws IOException {
        return enviar(HttpRequest.newBuilder(uri("/camara/frame")).GET().build());
    }

    public static void apagarCamara() throws IOException {
        enviar(HttpRequest.newBuilder(uri("/camara/apagar"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build());
    }

    public static void guardar(byte[] imagen, int usuarioId, String tipo, Double valorGamma) throws IOException {
        String ruta = "/imagenes/guardar?usuario_id=" + usuarioId + "&tipo=" + tipo;
        if (valorGamma != null) {
            ruta += "&valor_gamma=" + formatear(valorGamma);
        }
        enviarImagen(ruta, imagen);
    }

    public static String explicar(IOException ex) {
        if (ex instanceof ConnectException) {
            return "No se pudo conectar con el servidor de Python.\n¿Está corriendo uvicorn?";
        }
        return ex.getMessage();
    }

    private static byte[] enviarImagen(String ruta, byte[] imagen) throws IOException {
        String limite = "----ExamenIA" + UUID.randomUUID();

        ByteArrayOutputStream cuerpo = new ByteArrayOutputStream();
        cuerpo.writeBytes(("--" + limite + "\r\n"
                + "Content-Disposition: form-data; name=\"archivo\"; filename=\"imagen\"\r\n"
                + "Content-Type: application/octet-stream\r\n\r\n").getBytes(StandardCharsets.UTF_8));
        cuerpo.writeBytes(imagen);
        cuerpo.writeBytes(("\r\n--" + limite + "--\r\n").getBytes(StandardCharsets.UTF_8));

        HttpRequest peticion = HttpRequest.newBuilder(uri(ruta))
                .header("Content-Type", "multipart/form-data; boundary=" + limite)
                .POST(HttpRequest.BodyPublishers.ofByteArray(cuerpo.toByteArray()))
                .build();
        return enviar(peticion);
    }

    private static byte[] enviar(HttpRequest peticion) throws IOException {
        try {
            HttpResponse<byte[]> respuesta = cliente.send(peticion, HttpResponse.BodyHandlers.ofByteArray());
            if (respuesta.statusCode() != 200) {
                String detalle = new String(respuesta.body(), StandardCharsets.UTF_8);
                throw new IOException("El servidor respondió " + respuesta.statusCode() + ": " + detalle);
            }
            return respuesta.body();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new IOException("La petición fue interrumpida", ex);
        }
    }

    private static URI uri(String ruta) {
        return URI.create(URL_BASE + ruta);
    }

    private static String formatear(double valor) {
        return String.format(Locale.US, "%.2f", valor);
    }
}