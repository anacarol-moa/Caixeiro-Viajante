package caixeiro;

import caixeiro.ArvoreGeradoraMinima.Aresta;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class AproximacaoArvore {
    public static class Resultado {
        public final int[] tour;
        public final long custo;
        public final long custoMST;

        Resultado(int[] tour, long custo, long custoMST) {
            this.tour     = tour;
            this.custo    = custo;
            this.custoMST = custoMST;
        }
        public long limiteSuperior() {
            return 2L * custoMST;
        }
        public List<Integer> tourComoLista() {
            List<Integer> lista = new ArrayList<>(tour.length);
            for (int c : tour) lista.add(c);
            return lista;
        }
        @Override
        public String toString() {
            return String.format(
                    "Tour com %d cidades | custo = %d | limite 2·AGM = %d",
                    tour.length, custo, limiteSuperior());
        }
    }
    private final int[][] distancias;
    private final int numeroCidades;
    public AproximacaoArvore(int[][] distancias) {
        this.distancias    = distancias;
        this.numeroCidades = distancias.length;
    }

    public Resultado resolver() {
        return resolver(0);
    }
    public Resultado resolver(int cidadeInicial) {
        ArvoreGeradoraMinima agm = new ArvoreGeradoraMinima(distancias);
        List<Aresta> arestasMST  = agm.prim();
        List<Integer>[] adj = construirAdjacencia(arestasMST);
        int[] tour = preorder(adj, cidadeInicial);
        long custo    = calcularCusto(tour);
        long custoMST = ArvoreGeradoraMinima.pesoTotal(arestasMST);
        return new Resultado(tour, custo, custoMST);
    }
    @SuppressWarnings("unchecked")
    private List<Integer>[] construirAdjacencia(List<Aresta> arestas) {
        List<Integer>[] adj = new List[numeroCidades];
        for (int i = 0; i < numeroCidades; i++) adj[i] = new ArrayList<>();
        for (Aresta a : arestas) {
            adj[a.origem].add(a.destino);
            adj[a.destino].add(a.origem);
        }
        return adj;
    }
    private int[] preorder(List<Integer>[] adj, int inicio) {
        int[] ordem          = new int[numeroCidades];
        boolean[] visitado   = new boolean[numeroCidades];
        Deque<Integer> pilha = new ArrayDeque<>(numeroCidades);
        pilha.push(inicio);
        int pos = 0;
        while (!pilha.isEmpty()) {
            int v = pilha.pop();
            if (visitado[v]) continue;
            visitado[v] = true;
            ordem[pos++] = v;
            List<Integer> viz = adj[v];
            for (int i = viz.size() - 1; i >= 0; i--) {
                int u = viz.get(i);
                if (!visitado[u]) pilha.push(u);
            }
        }
        return ordem;
    }
    private long calcularCusto(int[] tour) {
        long total = 0;
        for (int i = 0; i < tour.length - 1; i++) {
            total += distancias[tour[i]][tour[i + 1]];
        }
        total += distancias[tour[tour.length - 1]][tour[0]];
        return total;
    }
}