import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Parser
{
    public static char NO_COMMAND = 'N';
    public static char A_COMMAND = 'A';
    public static char C_COMMAND = 'C';
    public static char L_COMMAND = 'L';

    private Scanner inputFile;
    private int lineNumber;
    private String rawLine;

    private String cleanLine;
    private char commandType;
    private String symbol;
    private String destMnemonic;
    private String compMnemonic;
    private String jumpMnemonic;
    public Parser(String inFileName)
    {
        try {
            File name = new File(inFileName);
            inputFile = new Scanner(name);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
        lineNumber = -1;


    }
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



    public void advance()
    {
        if(!hasMoreCommands())
        {
            rawLine = inputFile.nextLine();
            lineNumber++;
            cleanLine();
            parse();
        }
    }

    private void cleanLine()
    {
        cleanLine = rawLine.replaceAll(" ", "");
        if(cleanLine.contains("//"))
        {
            for(int number = 0; number < cleanLine.length(); number++)
            {
                if(cleanLine.charAt(number) != '/' && number+1 != cleanLine.length() && cleanLine.charAt(number+1) != '/')
                {
                    cleanLine = cleanLine.substring(0, number);
                }
            }

        }
    }
    private void parseCommandType()
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
        else if(cleanLine.equals(""))
        {
            commandType = NO_COMMAND;
        }
        else
        {
            commandType = C_COMMAND;
        }
    }
    private void parse()
    {
        parseCommandType();
        parseSymbol();
        parseComp();
        parseDest();
        parseJump();
    }
    public void parseSymbol()
    {
        symbol = cleanLine.replaceAll("[@()]","");
    }
    public void parseDest()
    {

    }
    public void parseComp()
    {

    }
    public void parseJump()
    {

    }

    public char getCommandType() {
        return commandType;
    }
    public String getSymbol()
    {
        return symbol;
    }
    public String getDest()
    {
        return destMnemonic;
    }

    public String getComp() {
        return compMnemonic;
    }

    public String getJump() {
        return jumpMnemonic;
    }

    public String getCommandTypeString()
    {
        return Character.toString(commandType);
    }



    public String getRawLine() {
        return rawLine;
    }

    public String getCleanLine() {
        return cleanLine;
    }
    public int getLineNumber() {
        return lineNumber;
    }


}
