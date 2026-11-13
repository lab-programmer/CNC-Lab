import java.util.*;

public class bitstuffing {

    static void receiver(ArrayList<Integer> frame) {

        ArrayList<Integer> data = new ArrayList<>();
        int count = 0;

        // Remove starting and ending flags
        for (int i = 8; i < frame.size() - 8; i++) {

            int bit = frame.get(i);

            if (bit == 1) {
                data.add(bit);
                count++;

                // Skip the stuffed 0 after five consecutive 1s
                if (count == 5) {
                    i++;
                    count = 0;
                }
            } 
            else {
                data.add(bit);
                count = 0;
            }
        }

        System.out.println("Data Received: " + data);
    }

    static void sender() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of bits: ");
        int n = sc.nextInt();

        ArrayList<Integer> data = new ArrayList<>();

        System.out.println("Enter " + n + " bits:");

        for (int i = 0; i < n; i++) {
            data.add(sc.nextInt());
        }

        // Flag: 01111110
        int[] flag = {0, 1, 1, 1, 1, 1, 1, 0};

        ArrayList<Integer> frame = new ArrayList<>();

        // Add starting flag
        for (int bit : flag) {
            frame.add(bit);
        }

        int count = 0;

        // Perform bit stuffing
        for (int bit : data) {

            frame.add(bit);

            if (bit == 1) {
                count++;

                if (count == 5) {
                    frame.add(0);
                    count = 0;
                }
            } 
            else {
                count = 0;
            }
        }

        // Add ending flag
        for (int bit : flag) {
            frame.add(bit);
        }

        System.out.println("Sent Frame: " + frame);

        // Call receiver
        receiver(frame);
    }

    public static void main(String[] args) {
        sender();
    }
}   