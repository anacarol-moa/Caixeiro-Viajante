
package caixeiro;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class GrafoInstancia {

    private int numeroCidades;
    private int[][] distancias;

    public GrafoInstancia(String caminhoArquivo) throws IOException {

        String formato = null;
        boolean encontrouSecao = false;

        try (BufferedReader leitor =
                 new BufferedReader(new FileReader(caminhoArquivo))) {

            String linha;

            //le o cabecalho do arquivo txt ate encontrar a secao dos pesos
            while ((linha = leitor.readLine()) != null) {

                linha = linha.trim();

                if (linha.startsWith("EDGE_WEIGHT_SECTION")) {
                    encontrouSecao = true;
                    break;
                }

                int posicao = linha.indexOf(':');

                if (posicao >= 0) {

                    String chave =
                        linha.substring(0, posicao).trim();

                    String valor =
                        linha.substring(posicao + 1).trim();

                    if (chave.equals("DIMENSION")) {
                        numeroCidades = Integer.parseInt(valor);
                    }

                    if (chave.equals("EDGE_WEIGHT_FORMAT")) {
                        formato = valor;
                    }
                }
            }

            if (!encontrouSecao ||
                numeroCidades <= 0 ||
                formato == null) {

                throw new IOException(
                    "Cabecalho TSPLIB invalido: " + caminhoArquivo
                );
            }

            boolean superior =
                formato.equals("UPPER_DIAG_ROW");

            boolean inferior =
                formato.equals("LOWER_DIAG_ROW");

            if (!superior && !inferior) {
                throw new IOException(
                    "Formato de matriz nao suportado: " + formato
                );
            }

            distancias = new int[numeroCidades][numeroCidades];

            //o Scanner separa os numeros por qualquer espaco ou quebra de linha. Paramos antes de ler secoes que vierem depois
            try (Scanner pesos = new Scanner(leitor)) {

                for (int i = 0; i < numeroCidades; i++) {

                    int inicio = superior ? i : 0;
                    int fim = superior ? numeroCidades - 1 : i;

                    for (int j = inicio; j <= fim; j++) {

                        if (!pesos.hasNextInt()) {
                            throw new IOException(
                                "Quantidade insuficiente de pesos em: "
                                + caminhoArquivo
                            );
                        }

                        int distancia = pesos.nextInt();

                        //preenche os dois sentidos da distancia
                        distancias[i][j] = distancia;
                        distancias[j][i] = distancia;
                    }
                }
            }
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
