/**
*/
public class Candidate
{

  public Candidate()
  {
  }
  
  public Candidate(String n, String p)
  {  name = n;
     party = p;
  }

  public String getName()
  {  return name;
  }

  public String getParty()
  {  return party;
  }
  
  public void setName(String n)
  {  name = n;
  }

  public void setParty(String p)
  {  party = p;
  }


  private String name;
  private String party;
}