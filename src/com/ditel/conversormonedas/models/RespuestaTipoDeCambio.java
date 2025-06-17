package com.ditel.conversormonedas.models;

import java.util.Map;

public record RespuestaTipoDeCambio(
        String baseCode,
        Map<String, Double> conversionRates

) {
}
