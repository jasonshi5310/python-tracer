package HW3;


/**
* This <code>Complexity</code> represents the Big-Oh complexity of 
* some block of code.
*
* @author Minqi Shi
* email: minqi.shi@stonybrook.edu
* Stony Brook ID: 111548035
**/
public class Complexity
{
    private int nPower; // The power of the n type.
    private int logPower; // The power of log_n type.

    /**
    * Returns an instance of <code>Complexity</code>.
    **/
    public Complexity()
    {
        this.nPower = 0;
        this.logPower = 0;
    }

    /**
    * Returns the power of the n type.
    *
    * @return 
    *    The power of the n type.
    **/
    public int getNPower()
    {
        return nPower;
    }

    /**
    * Returns the power of the log_n type.
    * 
    * @return
    *    The power of the log_n type.
    **/
    public int getLogPower()
    {
        return logPower;
    }

    /**
    * Sets a new power of the n type.
    *
    * @param newNPower
    *    The new power of the n type.
    **/
    public void setNPower(int newNPower)
    {
        this.nPower = newNPower;
    }

    /**
    * Sets a new power of the log_n type.
    *
    * @param newLogPower
    *    The new power of the log_n type.
    **/
    public void setLogPower(int newLogPower)
    {
        this.logPower = newLogPower;
    }

    /**
    * Returns a neatly formatted String representation of the complexity.
    *
    * @return
    *    A neatly formatted string containing the comlexity.
    **/
    @Override
    public String toString()
    {
        // A temperary string to store the information.
        String temp = "";
        if (this.logPower == 0 && this.nPower == 0)
            temp = "1";
        else if (this.logPower == 0)
        {
            if (this.nPower == 1)
                   temp = "n";
            else
                temp = "n^" + String.valueOf(nPower);
        }
        else if (this.nPower == 0)
        {
            if (this.logPower == 1)
                temp = "log(n)";
            else
                temp = "log(n)^" + String.valueOf(logPower);
        }
        else
        {
            if (this.nPower == 1 && this.logPower != 1)
                temp = "n" + " * log(n)^" + String.valueOf(logPower);
            else if(this.logPower == 1 && this.nPower != 1)
                temp = "n^" + String.valueOf(nPower)+" * log(n)";
            else if(this.logPower == 1 && this.nPower == 1)
                temp = "n * log(n)";
            else
                temp = "n^" + String.valueOf(nPower)+
                  " * log(n)^" + String.valueOf(logPower);
        }
        return "O(" + temp + ")";
    }
}