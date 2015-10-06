/**
 *   Execution engine for three-address code programs. Minimal
 *   error detection.
 *
 *   Syntax:
 *      x := y op z;
 *      x := y;
 *      read x;
 *      write x;
 *      if ( x == 0 ) goto l;  
 *      if (x != 0 ) goto l;
 *      goto l;
 *      l:
 *      halt;
 *      # This is a comment
 *
 *   Notes:
 *   1) Symbol := denotes assignment
 *   2) Instructions terminated by semicolons
 *   3) Identifiers: alphanumeric strings, beginning with letter; case sensitive
 *   4) Jump labels: as above; must be unique; no terminating semicolon
 *   5) Comments: begin with #, extend to end of line
 *
 *	 Intial Stub
 *   @author Kieran Herley, Nov 2011
 *
 *	 Completed by Colm Cahalane, writing completions for the constructor,
 *	 execution engine and parseIntOrGet mechanism
 */

import java.util.*;

public class TacExecutionEngine
{

   /**
    *  Create this (initially empty) program.
    */
   public TacExecutionEngine(TacProgram2 prog)
   {  program = prog;
      variables = new ArrayBasedMap<>();
      labels = new ArrayBasedMap<>();
      /* Initialize other data structures here */
      input = new Scanner(System.in);
   }


   /**
    *  Execute this program.
    *  Makes an inital pass to process label locations,
    *  and a secondary pass to execute the code.
    */
   public void execute()
   {
      // Put the line numbers of each label in a map of labels to
      // line numbers...
      for(int i = 0; i < program.size(); i++ ){
         TacInstruction2 currentInstruction = program.get(i);
         if( currentInstruction.getKind().equals(
               TacInstruction2.InstructionKind.LABEL) ){
            labels.put( currentInstruction.getLabel(), i );
         }
      }

      // Executing the program line by line, using a program counter
      // that can be modified from within the loop by jump instructions.
      for(int pc = 0; pc < program.size(); pc++ ){
         TacInstruction2 currentInstruction = program.get(pc);
         TacInstruction2.InstructionKind kind = currentInstruction.getKind();
         if( kind.equals( TacInstruction2.InstructionKind.THREEADDR ) ){
            // THREEADDR :: x = y + z; | Assignment With Operation
            String x = currentInstruction.getAddr1();
            int y = parseIntOrGet( currentInstruction.getAddr2() );
            int z = parseIntOrGet( currentInstruction.getAddr3() );

            int result;
            switch( currentInstruction.getOp() ){
              // ARITHMETIC OPERATORS
              case "+":
                result = y + z;
                break;
              case "-":
                result = y - z;
                break;
              case "/":
                result = y / z;
                break;
              case "*":
                result = y * z;
                break;

              // BOOLEAN OPERATORS
              // Assigning value 1 for true and 0 for false
              case "==":
                result = (y==z) ? 1 : 0;
                break;
              case ">":
                result = (y>z) ? 1 : 0;
                break;
              case "<":
                result = (y<z) ? 1 : 0;
                break;
              case "<=":
                result = (y<=z) ? 1 : 0;
                break;
              case ">=":
                result = (y>=z) ? 1 : 0;
                break;

              // DOUBLE ASSIGNMENT
              // This is pretty different so will be handled differently.
              case "=":
                result = z;
                variables.put( currentInstruction.getAddr2(), result );
                break;
              default:
                System.err.println("Symbol not recognised");
                result = 0;
                System.exit(1);
            }

            variables.put(x, result);
         } else if( kind.equals( TacInstruction2.InstructionKind.TWOADDR )){
            // TWOADDR :: x := y; | Assignment 
            String x = currentInstruction.getAddr1();
            int y = parseIntOrGet( currentInstruction.getAddr2() );
            variables.put(x, y);
         } else if( kind.equals( TacInstruction2.InstructionKind.GOTO)){
            // GOTO :: goto label; | Program jump
            pc = labels.get( currentInstruction.getLabel() );
         } else if( kind.equals( TacInstruction2.InstructionKind.JUMPTRUE)){
            // JUMPTRUE :: if(x!=0) goto label; | conditional jump
            if( parseIntOrGet( currentInstruction.getAddr1() ) != 0 ){
              pc = labels.get( currentInstruction.getLabel() );
            }
         } else if( kind.equals( TacInstruction2.InstructionKind.JUMPFALSE)){
            // JUMPFALSE :: if(x==0) goto label; | conditional jump
            if( parseIntOrGet( currentInstruction.getAddr1() ) == 0 ){
              pc = labels.get( currentInstruction.getLabel() );
            }
         } else if( kind.equals( TacInstruction2.InstructionKind.READ)) {
            // READ :: read x; | Asks user for input
            System.out.print( currentInstruction.getAddr1() + " = " );
            if( input.hasNextInt() ){
              int variableRead = input.nextInt();
                variables.put( currentInstruction.getAddr1(),
                               variableRead );
              }
            input.nextLine();
         } else if( kind.equals( TacInstruction2.InstructionKind.WRITE)){
            // WRITE :: write x; | Prints a variable to screen
            System.out.println( parseIntOrGet( 
                           currentInstruction.getAddr1() ) );
         } else if( kind.equals( TacInstruction2.InstructionKind.HALT)){
            // HALT :: halt; | Quits program
            System.exit(0);
         }
      }

      // If program has reached here, it's exited without reaching
      // a HALT instruction.
      System.err.println("No HALT instruction reached at EOF.");
      System.exit(1);

   }

   /**
    * Method that parses the "address" format in TAC returning an integer
    * from either a parsed integer string, or the integer that is
    * represented by that string in the variables map.
    *
    * If this method can not parse an integer or find a variable,
    * it will quit the program with an error message.
    * 
    * @param  s String to parse\lookup.
    * @return   An integer value.
    */
   public int parseIntOrGet(String s){
      Integer returnVal;
      try {
         returnVal = Integer.parseInt(s);
      } catch (NumberFormatException e) {
         returnVal = variables.get(s);
         if(returnVal == null){
            System.err.println("Variable " + s + " not initialised properly.");
            System.exit(1);
         }
      }

      return returnVal;
   }


   /* The instructions comprising this program */
   private TacProgram2 program;

   /* Insert declarations of other data stucrures here */
   /* Map structures to store variable/label names */
   private ArrayBasedMap<String, Integer> variables;
   private ArrayBasedMap<String, Integer> labels;

   /* Input source */
   private Scanner input;

   public static void main(String args[])
   {  System.out.println("TacExecutionEngine v1.0: KTH 11/11/'11");
      System.out.println("Beta version");
      if (args.length < 1 )
      {  System.err.println("Usage: java TacExecutionEngine filename");
         System.exit(1);
      } 
      String sourceFile = args[0];

      /* Scan source file and creat TAC program */
      TacScanner2 scanner = new TacScanner2(sourceFile);
      TacProgram2 prog = scanner.scan();
      System.out.println("*** Scanning complete; execution commencing ***");

      /* Create execution environment and execute program */
      TacExecutionEngine engine = new TacExecutionEngine(prog);
      engine.execute();     
      System.out.println("*** Execution complete                      ***");
   }
}