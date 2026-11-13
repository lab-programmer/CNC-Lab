
import java.util.Scanner;

class CRC {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int[] div = {1, 0, 0, 1};

        System.out.print("Enter the number of bits: ");
        int n = sc.nextInt();

        int[] data = new int[n];

        // Input data
        for (int i = 0; i < n; i++) {
            System.out.print("Enter bit " + (i + 1) + ": ");
            data[i] = sc.nextInt();

            if (data[i] != 0 && data[i] != 1) {
                System.out.println("Invalid Data");
                return;
            }
        }

        int m = div.length - 1;

        // Sender side
        int[] cdata = new int[n + m];

        System.arraycopy(data, 0, cdata, 0, n);

        // Append zeros automatically
        for (int i = n; i < n + m; i++) {
            cdata[i] = 0;
        }

        System.out.print("Data after appending zeros: ");
        for (int bit : cdata) {
            System.out.print(bit + " ");
        }
        System.out.println();

        // CRC division
        for (int i = 0; i < n; i++) {
            if (cdata[i] == 1) {
                for (int j = 0; j < div.length; j++) {
                    cdata[i + j] = cdata[i + j] ^ div[j];
                }
            }
        }

        // Calculate remainder
        int[] remainder = new int[m];

        for (int i = 0; i < m; i++) {
            remainder[i] = cdata[n + i];
        }

        System.out.print("CRC Remainder: ");
        for (int bit : remainder) {
            System.out.print(bit + " ");
        }
        System.out.println();

        // Generate codeword
        int[] codeword = new int[n + m];

        System.arraycopy(data, 0, codeword, 0, n);
        System.arraycopy(remainder, 0, codeword, n, m);

        System.out.print("Codeword to be transmitted: ");
        for (int bit : codeword) {
            System.out.print(bit + " ");
        }
        System.out.println();

        // Introduce error
        System.out.print("Introduce error (Y/N): ");
        char ch = sc.next().toUpperCase().charAt(0);

        int[] rdata = codeword.clone();

        if (ch == 'Y') {
            System.out.print("Enter the position to introduce error: ");
            int pos = sc.nextInt();

            if (pos < 1 || pos > rdata.length) {
                System.out.println("Invalid position");
                return;
            }

            rdata[pos - 1] = rdata[pos - 1] ^ 1;
        } else if (ch != 'N') {
            System.out.println("Invalid choice");
            return;
        }

        System.out.print("Received data: ");
        for (int bit : rdata) {
            System.out.print(bit + " ");
        }
        System.out.println();

        // Receiver side CRC division
        for (int i = 0; i < rdata.length - m; i++) {
            if (rdata[i] == 1) {
                for (int j = 0; j < div.length; j++) {
                    rdata[i + j] = rdata[i + j] ^ div[j];
                }
            }
        }

        // Check remainder
        boolean error = false;

        System.out.print("Receiver remainder: ");
        for (int i = rdata.length - m; i < rdata.length; i++) {
            System.out.print(rdata[i] + " ");
            if (rdata[i] != 0) {
                error = true;
            }
        }
        System.out.println();

        if (error) {
            System.out.println("Error Detected");
        } else {
            System.out.println("No Error Detected");
        }

        sc.close();
    }
}
