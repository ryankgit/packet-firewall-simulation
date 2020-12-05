# packet-firewall-simulation
The purpose of this project is to simulate a redundant Firewall. The program consists of a simulated Network that sends Packet objects to the Firewall at various rates. The number and configuration of Networks, Firewalls, Packet interarrival times, Packet service times, and FIFO-queue size can be set by the User to compare how different setting effect network performance. The program uses semaphore logic to emulate an actual firewall implementation and is modeled after a typical java Producer-Consumer program.

_Note: This simulation would best be performed using a simulation language. Java's sleep() function is sometimes inaccurate, so this simulation will be close but not precise. A simulation language implementation would give much more accurate results. However the purpose of this is to learn about Semaphores, Producer/Consumer problems, and CPU scheduling, and this homework accomplishes that._

# Project Files

* `Factory.java` is what the User runs to being the simulation. The program prompts the User to enter simulation parameters (number of Networks, number of Firewalls, Packet interarrival time, Packet service time, FIFO-queue buffer size, simulation run time), creates the specified number of Networks and Firewalls, tracks program run time, stops the Networks and Firewalls, and calls a method in Packet.java to print simulation statistics.

* `Packet.java` keeps track of all the information related to a Packet object. This includes each Packet create, service, end, turnaround, and wait time. Additionally, `Packet.java` keeps track of all processed and dropped Packets in ArrayLists and calculates the percent of discarded Packets, average service, max service, average turnaround, max turnaround, average wait, max wait times, the expected and actual processor utilization, and the processor throughput at the end of each simulation.

* `Producer.java` simulates a Network. The Producer class creates new Packets and inserts them into `BoundedBuffer.java`’s buffer. Based on whether the insert is successful or not, the Producer class adds the Packet to a list of processed or dropped packets.

* `Consumer.java` simulates a Firewall. The Consumer class removes Packets from `BoundedBuffer.java`’s buffer and calls a method in `Packet.java` to set the Packet’s end time. 

* `BoundedBuffer.java` has a Packet insert (called by the Network) and a Packet remove (called by the Firewall) method. `BoundedBuffer.java` is a FIFO-queue, set to the size specified by the User in `Factory.java`. The Packet inset method will first acquire the mutex Semaphore before checking if the buffer is full. If the buffer is full, the mutex Semaphore will be released and the method will return false (Packet dropped) back to Producer.java. If the buffer is not full, the empty and mutex Semaphores will be acquired, the number of Packets in the buffer will be incremented, the Packet will be inserted into the buffer, the mutex and fill Semaphores will be released, and the method will return true (Packet inserted) to `Producer.java`. The Packet remove method will acquire the full and mutex Semaphores, decrement count of items in the buffer, remove the Packet from the buffer, release the mutex and empty Semaphores, and return the Packet to the Consumer.

* `SleepUtilities.java` is called by `Producer.java` and `Consumer.java`. The Network calls `SleepUtilities.java`’s nap method before creating new Packets. The Network’s nap time is based on the Packet’s predefined interarrival time. The Firewall calls `SleepUtilities.java`’s nap method after removing a Packet from the buffer. The Firewall’s nap time is based on the Packet’s predefined service time. Depending on whether the User want to run the simulation using Static or Random sleep times, sections of `SleepUtilities.java`’s nap method need to be commented out prior to running the program. Running the program in Static mode sleeps the Thread for the predefined Packet interarrival or service time (depending on where the nap method is called from), while running the program in Random mode sleeps the Thread for a randomly generated amount of time based on an exponential distribution. This exponential distribution is calculated by `avgTime * -Math.log(Math.random())`, where `avgTime` is the predefined Packet interarrival or service time (depending on where the nap method is called from).

* `Buffer.java` is an interface for the FIFO-queue buffer used in `BoundedBuffer.java`.

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
* Allow user to switch between Static and Random mode from command line
