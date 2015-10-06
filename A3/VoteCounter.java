import java.util.Random;
/**
 * A fair, balanced and safe solution for the American people. Not
 * rigged by the Republicans. Or Sinn Féin.
 *
 * @author Colm Ó Cathaláin (ID: 113326986)
 */
public class VoteCounter{
    /**
     * This is a static method that determines the result of a General
     * Election if it is provided with a suitable List-based collection
     * of BallotPaper objets
     * @param  List<BallotPaper> A List-based collection of ballot papers.
     * @return                   The right and fair victor. Honest.
     */
    public static Candidate determineResult(List<BallotPaper> ballots){
        // This list will hold all the candidates that are in the race at the
        // current state. Losers are removed from this list. It will be used
        // to index into
        List<Candidate> candidates = new ArrayBasedList<Candidate>();

        // This map will hold the current state-of-play as to which candidates
        // currently hold which ballot papers.
        Map<Candidate, ArrayBasedList<BallotPaper>> currentState
             = new ArrayBasedMap<Candidate, ArrayBasedList<BallotPaper>>();

        // We'll increment this value with every vote we count.        
        int totalValidPoll = 0;

        // Indicates which count we're working on
        // starting on the "zeroth" count.
        int countNumber = 0;

        // For each ballot paper...
        for( BallotPaper ballot : ballots ){
            // Add it as being valid.
            totalValidPoll++;
            // Loop over the candidates
            // make sure we have each of them listed.
            for( Candidate candidate : ballot ){
                // Initially we won't add anything, unless there's a new
                // candidate that we need to add.
                Boolean skipAdding = false;
                for( Candidate candidate2 : candidates){
                    if(candidate2.equals(candidate)){
                        skipAdding = true;
                        break;
                    }
                }
                if(!skipAdding){
                    candidates.add(candidate);
                    currentState.put(candidate, new ArrayBasedList<BallotPaper>());
                }
            }
        
            // Get this candidate's first preference,
            // and add it to their pile of votes.
            Candidate pref = ballot.getPreference(1);
            if(pref!=null && currentState.get(pref) != null){
                currentState.get(pref).add(ballot);
            }
        }

        // Okay. Deterine the number of votes we have and an initial
        // quota for them.
        System.out.println("Intital number of votes: " + totalValidPoll);
        int quota = totalValidPoll/2;
        System.out.println("Quota has been set at " + quota + " votes.");
        int totalNumCandidates = candidates.size();

        // Initialise winner as a null value, and keep looping until he isn't.
        Candidate winner = null;
        while(winner == null){
            // Increase the number of the count.
            countNumber++;

            // If votes become usless, they're removed from the pile,
            // so we must recalculate the quota.
            quota = totalValidPoll/2;

            // Decoration.
            System.out.println();
            System.out.println();

            // If a candidate has achieved quota, they win.

            // These values are used for minimum comparison that we're
            // going to use to deterine the loser in a round.
            int lowestVotes = Integer.MAX_VALUE;
            int loserIndex = 0;

            // Loop over each of the candidates. We're doing this with
            // an index rather than an iterator loop because we'll need
            // to remove the loser from the state of play.
            for(int index = 0; index < candidates.size(); index++){
                Candidate currentCandidate = candidates.get(index);

                // Determine the number of votes by the size
                // of their "Vote Pile"
                int num_votes = currentState.get(currentCandidate).size();

                // Print the candidate and their number of votes - 
                // mostly for debugging purposes.
                System.out.println(currentCandidate + " " + num_votes);

                // If the candidate attains quota...
                if( num_votes >= quota ){
                    if(winner == null){
                        // If there isn't already a winner, make this the winner.
                        winner = currentCandidate;
                    } else {
                        // If there is already a winner (tie case),
                        // decide by coin toss.
                        System.out.println("A tie! Deciding by coin toss.");
                        Random random = new Random();
                        winner = random.nextBoolean() ? winner : currentCandidate;
                    }
                } 
                // If this candidate has below the minimum...
                if( num_votes < lowestVotes ){
                    // Mark the new minimum
                    lowestVotes = num_votes;
                    // Mark the index of loser to delete.
                    loserIndex = index;
                }
            }

            // If a winnner has not been decided on this count...
            if( winner == null ){
                // Remove a loser from the state of play.
                Candidate loser = candidates.remove(loserIndex);
                // Remove their vote pile.
                ArrayBasedList<BallotPaper> losersVotes = currentState.remove(loser);

                // While the loser has votes...
                while(losersVotes.size() != 0){
                    // Remove his most recent vote.
                    BallotPaper ballot = losersVotes.remove(0);
                    // Get his next preference and their "pile" if
                    // they're still in the game.
                    Candidate pref = null;
                    ArrayBasedList<BallotPaper> transferTo = null;

                    // Loop through the ballot papers until 
                    // We start at 2, because first preference will always
                    int increment = 2;
                    while( transferTo==null && increment<losersVotes.size() ){
                        pref = ballot.getPreference(increment);
                        transferTo = currentState.get(pref);
                        increment++;
                    }

                    if(transferTo != null){
                        // If the vote can be transferred transfer it.
                        transferTo.add(ballot);
                    }
                    else{
                        // Otherwise, the vote is useless and is discarded.
                        totalValidPoll--;
                    }
                }
                // Indicate who has been discarded.
                System.out.println(loser.getName() + " has been eliminated on count " + countNumber);
            }
        }
        // Decoration
        System.out.println();
        System.out.println();

        // Return our decided winner.
        return winner;
    }
}