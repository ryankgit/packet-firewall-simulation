import java.util.concurrent.TimeUnit;
import java.util.Scanner;

/**
 * This creates the buffer and the producer and consumer threads.
 *
 */
public class Factory {
    // Factory instance variables
    public static long run_time;
    public static int pk_interarrival_time;
    public static int pk_service_time;

    // get simulation parameters from user
    private static void setParams() {
        Scanner kb = new Scanner(System.in);

        System.out.println("Enter simulation run time (seconds):");
        run_time = kb.nextLong();
        System.out.println("Enter packet interarrival time (milliseconds):");
        pk_interarrival_time = kb.nextInt();
        System.out.println("Enter packet service time (milliseconds):");
        pk_service_time = kb.nextInt();

        kb.close();
    }

    public static void main(String args[]) {
        setParams();
        Buffer server = new BoundedBuffer();

        // create the producer and consumer threads
        Thread producerThread = new Thread(new Producer(server, pk_interarrival_time, pk_service_time));
        Thread consumerThread0 = new Thread(new Consumer(server));
//        Thread consumerThread1 = new Thread(new Consumer(server));
//        Thread consumerThread2 = new Thread(new Consumer(server));
        producerThread.start();
        consumerThread0.start();
//        consumerThread1.start();
//        consumerThread2.start();

        System.out.println("Simulation running for " + run_time + " seconds...");

        // used to stop producing/consuming threads
        try {
            TimeUnit.SECONDS.sleep(run_time);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("failed");
        }

        // stop producer and consumer threads
        producerThread.stop();
        consumerThread0.stop();
//        consumerThread1.stop();
//        consumerThread2.stop();

        // print stats on each packet for debugging purposes
//        for(Packet p : Packet.pList){
//            System.out.println(p + " end time: " + p.end_time + " process time: " + p.process_time + " service time: "
//                    + p.service_time + " wait time: " + p.wait_time);
//        }

        // print packet statistics
        Packet.printPacketStatistics();
    }
}