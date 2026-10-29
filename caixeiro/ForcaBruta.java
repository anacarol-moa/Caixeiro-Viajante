package caixeiro;

public class ForcaBruta {

    private Grafo grafo;

    private long melhorDistancia;
    private int[] melhorRota;
    private int[] rotaAtual;
    private boolean[] visitada;

    public ForcaBruta(Grafo grafo) {

        this.grafo = grafo;

        int n = grafo.getNumeroCidades();

        this.melhorDistancia = Long.MAX_VALUE;
        this.melhorRota = new int[n];

        this.rotaAtual = new int[n];
        this.visitada = new boolean[n];
    }

    public void resolver() {

        int n = grafo.getNumeroCidades();

        // a cidade 0 é a origem.
        rotaAtual[0] = 0;
        visitada[0] = true;

        // começa preenchendo a rota pela posição 1.
        gerarRotas(1);
    }

    private void gerarRotas(int posicao) {

        int n = grafo.getNumeroCidades();

        // caso-base: todas as cidades foram escolhidas.
        if (posicao == n) {

            long distanciaTotal = calcularDistancia(rotaAtual);

            // guarda a melhor rota encontrada.
            if (distanciaTotal < melhorDistancia) {

                melhorDistancia = distanciaTotal;

                // copia a rota para não perder a melhor solução.
                melhorRota = rotaAtual.clone();
            }

            return;
        }

        // experimenta cada cidade que ainda não foi visitada.
        for (int cidade = 1; cidade < n; cidade++) {

            if (!visitada[cidade]) {

                // escolhe a cidade para a posição atual.
                rotaAtual[posicao] = cidade;
                visitada[cidade] = true;

                // continua construindo a rota.
                gerarRotas(posicao + 1);

                // desfaz a escolha para testar outras rotas.
                visitada[cidade] = false;
            }
        }
    }

    private long calcularDistancia(int[] rota) {

        long total = 0;

        // soma as distâncias entre cidades consecutivas.
        for (int i = 0; i < rota.length - 1; i++) {
            total += grafo.getDistancia(rota[i],rota[i + 1]);
        }

        // soma o retorno à cidade inicial.
        total += grafo.getDistancia(rota[rota.length - 1], rota[0]);
        return total;
    }

    public long getMelhorDistancia() {
        return melhorDistancia;
    }

    public int[] getMelhorRota() {
        return melhorRota.clone();
    }
}