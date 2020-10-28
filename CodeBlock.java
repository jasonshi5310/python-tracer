package HW3;


/**
* This <code>CodeBlock</code> class describes a nested block of code.
*
* @author Minqi Shi
* email: minqi.shi@stonybrook.edu
* Stony Brook ID: 111548035
**/

public class CodeBlock
{
	// A static final array of six String variables to enumerate the 
	// types of blocks available for nesting.
    public static final String[] BLOCK_TYPES = {"def", "for", "while",
      "if", "elif", "else"};
    // Six static final int variables corresponding to the 
    // indices of the BLOCK_TYPE array.
    public static final int DEF = 0, FOR = 1, WHILE = 2, 
      IF = 3, ELIF = 4, ELSE = 5;
    // The Big-Oh complexity of this block.
    private Complexity blockComplexity;
    // The Big-Oh complexity of the highest-order 
    // block nested within this block.
    private Complexity highestSubComplexity;
    private String name; // The nested structure of the blocks.
    private String loopVariable; // For while loops.

    /**
    * Returns a instance of <code>CodeBlock</code>.
    **/
    public CodeBlock()
    {
        this.blockComplexity = new Complexity();
        this.highestSubComplexity = new Complexity();
    }

    /**
    * Returns the Big-Oh complexity of this block.
    *
    * @return
    *    The Big-Oh complexity of this block.
    **/
    public Complexity getBlockComplexity()
    {
    	return blockComplexity;
    }

    /**
    * Returns Big-Oh complexity of the highest-order 
    * block nested within this block.
    *
    * @return
    *    The Big-Oh complexity of the highest-order 
    *    block nested within this block.
    **/
    public Complexity getHighestSubComplexity()
    {
    	return highestSubComplexity;
    }

    /**
    * Returns the nested structure of the blocks.
    *
    * @return
    *    The nested structure of the blocks.
    **/
    public String getName()
    {
    	return name;
    }

    /**
    * Returns the loopVariable.
    *
    * @return
    *    The loopVariable of this block.
    **/
    public String getLoopVariable()
    {
    	return loopVariable;
    }

    /**
    * Set a new Big-Oh complexity of this block.
    *
    * @param newBlockComplexity
    *    The new Big-Oh complexity of this block.
    **/
    public void setBlockComplexity(Complexity newBlockComplexity)
    {
    	this.blockComplexity = newBlockComplexity;
    }

    /**
    * Set a new Big-Oh complexity of the highest-order 
    * block nested within this block.
    *
    * @param newHighestSubComplexity
    *    The new The Big-Oh complexity of the highest-order 
    *    block nested within this block.
    **/
    public void setHighestSubComplexity(Complexity newHighestSubComplexity)
    {
    	this.highestSubComplexity = newHighestSubComplexity;
    }

    /**
    * Set a new nested structure of the blocks.
    *
    * @param newName
    *    The nested structure of the blocks.
    **/
    public void setName(String newName)
    {
    	this.name = newName;
    }

    /**
    * Set a new loopVariable.
    *
    * @param newLoopVariable
    *    The new loopVariable of this block.
    **/
    public void setLoopVariable(String newLoopVariable)
    {
    	this.loopVariable = newLoopVariable;
    }
}