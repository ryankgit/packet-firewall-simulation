import java.util.ArrayList;

/**
 * This class you create to track information about each packet,
 * such as create time, end time, and generated service time.
 * The Packet must track when or whether the packet was created,
 * queued or discarded, the required processing time,
 * and the time the packet was completed processing (end time).
 */
public class Packet {
    // ================================================================================
    // Packet specific info
    public double create_time;
    public double actual_service_time; // arrival time, set in Consumer
    public double end_time;
    public double turnaround_time; // turnaround time = service time + wait time
    public double service_time; // service_time = end time - start service time
    public double wait_time; // wait time = process time - service time
    public String cqd; // Packet created, queued, or discarded

    // Packet maximums, initialized to 0
    public static double maxServiceTime = 0;
    //public static double maxProcessTime = 0;
    public static double maxTurnaroundTime = 0;
    public static double maxWaitTime = 0;

    // Packet averages
    public static double avgServiceTime;
    //public static double avgProcessTime;
    public static double avgTurnaroundTime;
    public static double avgWaitTime;

    // total service time of all packets; used to calculate utilization
    public static double totalServiceTime;

    // arraylists to keep track of added and dropped packets; added to in Producer based on Packet status in BoundedBuffer
    public static ArrayList<Packet> pProcessedList = new ArrayList<>();
    public static ArrayList<Packet> pDropList = new ArrayList<>();
    // ================================================================================

    // Constructor: packets created by Producer; service time predefined in Factory
    public Packet(double serviceTime) {
        create_time = System.currentTimeMillis();
        // service time set in Producer; predefined in Factory
        service_time = serviceTime;
        // default when new packet is created; queued and dropped cqd assigned in Producer
        cqd = "created";
    }

    // start tracking actual Packet service time; called in Consumer
    public static void startServiceTime(Packet p){
        p.actual_service_time = System.currentTimeMillis();
    }

    // call when packet done processing
    public static void setPacketTimes(Packet p) {
        p.end_time = System.currentTimeMillis();
        p.service_time = p.end_time - p.actual_service_time;
        p.wait_time = p.actual_service_time - p.create_time;
        p.turnaround_time = p.service_time + p.wait_time;

        // check for new packet maximums
        updatePacketMaximums(p);
    }

    private static void updatePacketMaximums(Packet p){
        // Check for new maxServiceTime, maxWaitTime, maxTurnaroundTime
        if (p.service_time > maxServiceTime) {
            maxServiceTime = p.service_time;
        }
        if (p.wait_time > maxWaitTime) {
            maxWaitTime = p.wait_time;
        }
        if (p.turnaround_time > maxTurnaroundTime) {
            maxTurnaroundTime = p.turnaround_time;
        }
    }

    private static void setPacketStatistics() {
        // add up total Packet turnaround, wait, service times:
        for (Packet p : pProcessedList) {
            avgTurnaroundTime += p.turnaround_time;
            avgWaitTime += p.wait_time;
            avgServiceTime += p.service_time;
        }
        totalServiceTime = avgServiceTime;
        // calculate averages based on only processed packages
        double packetsProcessed = pProcessedList.size();
        // find average Packet process, service, wait times:
        avgTurnaroundTime = avgTurnaroundTime / packetsProcessed;
        avgWaitTime = avgWaitTime / packetsProcessed;
        avgServiceTime = avgServiceTime / packetsProcessed;
    }

    public static void printPacketStatistics() {
        // generate packet statistics
        setPacketStatistics();

        double processedPackets = pProcessedList.size();
        double droppedPackets = pDropList.size();
        double totalPackets = processedPackets + droppedPackets;

        // print packet statistics:
        System.out.println("\nPercent of discarded packets: " + (droppedPackets / totalPackets) * 100
                + "% (" + droppedPackets + " out of " + totalPackets + " packets)");
        System.out.println("\nAverage Service Time: " + avgServiceTime + "ms");
        System.out.println("Max Service Time: " + maxServiceTime + "ms");
        System.out.println("Average Turn Around Time: " + avgTurnaroundTime + "ms");
        System.out.println("Max Turn Around Time: " + maxTurnaroundTime + "ms");
        System.out.println("Average Wait Time: " + avgWaitTime + "ms");
        System.out.println("Max Wait Time: " + maxWaitTime + "ms");

        System.out.println("\nExpected Processor Utilization: " + ((double) Factory.pk_service_time / Factory.pk_interarrival_time) * 100 + "%");
        System.out.println("Actual Processor Utilization: " + (totalServiceTime / (Factory.run_time * 1000)) * 100 +
                "% (Total Service Time of " + totalServiceTime + "ms / Program Run Time of " + Factory.run_time * 1000.0 + "ms)");
        System.out.println("Processor Throughput: " + totalPackets / Factory.run_time + " packets/second");
    }
}
