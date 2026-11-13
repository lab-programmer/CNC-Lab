
import java.util.Scanner;

public class DistanceVector {

    static final int INF = 999;

    // Method to implement Distance Vector Algorithm
    static void distanceVector(int n, int[][] cost) {

        int[][] dist = new int[n][n];
        int[][] nextHop = new int[n][n];

        // Initialize distance and next hop tables
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                dist[i][j] = cost[i][j];
                nextHop[i][j] = j;

            }
        }

        System.out.println("\nInitial Distance Table:");
        printTable(n, dist, nextHop);

        boolean updated = true;
        int iteration = 0;

        // Repeat until no distance changes
        while (updated) {

            updated = false;
            iteration++;

            System.out.println("\n--- Iteration " + iteration + " ---");

            for (int i = 0; i < n; i++) {

                for (int j = 0; j < n; j++) {

                    for (int k = 0; k < n; k++) {

                        // Check whether path through k is shorter
                        if (cost[i][k] != INF &&
                            dist[k][j] != INF &&
                            dist[i][j] > cost[i][k] + dist[k][j]) {

                            dist[i][j] = cost[i][k] + dist[k][j];

                            nextHop[i][j] = k;

                            updated = true;
                        }
                    }
                }
            }

            printTable(n, dist, nextHop);
        }

        System.out.println("\nConverged after " + iteration + " iterations.");
        
        // Display final routing table
        System.out.println("\nFinal Routing Table:");
        printTable(n, dist, nextHop);

        // Display shortest paths
        System.out.println("\nShortest Paths:");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                System.out.print("Router " + (i + 1) +
                    " to Router " + (j + 1) +
                    " : Cost = " + dist[i][j]);

                if (dist[i][j] == INF) {
                    System.out.println(", No path");
                } else if (i == j) {
                    System.out.println(", Same router");
                } else {
                    System.out.println(", Next Hop = Router " +
                        (nextHop[i][j] + 1));
                }
            }
        }
    }

    // Method to print distance table
    static void printTable(int n, int[][] dist, int[][] nextHop) {

        for (int i = 0; i < n; i++) {

            System.out.println("\nRouter " + (i + 1));

            System.out.println("Destination\tCost\tNext Hop");

            for (int j = 0; j < n; j++) {

                System.out.print("Router " + (j + 1) + "\t\t");

                if (dist[i][j] == INF) {
                    System.out.print("INF\t");
                } else {
                    System.out.print(dist[i][j] + "\t");
                }

                System.out.println("Router " + (nextHop[i][j] + 1));
            }
        }
    }

    // Main method
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of routers: ");
        int n = sc.nextInt();

        int[][] cost = new int[n][n];

        System.out.println("\nEnter the cost matrix:");
        System.out.println("Enter 999 if there is no direct connection.");

        for (int i = 0; i < n; i++) {

            System.out.println("Enter row " + (i + 1) + ":");

            for (int j = 0; j < n; j++) {
                cost[i][j] = sc.nextInt();
            }
        }

        distanceVector(n, cost);

        sc.close();
    }
}
