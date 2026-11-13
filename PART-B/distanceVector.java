import java.util.Scanner;

public class DistanceVector {
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

        // Distance Vector Algorithm
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
                            nextHop[i][j] = k;
                            updated = true;
                        }
                    }
                }
            }
        } while (updated);

        System.out.println("\nConverged after " + iteration + " iterations.");

        // Print Routing Tables
        for (int i = 0; i < n; i++) {
            System.out.println("\nRouting Table for Router " + (char) (65 + i) + ":");
            System.out.println("Destination\tNext Hop\tCost");
            for (int j = 0; j < n; j++) {
                System.out.println((char) (65 + j) + "\t\t" + (char) (65 + nextHop[i][j]) + "\t\t" + dist[i][j]);
            }
        }
        sc.close();
    }
}
