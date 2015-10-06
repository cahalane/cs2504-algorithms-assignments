/**
 * Interface for a Deque outlining the methods that all Deque (double-
 * ended queue) objects must have.
 *
 * @author  Colm Cahalane (ID: 113326986)
 */
public interface Deque<T>{
    /**
     * Returns the object which is present at the front of the deque.
     * @return T    object at the front position of the queue.
     */
    public T first();

    /**
     * Returns the object which is present at the rear of the deque.
     * @return T    object at the rear position of the queue.
     */
    public T last();

    /**
     * Removes and returns the object which is present at the front of
     * the deque.
     * @return  T    object at the front position of the queue.
     */
    public T removeFirst();

    /**
     * Removes and returns the object which is present at the rear of
     * the deque.
     * @return  T    object at the rear position of the queue.
     */
    public T removeLast();

    /**
     * Adds a new item to the front of the deque.
     * @param   T   element       T element to be placed at 
     *                            the front of the deque.
     */
    public void insertFirst(T element);

    /**
     * Adds a new item to the rear of the deque.
     * @param   T   element       T element to be placed at 
     *                            the rear of the deque.
     */
    public void insertLast(T element);

    /**
     * A method to get the current size of the deque.
     * @return  int             The number of elements in the array.
     */
    public int size();

    /**
     * A method to discern if the deque is empty.
     * @return  boolean         true iff the deque is empty.
     */
    public boolean isEmpty();
}