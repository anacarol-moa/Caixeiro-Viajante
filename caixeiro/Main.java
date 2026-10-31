package caixeiro;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        // quantidade de cidades que serão testadas.
        int limite = 10;

        System.out.println("Caixero viajante. Método: Força Bruta");
        System.out.println();

        System.out.printf(
            "%-10s %-20s %-20s%n",
            "Cidades",
            "Menor distância",
            "Tempo (ms)"
        );

        System.out.println(
            "--------------------------------------------------------"
        );

        // testa instâncias de 2 até o limite definido.
        for (int n = 2; n <= limite; n++) {

            Grafo grafo = new Grafo(n);

            // gera as distâncias aleatórias.
            grafo.gerarDistanciasAleatorias();

            ForcaBruta solucionador = new ForcaBruta(grafo);

            // mede o tempo da força bruta.
            long inicio = System.nanoTime();

            solucionador.resolver();

            long fim = System.nanoTime();

            double tempoMs = (fim - inicio) / 1_000_000.0;

            // exibe os resultados.
            System.out.printf(
                "%-10d %-20d %-20.6f%n",
                n,
                solucionador.getMelhorDistancia(),
                tempoMs
            );

            // exibe a melhor rota.
            System.out.print("Melhor rota: ");

            int[] rota = solucionador.getMelhorRota();

            for (int cidade : rota) {
                System.out.print(cidade + " -> ");
            }

            // retorna à cidade inicial.
            System.out.println(rota[0]);

            System.out.println();
        }

    // Método da Heuristica do Vizinho mais Proximo
        System.out.println();
        System.out.println(
            "Caixeiro viajante. Metodo: Heuristica do Vizinho Mais Proximo"
        );
        System.out.println();

        String[] arquivos = {
            "caixeiro/si535.tsp.txt",
            "caixeiro/pa561.tsp.txt",
            "caixeiro/si1032.tsp.txt"
        };

        for (String arquivo : arquivos) {

            System.out.println("Instancia: " + arquivo);

            //carrega o grafo a partir do arquivo TSPLIB
            GrafoInstancia grafo =
                new GrafoInstancia(arquivo);

            HeuristicaVP heuristica =
                new HeuristicaVP(grafo);

            long inicio = System.nanoTime();

            heuristica.resolver();

            long fim = System.nanoTime();

            double tempoMs = (fim - inicio) / 1_000_000.0;

            System.out.println(
                "Numero de cidades: "
                + grafo.getNumeroCidades()
            );
            System.out.println(
                "Distancia total: "
                + heuristica.getDistanciaTotal()
            );
            System.out.printf(
                "Tempo de execucao: %.3f ms%n",
                tempoMs
            );

            System.out.println();
        }

        // Método de Aproximação baseada na Árvore Geradora Mínima
        System.out.println();
        System.out.println(
                "Caixeiro viajante. Metodo: Aproximacao por Arvore Geradora Minima (2-aprox)"
        );
        System.out.println();

        for (String arquivo : arquivos) {
            System.out.println("Instancia: " + arquivo);
            GrafoInstancia grafo = new GrafoInstancia(arquivo);
            AproximacaoArvore aproximacao = new AproximacaoArvore(grafo.getDistancias());
            long inicio = System.nanoTime();
            AproximacaoArvore.Resultado resultado = aproximacao.resolver();
            long fim = System.nanoTime();
            double tempoMs = (fim - inicio) / 1_000_000.0;
            System.out.println(
                    "Numero de cidades: "
                            + grafo.getNumeroCidades()
            );
            System.out.println(
                    "Distancia total (Aproximacao): "
                            + resultado.custo
            );
            System.out.printf(
                    "Tempo de execucao: %.3f ms%n",
                    tempoMs
            );

            System.out.println("Rota completa:");
            int[] tour = resultado.tour;

            for (int i = 0; i < tour.length; i++) {
                System.out.print(tour[i]);

                if (i < tour.length - 1) {
                    System.out.print(" -> ");
                }
                if ((i + 1) % 20 == 0) {
                    System.out.println();
                }
            }

            System.out.println(" -> " + tour[0]);
            System.out.println();
        }
    }
}