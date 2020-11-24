# packet-firewall-simulation

The purpose of this project is to simulate a redundant firewall to compare performance of different configurations of Producers and Consumers, 
different Packet interarrival and process times, and various queue sizes. This program uses semaphore logic to emulate an actual 
firewall implementation.

# Project Files

* `Factory.java` prompts user for simulation parameters, generates Producers (Network) and Consumers (Firewall Processors), prints Packet statistics.
* `Producer.java` generates Packets.
* `Consumer.java` processes Packet.
* `BoundedBuffer.java` FIFO queue to simulate the Network issuing Packets to Firewall Processors.
* `Packet.java` tracks Packet-specific information (create time, end time, service time, turnaround time, processing time, etc).
* `SleepUtilities.java` sleeps Producer and Consumer to simulate set packet interarrival and service times.

# Example Statistical Output

~~~
Percent of discarded packets: 6.916621548456956% (511.0 out of 7388.0 packets)

Average Service Time: 5.811254907663225ms
Max Service Time: 60.0ms
Average Turn Around Time: 14.136105860113421ms
Max Turn Around Time: 91.0ms
Average Wait Time: 8.327468372836993ms
Max Wait Time: 77.0ms

Processor Utilization: 66.60666666666667% (Total Service Time: 39964.0ms, Program Run Time: 60000.0ms)
Processor Throughput: 123.13333333333334 packets/second
~~~
    
_(Example output is the result of running the simulation for 60 seconds with an interarrival time of 8 milliseconds, service time of 6 milliseconds, and buffer size of 5 with 1 Producer and 1 Consumer)_

# Future Work

* ~~Add configurable queue size in `BoundedBuffer.java`~~
* ~~Implement a random exponential distribution for Packet interarrival and process times~~
* Allow user to set number of Producers and Consumers from command line
