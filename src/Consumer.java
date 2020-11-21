/**
 * This is the consumer thread for the bounded buffer problem.
 */

public class Consumer implements Runnable {
    private Buffer buffer;

    public Consumer(Buffer b) {
        buffer = b;
    }

    public void run() {
        while (true) {
            // remove Packet from buffer
            Packet p = (Packet) buffer.remove();

            // set start packet service time
            Packet.startServiceTime(p);

            // nap for packet service time (defined in Factory)
            SleepUtilities.nap(p.service_time);

            // calculate processed packet info
            Packet.setPacketTimes(p);
        }
    }
}

