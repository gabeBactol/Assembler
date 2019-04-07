import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
/**
 * Assembler.java: Reads an .asm file, cleaning the line and parsing commands within the .asm file.
 * It also figures out the comp, dest, and jump commands within the line.
 * @author Gabriel Bactol
 * @version 4.0
 */
public class Parser
{
    public static final char NO_COMMAND = 'N';
    public static final char A_COMMAND = 'A';
    public static final char C_COMMAND = 'C';
    public static final char L_COMMAND = 'L';

    private Scanner inputFile;
    private int lineNumber;
    private String rawLine;

    private String cleanLine;
    private char commandType;
    private String symbol;
    private String destMnemonic;
    private String compMnemonic;
    private String jumpMnemonic;

    //DESCRIPTION:   opens input file/stream and prepares to parse
    //PRECONDITION:  provided file is ASM file
    //POSTCONDITION: if file can’t be opened, ends program w/ error message
    public Parser(String inFileName)
    {
        try {
            inputFile = new Scanner(new File(inFileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    //DESCRIPTION:   returns boolean if more commands left, closes stream if not
    //PRECONDITION:  file stream is open
    //POSTCONDITION: returns true if more commands, else closes stream
    public boolean hasMoreCommands()
    {
        if(inputFile.hasNext())
        {
            return true;
        }
        else
        {
            inputFile.close();
            return false;
        }
    }

    //DESCRIPTION:   reads next line from file and parses it into instance vars
    //PRECONDITION:  file stream is open, called only if hasMoreCommands()
    //POSTCONDITION: current instruction parts put into instance vars
    public void advance()
    {
        if(hasMoreCommands())
        {
            rawLine = inputFile.nextLine();
            lineNumber++;
            cleanLine();
            parse();
        }
    }

    //DESCRIPTION:   cleans raw instruction by removing non-essential parts
    //PRECONDITION:  String parameter given (not null)
    //POSTCONDITION: returned without comments and whitespace
    private void cleanLine()
    {
        cleanLine = rawLine.replaceAll(" ", "");
        if(cleanLine.contains("//"))
        {
            for(int number = 0; number < cleanLine.length(); number++)
            {
                if(cleanLine.charAt(number) == '/' && number+1 != 
						cleanLine.length() && cleanLine.charAt(number+1) 
																== '/')
                {
                    cleanLine = cleanLine.substring(0, number);
                    break;
                }
            }
        }
    }

    //DESCRIPTION:   determines command type from parameter
    //PRECONDITION:  String parameter is clean instruction
    //POSTCONDITION: returns ‘A’ (A-instruction), ‘C’ (C-instruction),
    //               ‘L’ (Label), ‘N’ (no command)
    private void parseCommandType()
    {
		if(cleanLine.equals(""))
        {
            commandType = NO_COMMAND;
        }
        else
        {
			char c = cleanLine.charAt(0);
	        if(c == '@')
	        {
	            commandType = A_COMMAND;
	        }
	        else if(c == '(')
	        {
	            commandType = L_COMMAND;
	        }
	        
	        else
	        {
	            commandType = C_COMMAND;
	        }
		}  
    }

    //DESCRIPTION:   helper method parses line depending on instruction type
    //PRECONDITION:  advance() called so cleanLine has value
    //POSTCONDITION: appropriate parts (instance vars) of instruction filled
    private void parse()
    {
        parseCommandType();
        parseSymbol();
        parseComp();
        parseDest();
        parseJump();
    }

    //DESCRIPTION:   parses symbol for A- or L-commands
    //PRECONDITION:  advance() called so cleanLine has value,
    //call for A- and L-commands only
    //POSTCONDITION: symbol has appropriate value from instruction assigned
    public void parseSymbol()
    {
        symbol = cleanLine.replaceAll("[@()]","");
    }

    //DESCRIPTION:   helper method parses line to get dest part
    //PRECONDITION:  advance() called so cleanLine has value,
    //call for C-instructions only
    //POSTCONDITION: destMnemonic set to appropriate value from instruction
    public void parseDest()
    {
        destMnemonic = "";
        if(cleanLine.contains("="))
        {
            destMnemonic = cleanLine.substring(0, cleanLine.indexOf('='));
        }
    }

    //DESCRIPTION:   helper method parses line to get comp part
    //PRECONDITION:  advance() called so cleanLine has value,
    //call for C-instructions only
    //POSTCONDITION: compMnemonic set to appropriate value from instruction

    public void parseComp()
    {
		int start = 0;
		int end = 0;
		if(cleanLine.contains("="))
		{
			start = cleanLine.indexOf("=")+1;
		}
		else
		{
			start = 0;
		}
		
		if(cleanLine.contains(";"))
		{
			end = cleanLine.indexOf(";");
		}
		else
		{
			end = cleanLine.length();
		}
		compMnemonic = cleanLine.substring(start, end);
    }

    //DESCRIPTION:   helper method parses line to get jump part
    //PRECONDITION:  advance() called so cleanLine has value,
    //   call for C-instructions only
    //POSTCONDITION: jumpMnemonic set to appropriate value from instruction
    public void parseJump()
    {
        jumpMnemonic = "";
        if(cleanLine.contains(";"))
        {
            jumpMnemonic = cleanLine.substring(cleanLine.indexOf(';')+1);
        }
    }

    //DESCRIPTION:   getter for command type
    //PRECONDITION:  cleanLine has been parsed (advance was called)
    //POSTCONDITION: returns char for command type (N/A/C/L)
    public char getCommandType() {
        return commandType;
    }

    //DESCRIPTION:   getter for symbol name
    //PRECONDITION:  cleanLine has been parsed (advance was called),
    //   call for labels only (use getCommandType())
    //POSTCONDITION: returns string for symbol name
    public String getSymbol()
    {
        return symbol;
    }

    //DESCRIPTION:   getter for dest part of C-instruction
    //PRECONDITION:  cleanLine has been parsed (advance was called),
    //  call for C-instructions only (use getCommandType())
    //POSTCONDITION: returns mnemonic (ASM symbol) for dest part
    public String getDest()
    {
        return destMnemonic;
    }

    //DESCRIPTION:   getter for comp part of C-instruction
    //PRECONDITION:  cleanLine has been parsed (advance was called),
    //  call for C-instructions only (use getCommandType())
    //POSTCONDITION: returns mnemonic (ASM symbol) for comp part
    public String getComp() {
        return compMnemonic;
    }

    //DESCRIPTION:   getter for jump part of C-instruction
    //PRECONDITION:  cleanLine has been parsed (advance was called),
    //  call for C-instructions only (use getCommandType())
    //POSTCONDITION: returns mnemonic (ASM symbol) for jump part
    public String getJump() {
        return jumpMnemonic;
    }

    //DESCRIPTION:   getter for string version of command type (debugging)
    //PRECONDITION:  advance() and parse() have been called
    //POSTCONDITION: returns string version of command type
    public String getCommandTypeString()
    {
        return Character.toString(commandType);
    }

    //DESCRIPTION:   getter for rawLine from file (debugging)
    //PRECONDITION:  advance() was called to put value from file in here
    //POSTCONDITION: returns string of current original line from file
    public String getRawLine() {
        return rawLine;
    }

    //DESCRIPTION:   getter for cleanLine from file (debugging)
    //PRECONDITION:  advance() and cleanLine() were called
    //POSTCONDITION: returns string of current clean instruction from file
    public String getCleanLine() {
        return cleanLine;
    }

    //DESCRIPTION:   getter for lineNumber (debugging)
    //PRECONDITION:  n/a
    //POSTCONDITION: returns line number currently being processed from file
    public int getLineNumber() {
        return lineNumber;
    }
}
