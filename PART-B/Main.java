
import java.util.Scanner;

public class Main {

    // Method to print routing tables
    static void printTable(int n, int[][] dist, int[][] nextHop) {

        for (int i = 0; i < n; i++) {

            System.out.println("\nRouting Table for Router " + (char)(65 + i));
            System.out.println("Destination\tNext Hop\tCost");

            for (int j = 0; j < n; j++) {

                System.out.println(
                    (char)(65 + j) + "\t\t" +
                    (char)(65 + nextHop[i][j]) + "\t\t" +
                    dist[i][j]
                );
            }
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of routers: ");
        int n = sc.nextInt();

        int[][] cost = new int[n][n];
        int[][] dist = new int[n][n];
        int[][] nextHop = new int[n][n];

        System.out.println("\nEnter the cost matrix (use 999 for infinity):");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                cost[i][j] = sc.nextInt();
                dist[i][j] = cost[i][j];
                nextHop[i][j] = j;
            }
        }

        // Print initial routing table
        System.out.println("\nInitial Routing Tables:");
        printTable(n, dist, nextHop);

        // Distance Vector Algorithm
        boolean updated;
        int iteration = 0;

        do {
            updated = false;
            iteration++;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    for (int k = 0; k < n; k++) {

                        if (cost[i][k] != 999 &&
                            dist[k][j] != 999 &&
                            dist[i][j] > cost[i][k] + dist[k][j]) {

                            dist[i][j] = cost[i][k] + dist[k][j];

                            nextHop[i][j] = k;

                            updated = true;
                        }
                    }
                }
            }

            // Print after every iteration
            System.out.println("\n========== ITERATION " + iteration + " ==========");
            printTable(n, dist, nextHop);

        } while (updated);

        System.out.println("\nConverged after " + iteration + " iterations.");

        sc.close();
    }
}
