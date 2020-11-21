/**
 * Utilities for causing a thread to sleep.
 * Note, we should be handling interrupted exceptions
 * but choose not to do so for code clarity.
 *
 */

public class SleepUtilities {

    // sleeps threads for random duration
    public static void nap(double duration) {
        try {
            // Producer duration = interarrivalTime
            // Consumer duration = p.service_time
            // calculate random sleep duration
            long rand_duration = (long) (duration * (-Math.log(Math.random())));

            Thread.sleep(rand_duration);
        }
        catch (InterruptedException e) {
            System.out.println("Error in Thread.sleep(duration): " + e);
        }
    }
}