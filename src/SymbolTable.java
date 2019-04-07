import java.util.HashMap;
/**
 * SymbolTable.java: A java class that holds a a HashMap containing symbols, which is added through
 * the Assembler class.
 * @author Gabriel Bactol
 * @version 4.0
 */
public class SymbolTable
{
    private String INITIAL_VALID_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_.$:";;
    private String ALL_VALID_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_.$:1234567890";
    private HashMap<String, Integer> symbolTable;

    //DESCRIPTION:   initializes hashmap with predefined symbols
    //PRECONDITION:  follows symbols/values from book/appendix
    //POSTCONDITION: all hashmap values have valid address integer
    public SymbolTable()
    {
        symbolTable = new HashMap<>();
        symbolTable.put("R0",0);
        symbolTable.put("R1",1);
        symbolTable.put("R2",2);
        symbolTable.put("R3",3);
        symbolTable.put("R4",4);
        symbolTable.put("R5"    ,5    );
        symbolTable.put("R6"    ,6    );
        symbolTable.put("R7"    ,7    );
        symbolTable.put("R8"    ,8    );
        symbolTable.put("R9"    ,9    );
        symbolTable.put("R10"   ,10   );
        symbolTable.put("R11"   ,11   );
        symbolTable.put("R12"   ,12   );
        symbolTable.put("R13"   ,13   );
        symbolTable.put("R14"   ,14   );
        symbolTable.put("R15"   ,15   );
        symbolTable.put("SCREEN",16384);
        symbolTable.put("KBD"   ,24576);
        symbolTable.put("SP"    ,0    );
        symbolTable.put("LCL"   ,1    );
        symbolTable.put("ARG"   ,2    );
        symbolTable.put("THIS"  ,3    );
        symbolTable.put("THAT"  ,4    );
    }

    //DESCRIPTION:   adds new pair of symbol/address to hashmap
    //PRECONDITION:  symbol/address pair not in hashmap (check contains() 1st)
    //POSTCONDITION: adds pair, returns true if added, false if illegal name
    public boolean addEntry(String symbol, int address)
    {
        if(!contains(symbol) || isValidName(symbol))
        {
            symbolTable.put(symbol, address);
            return true;
        }
        else
        {
            return false;
        }
    }

    //DESCRIPTION:   returns boolean of whether hashmap has symbol or not
    //PRECONDITION:  table has been initialized
    //POSTCONDITION: returns boolean if arg is in table or not
    public boolean contains(String symbol)
    {
        if(symbolTable.containsKey(symbol))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    //DESCRIPTION:   returns address in hashmap of given symbol
    //PRECONDITION:  symbol is in hashmap (check w/ contains() first)
    //POSTCONDITION: returns address associated with symbol in hashmap
    public int getAddress(String symbol)
    {
        if(symbolTable.containsKey(symbol))
        {
            return symbolTable.get(symbol);
        }
        else
        {
            return -1;
        }
    }

    //DESCRIPTION:   checks validity of identifiers for assembly code symbols
    //PRECONDITION:  start with letters or “_.$:” only, numbers allowed after
    //POSTCONDITION: returns true if valid identifier, false otherwise
    private boolean isValidName(String symbol)
    {
        char c  = 'a';
        for(int i = 0; i < symbol.length(); i++)
        {
            c = symbol.charAt(i);
            if(i == 0 && !INITIAL_VALID_CHARS.contains(Character.toString(c)) ||
                i != 0 && !ALL_VALID_CHARS.contains(Character.toString(c)))
            {
                return false;
            }
        }
        return true;
    }
}
