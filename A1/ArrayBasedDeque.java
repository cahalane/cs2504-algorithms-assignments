/**
 * An array-based implementation of the Deque interface as specified
 * in Deque.java
 *
 * @author  Colm Cahalane (ID: 113326986)
 */
public class ArrayBasedDeque<T> implements Deque<T> {
    private static final int INITIAL_CAPACITY = 50;
    private static final String ERROR = "The deque is empty";
    private int capacity;
    private int f; // Index representing "front" of deque
    private int r; // Index representing "rear" of deque
    private T[] array;  // The array on which this implementation is based.

    /**
     * Generic constructor for a Deque that uses the class-specific
     * constant INITIAL_CAPACITY. Calls the private constructor with
     * this value
     * @return An array based Deque of capacity INITIAL_CAPACITY.
     */
    public ArrayBasedDeque(){
        this(INITIAL_CAPACITY);
    }

    /**
     * Private constructor to make new ArrayBasedDeque objects with a
     * variable capacity as set by parameter.
     * The ArrayBasedDeque is formed from two indexes (front and rear)
     * and an array of our type-T objects.
     * 
     * @param int       capacity        Max number of objects that can
     *                                  be stored in the array.
     * @return          An ArrayBasedDeque object.
     */
    private ArrayBasedDeque(int capacity){
        this.capacity = capacity;
        this.array = (T[]) (new Object[capacity]); // !?!? ok

        // We initialise our indexes.
        this.f = 0;
        this.r = 0;
    }

    /**
     * Creates a new ArrayBasedDeque object, moves the elements
     * contained within to the new object using our instance methods
     * and then re-assigns the instance attributes of the new deque
     * to our current one.
     */
    private void extend(){
        ArrayBasedDeque<T> newQueue = new ArrayBasedDeque<>(capacity*2);

		while( !this.isEmpty() ){
            newQueue.insertLast( this.removeFirst() );
		}
		
        this.capacity = newQueue.capacity;
        this.r = newQueue.r;
        this.f = newQueue.f;
        this.array = newQueue.array;
    }

    /**
     * Used to calculate the next position in the circular array
     * based on a current positon provided.
     * @param   int     x       A "current" position in the deque.
     * @return  int             The "next" position in the deque.
     */
    private int next(int x){
        return (x+1)%capacity;
    }

    /**
     * Used to calculate the previous position in the circular array
     * based on a current positon provided.
     * As Java implements negative results for the % operator, this method
     * checks for that and returns only positive values.
     * @param   int     x       A "current" position in the deque.
     * @return  int             The "previous" position in the deque.
     */
    private int previous(int x){
        int value = (x-1)%capacity;
		if(value<0){
			value += capacity;
		}
		return value;
    }

    /**
     * A method to get the current size of the deque.
     * @return  int             The number of elements in the array.
     */
    @Override
    public int size(){
        return ((capacity - f + r) % capacity);
    }

    /**
     * A method to discern if the deque is empty.
     * @return  boolean         true iff the deque is empty.
     */
    @Override
    public boolean isEmpty(){
        return (f == r);
    }

    /**
     * Returns the object which is present at the front of the deque.
     * @return T    object at the front position of the queue.
     */
    @Override
    public T first(){
        if( isEmpty() ){
            System.err.println(ERROR);
            return null;
        } else {
            return array[f];
        }
    }

    /**
     * Removes and returns the object which is present at the front of
     * the deque.
     * @return  T    object at the front position of the queue.
     */
    @Override
    public T removeFirst(){
        T returnVal = this.first();
        if( returnVal != null){
            f = next(f);
        }
        return returnVal;
    }

    /**
     * Returns the object which is present at the rear of the deque.
     * @return T    object at the rear position of the queue.
     */
    @Override
    public T last(){
        if( isEmpty() ){
            System.err.println(ERROR);
            return null;
        } else {
            return array[ previous(r) ];
        }
    }

    /**
     * Removes and returns the object which is present at the rear of
     * the deque.
     * @return  T    object at the rear position of the queue.
     */
    @Override
    public T removeLast(){
        T returnVal = this.last();
        if(returnVal != null){
            r = previous(r);
        }
        return returnVal;
    }

    /**
     * Adds a new item to the front of the deque.
     * @param   T   element       T element to be placed at 
     *                            the front of the deque.
     */
    @Override
    public void insertFirst(T element){  
        if( size() == capacity-1 ){
            extend();
        }
        f = previous(f);
        array[f] = element;
    }

    /**
     * Adds a new item to the rear of the deque.
     * @param   T   element       T element to be placed at 
     *                            the rear of the deque.
     */
    @Override
    public void insertLast(T element){  
        if( size() == capacity-1 ){
            extend();
        }
        array[r] = element;
        r = next(r);
    }
}
