import java.util.concurrent.TimeUnit;
import java.util.Scanner;

/**
 * This creates the buffer and the producer and consumer threads.
 *
 */
public class Factory {
    // ================================================================================
    // instance variables for simulation
    public static long run_time;
    public static int pk_interarrival_time;
    public static int pk_service_time;
    public static int buffer_size;
    private static int numProducers;
    private static int numConsumers;
    private static Thread[] producerArr;
    private static Thread[] consumerArr;
    // ================================================================================

    // get simulation parameters from user
    private static void setParams() {
        Scanner kb = new Scanner(System.in);

        System.out.println("Enter number of Networks (Producers):");
        numProducers = kb.nextInt();
        producerArr = new Thread[numProducers];

        System.out.println("Enter number of Firewalls (Consumers):");
        numConsumers = kb.nextInt();
        consumerArr = new Thread[numConsumers];

        System.out.println("Enter packet interarrival time (in milliseconds):");
        pk_interarrival_time = kb.nextInt();
        System.out.println("Enter packet service time (in milliseconds):");
        pk_service_time = kb.nextInt();
        System.out.println("Enter FIFO-queue buffer size:");
        buffer_size = kb.nextInt();
        System.out.println("Enter simulation run time (in seconds):");
        run_time = kb.nextLong();

        kb.close();
        System.out.println("\nSimulation running for " + run_time + " seconds...\n\n");
    }

    public static void main(String[] args) {
        setParams();
        Buffer server = new BoundedBuffer();

        // create the Producer and Consumer Threads
        for (int i = 0; i < numProducers; i++) {
            producerArr[i] = new Thread(new Producer(server, pk_interarrival_time, pk_service_time));
            producerArr[i].start();
        }
        for (int i = 0; i < numConsumers; i++) {
            consumerArr[i] = new Thread(new Consumer(server));
            consumerArr[i].start();
        }

        // track when to stop simulation
        try {
            TimeUnit.SECONDS.sleep(run_time);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("failed");
        }

        // stop Producer and Consumer Threads
        for (int i = 0; i < numProducers; i++) { producerArr[i].stop(); }
        for (int i = 0; i < numConsumers; i++) { consumerArr[i].stop(); }

        // print stats on each processed packet for debugging purposes
//        for(Packet p : Packet.pProcessedList){
//            System.out.println(p + " end time: " + p.end_time + " process time: " + p.process_time + " service time: "
//                    + p.service_time + " wait time: " + p.wait_time);
//        }

        // print packet statistics
        Packet.printPacketStatistics();
    }
}
