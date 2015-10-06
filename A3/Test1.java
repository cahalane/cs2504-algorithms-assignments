 import java.util.Random;

 /**
  * A test case application for the vote counter.
  * @author Colm Cahalane (ID: 113326986)
  */
 
public class Test1 {
   /**
    * This is a test case application that generates 100
    * random ballot papers containing the each of five candidates.
    * @param args Command line arguments (unused)
    */
    public static void main(String[] args) {
       ArrayBasedList<Candidate> candidates = new ArrayBasedList<Candidate>();

       // Let's manually create some candidates...
       candidates.add(new Candidate("Adolf Hitler", "Nationalsozialistische Deutsche Arbeiterpartei"));
       candidates.add(new Candidate("Benito Mussolini", "Partito Nazionale Fascista"));
       candidates.add(new Candidate("Mao Zedong", "中国共产党"));
       candidates.add(new Candidate("Joseph Stalin", "Коммунистическая партия Советского Союза"));
       candidates.add(new Candidate("Gearóid Mac Ádhaimh", "Sinn Féin"));

        // Container for papers to send to the determineResult function
        ArrayBasedList<BallotPaper> papers = new ArrayBasedList<BallotPaper>();

        Random rng = new Random();
        for(int i=0; i<100; i++){
            // Copy that container to another one.
            ArrayBasedList<Candidate> candidates2 = new ArrayBasedList<Candidate>();
            for(Candidate candidate: candidates){
                candidates2.add(candidate);
            }

            int numberOfCandidates = candidates.size();
            BallotPaper paper = new BallotPaper();
            for(int j=1; j<=numberOfCandidates; j++){
                // Take out a random candidate and make it one of the preferences.
                paper.setPreference(j, candidates2.remove(rng.nextInt(candidates2.size())));
            }

            papers.add(paper);
        }

        Candidate winner = VoteCounter.determineResult(papers);
        System.out.println("A winner has been decided!");
        System.out.println(winner);
    }
}
