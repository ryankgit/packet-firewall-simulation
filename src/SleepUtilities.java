/**
 * Utilities for causing a thread to sleep.
 * Note, we should be handling interrupted exceptions
 * but choose not to do so for code clarity.
 *
 */

public class SleepUtilities {

    public static void nap(int duration) {
        try {
            // Producer duration = interarrivalTime
            // Consumer duration = (int)p.service_time
            Thread.sleep(duration);
        }
        catch (InterruptedException e) {
            System.out.println("Error in Thread.sleep(duration): " + e);
        }
    }

}