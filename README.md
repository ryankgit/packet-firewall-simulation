# packet-firewall-simulation

The purpose of this project is to simulate a redundant firewall to compare performance of different configurations of Producers and Consumers, 
different Packet interarrival and process times, and various queue sizes. This program uses semaphore logic to emulate an actual 
firewall implementation.

# Project Files

* `Factory.java` prompts user for simulation parameters, generates Producers (Networks) and Consumers (Firewalls), prints Packet statistics.
* `Producer.java` generates Packets.
* `Consumer.java` processes Packet.
* `BoundedBuffer.java` FIFO queue to simulate the Network issuing Packets to Firewall Processors.
* `Packet.java` tracks Packet-specific information (create time, end time, service time, turnaround time, processing time, etc).
* `SleepUtilities.java` sleeps Producer and Consumer to simulate set packet interarrival and service times.

# Example Simulation

### User enters simulation parameters:
~~~
Enter number of Networks (Producers):
1
Enter number of Firewalls (Consumers):
1
Enter packet interarrival time (in milliseconds):
800
Enter packet service time (in milliseconds):
600
Enter FIFO-queue buffer size:
5
Enter simulation run time (in seconds):
200
~~~
    
### Program output:
~~~
Percent of discarded packets: 7.264957264957266% (17.0 out of 234.0 packets)

Average Service Time: 653.2718894009216ms
Max Service Time: 3047.0ms
Average Turn Around Time: 1791.3456221198157ms
Max Turn Around Time: 6579.0ms
Average Wait Time: 1146.36866359447ms
Max Wait Time: 5369.0ms

Processor Utilization: 70.88% (Total Service Time: 141760.0ms, Program Run Time: 200000.0ms)
Processor Throughput: 1.17 packets/second
~~~

# Future Work

* ~~Add configurable queue size in `BoundedBuffer.java`~~
* ~~Implement a random exponential distribution for Packet interarrival and process times~~
* ~~Allow user to set number of Producers and Consumers from command line~~
