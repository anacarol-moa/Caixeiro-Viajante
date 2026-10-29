package caixeiro;

import java.util.Random;

public class Grafo {

    private int numeroCidades;
    private int[][] distancias; // distâncias simétricas. ir de de A para B é o mesmo que ir de B para A.

    public Grafo(int numeroCidades) {
        this.numeroCidades = numeroCidades;
        this.distancias = new int[numeroCidades][numeroCidades];
    }

    public void gerarDistanciasAleatorias() {

        Random random = new Random();

        for (int i = 0; i < numeroCidades; i++) {
            for (int j = i + 1; j < numeroCidades; j++) {

                // distância aleatória entre 1 e 100.
                int distancia = random.nextInt(100) + 1;

                // distâncias simétricas.
                distancias[i][j] = distancia;
                distancias[j][i] = distancia;
            }
        }
    }

    public void imprimirMatriz() {

        System.out.println("Matriz de distâncias:");

        for (int i = 0; i < numeroCidades; i++) {
            for (int j = 0; j < numeroCidades; j++) {
                System.out.printf("%5d", distancias[i][j]);
            }

            System.out.println();
        }
    }

    public int getNumeroCidades() {
        return numeroCidades;
    }

    public int getDistancia(int origem, int destino) {
        return distancias[origem][destino];
    }

    public int[][] getDistancias() {
        return distancias;
    }
}