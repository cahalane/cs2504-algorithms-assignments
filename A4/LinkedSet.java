/**
*  Linked list implmentation for ADT Set
*  @author Colm Cahalane (ID: 113326986)
*/
import java.util.Iterator;

public class LinkedSet<EltType>
             implements Set<EltType>
{
   /**
   * Create an empty set.
   */
   public LinkedSet()
   {
      numElts = 0;
      head = new LLNode<EltType>();
      tail = new LLNode<EltType>();
      head.setNext(tail);
      tail.setPrev(head);
      comp = null;
   }

   /**
   *   Create an empty set.
   *   @param comparator to be used for all element comparisons 
   */   
   public LinkedSet(Comparator<EltType> c)
   {
      this();
      comp = c;
   }
   
   /**
   *   Create singleton set containing  the specified element
   *   @param the element 
   */
   public LinkedSet(EltType elt)
   {  
      this();
      add(elt);
   }

   /**
   *   Create singleton set containing  the specified element
   *   @param the element 
   *   @param comparator to be used for all element comparisons 
   */
   public LinkedSet(Comparator<EltType> c, EltType elt)
   {  
      this(c);
      add(elt);
   }


   /**
   * Return the number of elements in this set
   * @return size of set
   */
   public int size()
   {  
      return numElts;
   }
   
   /**
   * Return true if this set is empty, false otherwise.
   * @return emptiness flag
   */
   public boolean isEmpty()
   {  
      return (numElts == 0);
   }
   

   /**
   * Add element newElt to the list. Has no effect if element
   * is already present.
   * @param newElt-- the element to be added
   */
   public void add(EltType newElt)
   {  
      if( !contains(newElt) ){   
         LLNode<EltType> nodeBeforeTail = tail.getPrev();
         LLNode<EltType> toAdd = new LLNode<EltType>(nodeBeforeTail,
                                                         tail,newElt);
         tail.setPrev(toAdd);
         nodeBeforeTail.setNext(toAdd);
         numElts++;
      }
   }

   /**
   *   Return true if this set contains the specified element
   *   i.e. if checkElement is a member of this set.
   *   @param checkElement-the candidate member
   *   @return boolean membership indication
   */   
   public boolean contains(EltType checkElement)
   {
      LLNode<EltType> current = head.getNext();
      while(current != tail){
         if( areEqual(checkElement, current.element()) ){
            return true;
         }
         current = current.getNext();
      }
      return false;
   }

   /**
   *  Remove the specified element from this set if it is 
   *  present. 
   *   @param remElement-- the element to be removed
   */
   public void remove(EltType remElement)
   {  
      LLNode<EltType> current = head.getNext();
      LLNode<EltType> remNode = null;
      while(current != tail && remNode == null){
         if( areEqual(remElement, current.element()) ){
            remNode = current;
         }
         current = current.getNext();
      }
      if(remNode != null){
         LLNode<EltType> nodeBefore = remNode.getPrev();
         LLNode<EltType> nodeAfter = remNode.getNext();
         nodeBefore.setNext(nodeAfter);
         nodeAfter.setPrev(nodeBefore);
         numElts--;
      }
   }

   /**
   *   Add all of the elements in the set addSet to this 
   *   set if they're not already present. The addAll operation 
   *   effectively modifies this set so that its value is the 
   *   union of the two sets.
   *   @param addSet-- the elements to be added
   */
   public void addAll(Set<EltType> addSet)
   {
      for(EltType toAdd : addSet){
         add(toAdd); // No effect if element already in set.
      }
   }
   

   
   /**
   *   Return true if this set contains all of the elements 
   *   of the specified set i.e. returns true if it is a subset 
   *   of this set.
   *   @param checkSet-- the candidate subset
   *   @return boolean subset indication
   */   
   public boolean containsAll(Set<EltType> checkSet)
   {  /* fill this in */
      for(EltType toCheck : checkSet){
         if( !contains(toCheck) ){
            // Doesn't contain one, mustn't contain all.
            return false;
         }
      }
      return true;
   }
   
   
   /**
   *   Remove from this set all of its elements that are 
   *   contained in the specified set. This operation 
   *   effectively modifies this set so that its value is 
   *   the asymmetric set difference of the two sets.
   *   @param remSet-- the elements to be removed
   */
   public void removeAll(Set<EltType> remSet)
   {
      for(EltType toRemove : remSet){
         remove(toRemove); // No effect if element not in set.
      }
   }
   
   /**
   *   Retain only the elements in this set that are 
   *   contained in the specified set. This operation 
   *   effectively modifies this set so that its value 
   *   is the intersection of the two sets.
   *   @param retSet-- the elemnets to be retained
   */
   public void retainAll(Set<EltType> retSet)
   {
      LLNode<EltType> current = head.getNext();
      while(current != tail){
         if( ! retSet.contains( current.element() ) ){
            remove( current.element() );
         }
         current = current.getNext();
      }
   }
   
   /**
   *   Return a list of the elements in this set. 
   *   The elements are returned in no particular order .
   *   @return the elements of this set
   *
   */
   public Iterator<EltType> iterator()
   {  
      ArrayBasedIterator<EltType> iterator = new ArrayBasedIterator<>();

      LLNode<EltType> current = head.getNext();
      while(current != tail){
         iterator.add( current.element() );
         current = current.getNext();
      }

      return iterator;
   }

   
   /**
   *   Return a list of the elements in this set. 
   *   The elements are returned in no particular order .
   *   @return the elements of this set
   *
   */
   public Iterator<EltType> elements()
   {
      return iterator();
   }
   
   /**
    * Discern if two elements are equal using either
    * the default .equals() method or a comparator if
    * available.
    * @param  a First element to be compared
    * @param  b Second element to be compared
    * @return   Boolean: Are elements equal?
    */
   private boolean areEqual(EltType a, EltType b){
      if(comp == null){
         return a.equals(b);
      } else {
         return ( comp.compare(a,b) == 0);
      }
   }

   @Override
   public String toString(){
      String returnVal = "{ ";
      LLNode<EltType> current = head.getNext();
      while(current != tail){
         returnVal += current.element() + " ";
         current = current.getNext();
      }
      returnVal += "}";
      return returnVal;
   }
   
   /* device to compare items of type EltType */
   private Comparator<EltType> comp; 
   
   /* Number of items in the set */
   private int numElts;  
   
   /* Special head sentinel */
   private final LLNode<EltType> head;  
   
   /* Special tail sentinel */
   private final LLNode<EltType> tail; 

}