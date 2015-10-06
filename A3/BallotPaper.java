import java.util.Iterator;

/**
 * A mechanism for a citizen to vote in an election. Allows a citizen
 * to select a number of candidates for election in a proportional
 * representation system of government.
 *
 * @author Colm Cahalane (ID: 113326986)
 */
public class BallotPaper implements Iterable<Candidate>{
    private List<Candidate> preferences;

    // Difference between a candidate list (beginning at 1)
    // and Java's representation of it (beginning at 0)
    private static final int DIFFERENCE = 1;

    /**
     * Constructor for a ballot paper.
     * @return A blank ballot paper.
     */
    public BallotPaper(){
        this.preferences = new ArrayBasedList<Candidate>();
    }

    /**
     * Adds, or changes, a preference set in the ballot.
     * @param  n    Position to change.
     * @param  c    Candidate to add at that position.
     */
    public void setPreference(int n, Candidate c){
        if( n == preferences.size()+DIFFERENCE ){
            preferences.add(c);
        } else {
            preferences.set(n-DIFFERENCE,c);
        }
    }

    /**
     * Returns a candidate at a specific position.
     * @param   n Position to return in the list.
     * @return  A candidate with the given preference
     *          number, or null.
     */
    public Candidate getPreference(int n){
        if( n < preferences.size()+DIFFERENCE ){
            return preferences.get(n-DIFFERENCE);
        } else {
            return null;
        }
    }

    /**
     * Returns the number of candidates in the ballot.
     * @return integer number of candidates in ballot.
     */
    public int size(){
        return preferences.size();
    }

    /**
     * Used for looping through a ballot paper...
     * @return  An iterator for the list of candidates
     *          in the ballot paper.
     */
    public Iterator<Candidate> iterator(){
        return preferences.iterator();
    }
}