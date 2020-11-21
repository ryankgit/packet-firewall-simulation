/**
 * An interface for buffers
 *
 */


public interface Buffer
{
    /**
     * insert an item into the Buffer.
     * Note this may be either a blocking
     * or non-blocking operation.
     */
    public abstract boolean insert(Object item);

    /**
     * remove an item from the Buffer.
     * Note this may be either a blocking
     * or non-blocking operation.
     */
    public abstract Object remove();
}