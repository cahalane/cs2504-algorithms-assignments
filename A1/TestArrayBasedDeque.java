/**
 * Class that contains a basic testing program for an ArrayBasedDeque
 * that adds and removes integers in pairs. The nuimber of elements
 * added is higher than the initial capacity hence we will also test
 * the ability for the array to extend.
 *
 * @author Colm Cahalane (ID: 113326986)
 */
public class TestArrayBasedDeque {
    /**
     * A testing program that faces the user.
     * @param args Command line arguments (unused)
     */
    public static void main(String[] args){
        // Casting this as the Deque interface allows us to check that
        // it is properly implemented
        Deque<Integer> deque = new ArrayBasedDeque<Integer>();

        // Allows us to make alternating First/Last insertions/removals
		int decider = 0;

        // Populate array with integers from 1 to 100 in alternating
        // last/first positions.
        for(int i=1; i<=100; i++){
            if( (decider%2) == 0 ){
                deque.insertLast(i);
            } else {
                deque.insertFirst(i);
            }
            decider++;
        }
		
        // Test the size() instance attribute. Should be 100.
        System.out.println("Built an ArrayBasedDeque<Integer> \n" +
                            "DEQUE SIZE : " + deque.size() +
                            "\nTesting some outputs..." );
        System.out.println();

        // Until the array is empty, return its content in alternating
        // last/first order, printing to screen.
		while( !deque.isEmpty() ){
            if( (decider%2) == 0 ){
                System.out.println( deque.removeLast() );
            } else {
                System.out.println( deque.removeFirst());
            }
            decider++;
		}

        System.out.println();
        System.out.println("Starting to test an ArrayBasedDeque<String>");
        System.out.println();

        Deque<String> deque2 = new ArrayBasedDeque<String>();
        deque2.insertLast("the");
        deque2.insertLast("quick");
        deque2.insertLast("brown");
        deque2.insertLast("fox");
        deque2.insertLast("jumped");
        deque2.insertLast("over");
        deque2.insertLast("the");
        deque2.insertLast("lazy");
        deque2.insertLast("dog");

        while( !deque2.isEmpty() ){
            System.out.println( deque2.removeFirst() );
        }

    }
}