/**
 * This is the producer thread for the bounded buffer problem.
 */

public class Producer implements Runnable {
    // Producer instance variables
    private Buffer buffer;
    private int serviceTime;
    private int interarrivalTime;

    // Producer constructor, called in Factory.java
    public Producer(Buffer b, int interarrivalTime, int serviceTime) {
        buffer = b;
        this.interarrivalTime = interarrivalTime;
        this.serviceTime = serviceTime;
    }

    public void run() {
        while (true) {
            // "purpose of the Network-Producer is to generate (or produce) Packets every interarrivalTime"
            SleepUtilities.nap(interarrivalTime);
            Packet p = new Packet(serviceTime);

            // insert packet into buffer, add packet to pList or pDropList depending on if packet is processed
            if (buffer.insert(p)) {
                // packet added to buffer
                Packet.pList.add(p);
                // set cqd: packet has be queued
                p.cqd = "queued";
            }
            else {
                // add packet to drop list (buffer full)
                Packet.pDropList.add(p);
                // set cqd: packet has been dropped
                p.cqd = "dropped";
            }
        }
    }
}