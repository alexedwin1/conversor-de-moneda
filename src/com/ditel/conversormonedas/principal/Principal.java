package com.ditel.conversormonedas.principal;

import com.ditel.conversormonedas.models.ServicioDeDivisasAPI;

import java.io.IOException;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ServicioDeDivisasAPI servicio = new ServicioDeDivisasAPI();
        var opcion = -1;
        String menu = """
                
                ************************************************
                Bienvenido al Conversor de Monedas.
                            
                1. Dólar  -----------> Peso Argentino
                2. Peso Argentino ---> Dólar
                3. Dólar ------------> Real Brasileño
                4. Real Brasileño ---> Dólar
                5. Dólar ------------> Peso Colombiano
                6. Peso Colombiano --> Dólar
                0. Salir
                                
                ************************************************
                """;

        while(opcion != 0){
            System.out.println(menu);
            System.out.println("Elija una opción de conversión de moneda:");

            try {
                opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1:
                        realizarConversion(servicio, teclado, "USD", "ARS", "Dólares", "Pesos Argentinos");
                        break;
                    case 2:
                        realizarConversion(servicio, teclado, "ARS", "USD", "Pesos Argentinos", "Dólares");
                        break;
                    case 3:
                        realizarConversion(servicio, teclado, "USD", "BRL", "Dólares", "Reales Brasileños");
                        break;
                    case 4:
                        realizarConversion(servicio, teclado, "BRL", "USD", "Reales Brasileños", "Dólares");
                        break;
                    case 5:
                        realizarConversion(servicio, teclado, "USD", "COP", "Dólares", "Pesos Colombianos");
                        break;
                    case 6:
                        realizarConversion(servicio, teclado, "COP", "USD", "Pesos Colombianos", "Dólares");
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema...");
                        System.out.println("Salida con éxito!");
                        break;
                    default:
                        System.out.println("Opción no disponible, seleccione una opción válida");
                }

            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }

        teclado.close();
    }

    //método que pide el ingreso de cantidad a convertir y usa los la clase ServicioDeDivisasAPI y el método convertir.
    private static void realizarConversion(ServicioDeDivisasAPI servicio, Scanner teclado,
                                           String monedaOrigen, String monedaDestino,
                                           String nombreOrigen, String nombreDestino) {
        try {
            System.out.println("Ingrese la cantidad de " + nombreOrigen + " a convertir: ");
            double cantidad = Double.parseDouble(teclado.nextLine());

            if (cantidad < 0) {
                System.out.println("La cantidad no puede ser negativa.");
                return;
            }

            double resultado = servicio.convertir(monedaOrigen, monedaDestino, cantidad);

            System.out.printf("%.2f %s equivalen a %.2f %s%n",
                    cantidad, nombreOrigen, resultado, nombreDestino);

        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingrese un número válido.");
        } catch (IOException e) {
            System.out.println("Error de conexión con la API: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("La operación fue interrumpida.");
            Thread.currentThread().interrupt();
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
        }
    }
}
