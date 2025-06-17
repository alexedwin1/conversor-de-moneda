package com.ditel.conversormonedas.models;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ServicioDeDivisasAPI {

    // método que obtiene los datos de la API y los deserializa para ser recibidos en el Record
    public RespuestaTipoDeCambio obtenerTiposDeCambio(String monedaBase) {
        URI direccion = URI.create("https://v6.exchangerate-api.com/v6/d605ee2f17e3590c5d7a725f/latest/" + monedaBase);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();

        HttpResponse<String> response = null;
        try {
            response = client              //response recibe los datos
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return gson.fromJson(response.body(), RespuestaTipoDeCambio.class);
    }

    // método que devuelve el valor de conversion de la moneda, a partir del método anterior.
    public double convertir(String monedaOrigen, String monedaDestino, double cantidad)
            throws IOException, InterruptedException {

        RespuestaTipoDeCambio respuesta = obtenerTiposDeCambio(monedaOrigen);

        if (respuesta == null || respuesta.conversionRates() == null) {
            throw new RuntimeException("No se pudieron obtener los tipos de cambio");
        }

        if (!respuesta.conversionRates().containsKey(monedaDestino)) {
            throw new IllegalArgumentException("Moneda de destino no encontradda: " + monedaDestino);
        }

        double tipoCambio = respuesta.conversionRates().get(monedaDestino);
        return cantidad * tipoCambio;
    }

}
