import java.util.concurrent.Semaphore;

/**
 * BoundedBuffer.java
 *
 * This program implements the bounded buffer with semaphores.
 *
 */
public class BoundedBuffer implements Buffer {

    private static final int BUFFER_SIZE = Factory.buffer_size;
    private Semaphore mutex, empty, full;
    private int count, in, out;
    private Object[] buffer;

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
        try {
            mutex.acquire();
            // if buffer is full, drop packet
            if (count == BUFFER_SIZE) {
                // drop packet
                //System.out.println("Packet discarded: Buffer full");
                mutex.release();
                return false;
            }
            else {
                mutex.release();
            }
        } catch (Exception e) {
            System.out.println("Error inserting packet:" + e);
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
