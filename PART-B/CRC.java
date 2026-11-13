
import java.util.Scanner;

public class CRC {

    // CRC-CCITT 16-bit generator polynomial
    static int[] div = {
        1, 0, 0, 0, 1, 0, 0, 0, 0,
        0, 0, 1, 0, 0, 0, 0, 1
    };

    // Function to calculate CRC remainder
    static int[] calculateCRC(int[] data) {

        int n = data.length;
        int m = div.length;

        int[] cdata = new int[n + m - 1];

        // Copy original data and append zeros
        System.arraycopy(data, 0, cdata, 0, n);

        // Perform modulo-2 division
        for (int i = 0; i < n; i++) {
            if (cdata[i] == 1) {
                for (int j = 0; j < m; j++) {
                    cdata[i + j] = cdata[i + j] ^ div[j];
                }
            }
        }

        // Extract remainder
        int[] remainder = new int[m - 1];

        for (int i = 0; i < m - 1; i++) {
            remainder[i] = cdata[n + i];
        }

        return remainder;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of bits: ");
        int n = sc.nextInt();

        int[] data = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter bit " + (i + 1) + ": ");
            data[i] = sc.nextInt();
        }

        // Calculate CRC
        int[] crc = calculateCRC(data);

        System.out.print("Initial codeword: ");
        for (int bit : data) {
            System.out.print(bit);
        }

        System.out.print("\nCRC Remainder: ");
        for (int bit : crc) {
            System.out.print(bit);
        }

        // Generate final codeword
        int[] codeword = new int[n + crc.length];

        System.arraycopy(data, 0, codeword, 0, n);
        System.arraycopy(crc, 0, codeword, n, crc.length);

        System.out.print("\nFinal Codeword: ");
        for (int bit : codeword) {
            System.out.print(bit);
        }

        // Transmission
        System.out.println("\nCodeword to be transmitted.");

        // Receiver side
        System.out.print("\nIntroduce error? (y/n): ");
        char error = sc.next().charAt(0);

        int[] received = codeword.clone();

        if (error == 'y' || error == 'Y') {

            System.out.print("Enter error position (1 to "
                    + received.length + "): ");

            int pos = sc.nextInt();

            if (pos < 1 || pos > received.length) {
                System.out.println("Invalid error position!");
                sc.close();
                return;
            }

            // Flip the selected bit
            received[pos - 1] = 1 - received[pos - 1];
        }

        System.out.print("Received Data: ");
        for (int bit : received) {
            System.out.print(bit);
        }

        // Check received codeword
        int[] remainder = calculateCRC(received);

        System.out.print("\nRemainder: ");
        for (int bit : remainder) {
            System.out.print(bit);
        }

        // Error detection
        boolean errorDetected = false;

        for (int bit : remainder) {
            if (bit != 0) {
                errorDetected = true;
                break;
            }
        }

        if (errorDetected) {
            System.out.println("\nError detected!");
        } else {
            System.out.println("\nNo Error detected.");
        }

        sc.close();
    }
}
