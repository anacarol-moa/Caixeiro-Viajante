package caixeiro;

public class Main {

    public static void main(String[] args) {

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
    }
}