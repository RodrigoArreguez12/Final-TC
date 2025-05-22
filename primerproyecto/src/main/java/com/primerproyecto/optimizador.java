package com.primerproyecto;

import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.Scanner;

public class optimizador {

    private PrintWriter outOptimizado;

    public optimizador() throws IOException {
        // Inicializa el archivo de salida
        outOptimizado = new PrintWriter("codigo-intermedio-optimizado.txt");
    } /*
       * ************** Funcion de optimizacion de codigo **********************
       * Vamos leyendo cada linea, y chequeamos que si encontramos
       * una linea que comienza con una variable temporal "t**",
       * nos fijamos en la siguiente linea a que variable se le esta asignando
       * esa variable temporal y le asignamos a esta ultima
       * variable lo que tenia asignado la variable temporal en la linea anterior.
       * t0 = a + b;
       * total = t0; lo reemplazamos eliminando t0 y asignando a+b a total --> total =
       * a+b;
       */

    public void optimizarCodigo() {
        System.out.println("----------------OPTIMIZADO----------------------------");
        try (FileInputStream fis = new FileInputStream("D:\\UBP\\TC\\Final F- Tc\\TPFinal-4\\codigo-intermedio.txt");
                Scanner sc = new Scanner(fis)) {

            String lineaAnterior = null;

            while (sc.hasNextLine()) {
                if (lineaAnterior != null) {
                    String splitAnterior[] = lineaAnterior.split(" ");
                    String variable = splitAnterior[0];

                    if (variable.charAt(0) == 't') {
                        String lineaActual = sc.nextLine();
                        String splitActual[] = lineaActual.split(" ");

                        if (splitActual.length == 3) {
                            if (splitActual[2].equals(variable)) {
                                outOptimizado.print(splitActual[0] + " ");
                                for (int i = 1; i < splitAnterior.length; i++) {
                                    outOptimizado.print(splitAnterior[i] + " ");
                                }
                                outOptimizado.println();

                                if (sc.hasNextLine()) {
                                    lineaAnterior = sc.nextLine();
                                } else {
                                    lineaAnterior = null;
                                }
                            } else {
                                outOptimizado.println(lineaAnterior);
                                lineaAnterior = lineaActual;
                            }
                        } else {
                            outOptimizado.println(lineaAnterior);
                            lineaAnterior = lineaActual;
                        }
                    } else {
                        outOptimizado.println(lineaAnterior);
                        lineaAnterior = sc.nextLine();
                    }
                } else {
                    lineaAnterior = sc.nextLine();
                }
            }

            if (lineaAnterior != null) {
                outOptimizado.println(lineaAnterior);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outOptimizado != null) {
                outOptimizado.close();
            }
        }

    }

}
