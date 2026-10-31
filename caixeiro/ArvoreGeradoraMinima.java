package caixeiro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArvoreGeradoraMinima {
    public static class Aresta implements Comparable<Aresta> {
        public final int origem;
        public final int destino;
        public final int peso;

        public Aresta(int origem, int destino, int peso) {
            this.origem  = origem;
            this.destino = destino;
            this.peso    = peso;
        }

        @Override
        public int compareTo(Aresta outra) {
            return Integer.compare(this.peso, outra.peso);
        }

        @Override
        public String toString() {
            return String.format("(%d, %d) peso=%d", origem, destino, peso);
        }
    }

    private final int[][] distancias;
    private final int numeroCidades;

    public ArvoreGeradoraMinima(int[][] distancias) {
        this.distancias    = distancias;
        this.numeroCidades = distancias.length;
    }
    public List<Aresta> kruskal() {
        List<Aresta> arestas = new ArrayList<>(
                numeroCidades * (numeroCidades - 1) / 2);

        for (int i = 0; i < numeroCidades; i++) {
            for (int j = i + 1; j < numeroCidades; j++) {
                arestas.add(new Aresta(i, j, distancias[i][j]));
            }
        }
        Collections.sort(arestas);

        UnionFind uf = new UnionFind(numeroCidades);
        List<Aresta> agm = new ArrayList<>(numeroCidades - 1);

        for (Aresta a : arestas) {
            if (uf.find(a.origem) != uf.find(a.destino)) {
                uf.union(a.origem, a.destino);
                agm.add(a);
                if (agm.size() == numeroCidades - 1) break; // AGM completa
            }
        }
        return agm;
    }
    public List<Aresta> prim() {
        boolean[] visitado = new boolean[numeroCidades];
        int[] menorPeso    = new int[numeroCidades];
        int[] pai          = new int[numeroCidades];

        for (int i = 0; i < numeroCidades; i++) {
            menorPeso[i] = Integer.MAX_VALUE;
            pai[i]       = -1;
        }
        menorPeso[0] = 0;
        List<Aresta> agm = new ArrayList<>(numeroCidades - 1);
        for (int iter = 0; iter < numeroCidades; iter++) {
            int u = -1;
            for (int v = 0; v < numeroCidades; v++) {
                if (!visitado[v] && (u == -1 || menorPeso[v] < menorPeso[u])) {
                    u = v;
                }
            }
            if (u == -1 || menorPeso[u] == Integer.MAX_VALUE) break;
            visitado[u] = true;
            if (pai[u] != -1) {
                agm.add(new Aresta(pai[u], u, menorPeso[u]));
            }
            for (int v = 0; v < numeroCidades; v++) {
                if (!visitado[v] && distancias[u][v] < menorPeso[v]) {
                    menorPeso[v] = distancias[u][v];
                    pai[v]       = u;
                }
            }
        }
        return agm;
    }
    private static final class UnionFind {
        private final int[] pai;
        private final int[] rank;
        UnionFind(int n) {
            pai  = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) pai[i] = i;
        }
        int find(int x) {
            while (pai[x] != x) {
                pai[x] = pai[pai[x]];
                x = pai[x];
            }
            return x;
        }
        void union(int a, int b) {
            int ra = find(a), rb = find(b);
            if (ra == rb) return;
            if      (rank[ra] < rank[rb]) pai[ra] = rb;
            else if (rank[ra] > rank[rb]) pai[rb] = ra;
            else { pai[rb] = ra; rank[ra]++; }
        }
    }
    public static long pesoTotal(List<Aresta> agm) {
        long total = 0;
        for (Aresta a : agm) total += a.peso;
        return total;
    }
}