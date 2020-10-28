package HW3;


/**
* This <code>PythonTracer</code> class contains a main method
* to trace the python files and determine the Big-Oh complexity.
*
* @author Minqi Shi
* email: minqi.shi@stonybrook.edu
* Stony Brook ID: 111548035
**/
import java.util.*;
import java.io.*;

public class PythonTracer
{
    // The unit indentation of each statement.
    public static final int SPACE_COUNT = 4;

    /**
    * Prompts the user for the name of a file containing a single Python 
    * function, determines its order of complexity, and prints the result
    * to the console.
    **/
    public static void main(String[] args) 
    {
        Scanner stdin = new Scanner(System.in);
        String command = "";
        while (!command.equals("quit"))
        {
            System.out.print("Please enter a file name "
              + "(or 'quit' to quit): ");
            command = stdin.nextLine();
            if (command.equals("quit"))
                break;
            System.out.println();
            Complexity fuctionCom = traceFile(command);
            if(fuctionCom != null)
                System.out.println("Overall complexity of test_function: "
            	  +fuctionCom);
            System.out.println();
        }
        System.out.println("Program terminating successfully...");
    }

    /**
    * Opens the indicated file and traces through the code of the Python
    * function contained within the file, returning the Big-Oh order of 
    * complexity of the function. During operation, the stack trace should
    * be printed to the console as code blocks are pushed to/popped 
    * from the stack.
    *
    * <dt>Preconditions:
    *    <dd> The filename is not null and the file it names contains a 
    *    single Python function with valid syntax.
    *
    * @param filename
    *    The name of the python file.
    *
    * @return
    *    A Complexity object representing the total order of complexity of 
    *    the Python code contained within the file.
    **/
    public static Complexity traceFile(String filename)
    {
        Stack<CodeBlock> stack = new Stack<>();
        try
        {
            File file = new File(filename);
            Scanner stdin = new Scanner(file);
            // Count the occurance of the block.
            int[] count = new int[100];
            while (stdin.hasNext())
            {
                String line = stdin.nextLine();
                if (line!=null && line.length()!=0 && !line.contains("#"))
                {
                    int indents = 0;
                    while (indents<line.length()&&line.charAt(indents) == ' ')
                    {
                            indents++;
                    }
                    indents = indents / SPACE_COUNT;
                    while (indents < stack.size())
                    {
                        if (indents == 0)
                        {
                            stdin.close();
                            // Get the total complexity before return.
                            stack.peek().getBlockComplexity().setLogPower(
                              stack.peek().getBlockComplexity().getLogPower()
                              + stack.peek().getHighestSubComplexity()
                              .getLogPower());
                            stack.peek().getBlockComplexity().setNPower(
                              stack.peek().getBlockComplexity().getNPower()
                              + stack.peek().getHighestSubComplexity()
                              .getNPower());
                            System.out.println("    Leaving block 1.");
                            System.out.println();
                            return stack.peek().getBlockComplexity();
                        }
                        else
                        {
                            CodeBlock oldTop = stack.pop();
                            Complexity oldTopComplexity = 
                              oldTop.getBlockComplexity();
                            oldTopComplexity.setNPower(
                              oldTopComplexity.getNPower()+
                              oldTop.getHighestSubComplexity().getNPower());
                            oldTopComplexity.setLogPower(
                              oldTopComplexity.getLogPower()+
                              oldTop.getHighestSubComplexity().getLogPower());
                            System.out.print("    Leaving block "
                              + oldTop.getName() + ", ");
                            if (oldTopComplexity.getNPower()>stack.peek()
                              .getHighestSubComplexity().getNPower()
                              |(stack.peek().getHighestSubComplexity()
                              .getNPower() == oldTopComplexity.getNPower()
                              && oldTopComplexity.getLogPower()
                              > stack.peek().getHighestSubComplexity()
                              .getLogPower()))
                            {
                                stack.peek()
                                .setHighestSubComplexity(oldTopComplexity);
                                System.out.println("updating block "
                                  + stack.peek().getName()+":");
                            }
                            else
                                System.out.println("nothing to update.");
                            String result = String.format("%13s %-10s "
                              + "block complexity = %-10s highest sub-"
                              + "complexity = %s", "BLOCK" ,
                              stack.peek().getName()+":",
                              stack.peek().getBlockComplexity(),
                              stack.peek().getHighestSubComplexity());
                            System.out.println(result);
                            System.out.println();
                            if (indents < stack.size())
                                count[stack.size()] = 0;
                        }
                    }
                    String keyword = "";
                    for (int i = 0; i < 6 ; i++)
                    {
                        if (line.contains(CodeBlock.BLOCK_TYPES[i]+" "))
                            keyword = CodeBlock.BLOCK_TYPES[i];
                    }
                    if (!keyword.equals(""))
                    {
                        // A temperary CodeBlock to store the new CodeBlock.
                        CodeBlock temp = new CodeBlock();
                        if(keyword.equals("for"))
                        {
                            if(line.contains(" N"))
                            {
                                temp.getBlockComplexity().setNPower(1);
                            }
                            else if(line.contains(" log_N"))
                            {
                                temp.getBlockComplexity().setLogPower(1);
                            }
                        }
                        else if(keyword.equals("while"))
                        {
                            // loopVariable = variable being updated 
                            // (first token after "while").
                            String loopVariable = line.substring(
                              line.indexOf("while")+6, 
                              line.indexOf("while")+7);
                            temp.setLoopVariable(loopVariable);
                        }
                        if (count[0]==0)
                        {
                            temp.setName("1");
                            count[0] = 1;
                        }
                        else
                        {//temp.setName("1."+String.valueOf(1));
                            temp.setName(stack.peek().getName()+"."
                              + String.valueOf(++count[indents]));
                        }
                        stack.push(temp);
                        System.out.println("    Entering block "
                          + temp.getName() + " \'"+keyword+"\':");
                        String result = String.format("%13s %-10s block "
                          +"complexity = %-10s highest sub-complexity = %s",
                          "BLOCK",
                          stack.peek().getName()+":",
                          stack.peek().getBlockComplexity(),
                          stack.peek().getHighestSubComplexity());
                        System.out.println(result);
                        System.out.println();
                    }
                    else if (stack.peek().getLoopVariable()!=null)
                    {   //line updates stack.top's loopVariable 
                        //Update the blockComplexity of stack.top.
                        if (line.contains(stack.peek().getLoopVariable()
                          +" -= 1"))
                        {
                            stack.peek().getBlockComplexity().setNPower(1);
                            System.out.println("    Found update statement, "
                              + "updating block "+stack.peek().getName()+":");
                            String result = String.format("%13s %-10s block "
                              +"complexity = %-10s highest "
                              + "sub-complexity = %s",
                              "BLOCK", stack.peek().getName()+":",
                              stack.peek().getBlockComplexity(),
                              stack.peek().getHighestSubComplexity());
                            System.out.println(result);
                            System.out.println();
                        }
                        else if (line.contains(stack.peek().getLoopVariable()
                          +" /= 2"))
                        {
                            stack.peek().getBlockComplexity().setLogPower(1);
                            System.out.println("    Found update statement, "
                              + "updating block "+stack.peek().getName()+":");
                            String result = String.format("%13s %-10s block "
                              + "complexity = %-10s highest "
                              + "sub-complexity = %s",
                              "BLOCK", stack.peek().getName()+":",
                              stack.peek().getBlockComplexity(),
                              stack.peek().getHighestSubComplexity());
                            System.out.println(result);
                            System.out.println();
                        } 
                    }
                }
            }
            while (stack.size()>1)
            {
                CodeBlock oldTop = stack.pop();
                Complexity oldTopComplexity = oldTop.getBlockComplexity();
                oldTopComplexity.setNPower(
                  oldTopComplexity.getNPower()+
                  oldTop.getHighestSubComplexity().getNPower());
                oldTopComplexity.setLogPower(
                  oldTopComplexity.getLogPower()+
                  oldTop.getHighestSubComplexity().getLogPower());
                if (oldTopComplexity.getNPower()>stack.peek()
                  .getHighestSubComplexity().getNPower()
                  |(stack.peek().getHighestSubComplexity()
                  .getNPower() == oldTopComplexity.getNPower()
                  && oldTopComplexity.getLogPower()
                  > stack.peek().getHighestSubComplexity()
                  .getLogPower()))
                    stack.peek().setHighestSubComplexity(oldTopComplexity);
            }
            stack.peek().getBlockComplexity().setLogPower(
              stack.peek().getBlockComplexity().getLogPower()
              + stack.peek().getHighestSubComplexity().getLogPower());
            stack.peek().getBlockComplexity().setNPower(
              stack.peek().getBlockComplexity().getNPower()
              + stack.peek().getHighestSubComplexity().getNPower());
            System.out.println("    Leaving block 1.");
            System.out.println();
            return stack.pop().getBlockComplexity();
        }
        catch (FileNotFoundException fnf)
        {
            System.out.println("The file is not found!");
        }
        catch (NullPointerException npe)
        {
            System.out.println("Null pointer exception throwed!");
        }
        catch (StringIndexOutOfBoundsException sofi)
        {
            System.out.println("String out of Bounds exception throwed!");
        }
        return null;
    }
}