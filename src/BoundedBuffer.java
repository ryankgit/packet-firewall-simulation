import java.util.concurrent.Semaphore;

/**
 * BoundedBuffer.java
 *
 * This program implements the bounded buffer with semaphores.
 * Note that the use of count only serves to output whether
 * the buffer is empty of full.
 */
public class BoundedBuffer implements Buffer {

    private static final int BUFFER_SIZE = 5; // "The maximum queue size is 5 packets"
    private Semaphore mutex;
    private Semaphore empty;
    private Semaphore full;
    private int count;
    private int in, out;
    private Object[] buffer;
    // for packet statistics
    public static int pTotal;
    public static int pDropTotal;

    public BoundedBuffer() {
        // buffer is initially empty
        count = 0;
        in = 0;
        out = 0;

        buffer = new Object[BUFFER_SIZE];

        mutex = new Semaphore(1);
        empty = new Semaphore(BUFFER_SIZE);
        full = new Semaphore(0);
    }

    // producer calls this method
    public boolean insert(Object item) {
        // if buffer is full, drop packet
        if (count == BUFFER_SIZE) {
            // drop packet
            //System.out.println("Packet discarded: Buffer full");
            pDropTotal++;
            return false;
        }

        // insert packet
        try {
            empty.acquire();
            mutex.acquire();
        } catch (Exception e) {
            System.out.println("Error inserting packet:" + e);
        }
        // add an item to the buffer
        ++count;
        buffer[in] = item;
        in = (in + 1) % BUFFER_SIZE;
        //System.out.println("Producer created " + item + " Buffer Size = " + count);

        mutex.release();
        full.release();

        pTotal++;
        return true;
    }

    // consumer calls this method
    public Object remove() {
        try {
            full.acquire();
            mutex.acquire();
        } catch (Exception e) {
            System.out.println("Error removing packet:" + e);
        }

        // remove an item from the buffer
        --count;
        Object item = buffer[out];
        out = (out + 1) % BUFFER_SIZE;

//        if (count == 0) {
//            System.out.println("Consumer processed " + item + " Buffer is empty");
//        } else {
//            System.out.println("Consumer processed " + item + " Buffer contains " + count + " packet");
//        }

        mutex.release();
        empty.release();

        return item;
    }
}
