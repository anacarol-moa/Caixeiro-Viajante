/*
Heuristica do Vizinho mais Próximo:
*/
package caixeiro;

public class HeuristicaVP {

    private GrafoInstancia grafo;
    private long distanciaTotal;
    private int[] melhorRota;

    public HeuristicaVP(GrafoInstancia grafo) {

         if (grafo == null || grafo.getNumeroCidades() <= 0) {
            throw new IllegalArgumentException(
                "O grafo precisa possuir pelo menos uma cidade."
            );
        }

        this.grafo = grafo;
        this.distanciaTotal = 0;
        this.melhorRota = new int[grafo.getNumeroCidades()];
    }

    public void resolver() {

        int n = grafo.getNumeroCidades();

        boolean[] visitada = new boolean[n]; //indicar se cada cidade ja foi visitada (default=false)

        int cidadeAtual = 0; //começa pela cidade 0
        melhorRota[0] = cidadeAtual;
        visitada[cidadeAtual] = true;

        distanciaTotal = 0;

        //vai procurar a cidade mais próxima que ainda não visitada
        for (int posicao = 1; posicao < n; posicao++) {

            int proximaCidade = -1;
            int menorDistancia = Integer.MAX_VALUE;

            //percorre todas as cidades ignorando as que ja foram visitadas
            for (int cidade = 0; cidade < n; cidade++) {

                if (!visitada[cidade]) {

                    int distancia = grafo.getDistancia(
                        cidadeAtual, cidade
                    );

                    if (distancia < menorDistancia) {
                        menorDistancia = distancia;
                        proximaCidade = cidade;
                    }
                }
            }
            
            melhorRota[posicao] = proximaCidade;
            visitada[proximaCidade] = true;

            distanciaTotal += menorDistancia;

            cidadeAtual = proximaCidade; //atualiza qual é a cidade atual agora
        }

        distanciaTotal += grafo.getDistancia(cidadeAtual, 0);
    }

    public long getDistanciaTotal() {
        return distanciaTotal;
    }

    public int[] getMelhorRota() {
        return melhorRota.clone();
    }
}
