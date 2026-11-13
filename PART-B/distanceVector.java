import java.util.Scanner;

public class distanceVector {
   static int INF = 999;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of routers : ");
        int n = sc.nextInt();

        int[][] cost = new int[n][n];
        int[][] dist = new int[n][n];
        int[][] nextHop = new int[n][n];

        for (int i = 0; i < n; i++) {
            System.out.print("Row " + (i + 1) + " : ");
            for (int j = 0; j < n; j++) {
                cost[i][j] = sc.nextInt();
                dist[i][j] = cost[i][j];
                nextHop[i][j] = j;
            }
        }

        System.out.println("\nInitial Distance Table :");
        printTables(n, dist, nextHop);

        boolean updated;
        int iteration = 0;

        do {
            updated = false;
            iteration++;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    for (int k = 0; k < n; k++) {
                        if (dist[i][j] > cost[i][k] + dist[k][j]) {
                            dist[i][j] = cost[i][k] + dist[k][j];
                            nextHop[i][j] = nextHop[i][k];
                            updated = true;
                        }
                    }
                }
            }
            if (updated) {
                System.out.println("\n= = = Iteration " + iteration + " = = =");
                printTables(n, dist, nextHop);
            }
        } while (updated);

        System.out.println("\nConverged after " + iteration + " iteration.");
        sc.close();
    }

    static void printTables(int n, int[][] dist, int[][] nextHop) {
        for (int i = 0; i < n; i++) {
            System.out.println("\nRouter " + (char) (65 + i) + ":");
            System.out.println("Destination\tCost\tNext-hop");
            for (int j = 0; j < n; j++) {
                String c = (dist[i][j] >= INF) ? "INF" : String.valueOf(dist[i][j]);
                System.out.println((char) (65 + j) + "\t\t" + c + "\t" + (char) (65 + nextHop[i][j]));
            }
        }
    }
}