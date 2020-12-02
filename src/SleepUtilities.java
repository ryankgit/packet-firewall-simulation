/**
 * Utilities for causing a thread to sleep.
 * Note, we should be handling interrupted exceptions
 * but choose not to do so for code clarity.
 *
 */

public class SleepUtilities {

    // sleeps threads
    public static void nap(double duration) {
        try {
            // Producer duration = interarrivalTime
            // Consumer duration = p.service_time
            // ==================================================================
            // random sleep mode:
//            long rand_duration = (long) (duration * (-Math.log(Math.random())));
//            Thread.sleep(rand_duration);
            // ==================================================================
            // static sleep mode:
            Thread.sleep((long)duration);
            // ==================================================================

        }
        catch (InterruptedException e) {
            System.out.println("Error in Thread.sleep(duration): " + e);
        }
    }
}
