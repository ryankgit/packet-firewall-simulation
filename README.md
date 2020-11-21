# packet-firewall-simulation

### Project Summary:
The purpose of this project is to simulate a redundant firewall to compare performance of different configurations of Producers and Consumers, 
different Packet interarrival and process times, and various queue sizes. This program uses semaphore logic to emulate an actual 
firewall implementation.

### Project Files:
• `Factory.java` prompts user for simulation parameters, generates Producers (Network) and Consumers (Firewall Processors), prints Packet statistics.

• `Producer.java` generates Packets.

• `Consumer.java` processes Packet.

• `BoundedBuffer.java` FIFO queue to simulate the Network issuing Packets to Firewall Processors.

• `Packet.java` tracks Packet-specific information (create time, end time, service time, turnaround time, processing time, etc).

• `SleepUtilities.java` sleeps Producer and Consumer to simulate set packet interarrival and service times.

### Example Statistical Output:

    Percent of discarded packets: 0.0% (0 out of 74 packets)

    Average Service Time: 611.7837837837837ms
    Max Service Time: 638.0ms
    Average Turn Around Time: 603.6756756756756ms
    Max Turn Around Time: 638.0ms
    Average Wait Time: 0.0ms
    Max Wait Time: 0.0ms

    Processor Utilization: 75.45333333333333% (Total Service Time: 45272.0ms, Program Run Time: 60000.0ms)
    Processor Throughput: 1.2333333333333334 packets/second
    
_(Example output is the result of running the simulation for 60 seconds with a packet interarrival time of 800 milliseconds and packet service time of 600 milliseconds)_

### Future Work:

• Add configurable queue size in `BoundedBuffer.java`

• Implement a random exponential distribution for Packet interarrival and process times
