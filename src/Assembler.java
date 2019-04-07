/**
 *	Assembler.java: Reads an .asm file and converts it into a .hack file containing
 *					binary numbers.
 * @author Gabriel Bactol
 * @version 4.0
 */
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;

public class Assembler {

	// ALGORITHM:
	// get input file name
	// create output file name and stream

	// create symbol table
	// do first pass to build symbol table (no output yet!)
	// do second pass to output translated ASM to HACK code

	// print out "done" message to user
	// close output file stream
	public static void main(String[] args) {

		String inputFileName, outputFileName;
		PrintWriter outputFile = null; //keep compiler happy
		SymbolTable symbolTable = new SymbolTable();

		//get input file name from command line or console input
		if(args.length == 1) {
			System.out.println("command line arg = " + args[0]);
			inputFileName = args[0];
		}
		else
		{
			Scanner keyboard = new Scanner(System.in);

			System.out.println("Please enter assembly file name you would like to assemble.");
			System.out.println("Don't forget the .asm extension: ");
			inputFileName = keyboard.nextLine();

			keyboard.close();
		}

		outputFileName = inputFileName.substring(0,inputFileName.lastIndexOf('.')) + ".hack";

		try {
			outputFile = new PrintWriter(new FileOutputStream(outputFileName));
		} catch (FileNotFoundException ex) {
			System.err.println("Could not open output file " + outputFileName);
			System.err.println("Run program again, make sure you have write permissions, etc.");
			System.exit(0);
		}

		// TODO: finish driver as algorithm describes
		firstPass(inputFileName, symbolTable);
		secondPass(inputFileName, symbolTable, outputFile);
	}

	// TODO: march through the source code without generating any code
		//for each label declaration (LABEL) that appears in the source code,
		// add the pair <LABEL, n> to the symbol table
		// n = romAddress which you should keep track of as you go through each line
	//HINT: when should rom address increase? For what kind of commands?
	private static void firstPass(String inputFileName, SymbolTable symbolTable)
	{
		Parser firstPass = new Parser(inputFileName);
		int romAddress = 0;
		while(firstPass.hasMoreCommands())
		{
			firstPass.advance();
			if(firstPass.getCommandType() == firstPass.L_COMMAND)
            {
                symbolTable.addEntry(firstPass.getSymbol(), romAddress);
            }
			else if(firstPass.getCommandType() == firstPass.C_COMMAND ||
					firstPass.getCommandType() == firstPass.A_COMMAND)
			{
				romAddress++;
			}

		}
	}

	// TODO: march again through the source code and process each line:
		// if the line is a c-instruction, simple (translate)
		// if the line is @xxx where xxx is a number, simple (translate)
		// if the line is @xxx and xxx is a symbol, look it up in the symbol
		//	table and proceed as follows:
			// If the symbol is found, replace it with its numeric value and
			//	and complete the commands translation
			// If the symbol is not found, then it must represent a new variable:
				// add the pair <xxx, n> to the symbol table, where n is the next
				//	available RAM address, and complete the commands translation
	// HINT: What should ram address start at? When should it increase?
	// What do you do with L commands and No commands?
	private static void secondPass(String inputFileName, SymbolTable symbolTable, PrintWriter outputFile) {
		Code codeObject = new Code();
		Parser secondPass = new Parser(inputFileName);
		int ramAddress = 16;
		String c,d,j;
		while(secondPass.hasMoreCommands())
		{
			secondPass.advance();
			String result = "";
			if(secondPass.getCommandType() == secondPass.A_COMMAND)
            {
                String symbol = secondPass.getSymbol();
                if(symbol.matches("\\d+"))
                {
                    result = "0" + codeObject.decimalToBinary(
                            Integer.parseInt(symbol));
                }
                else
                {
                    if(!symbolTable.contains(symbol))
                    {
                        symbolTable.addEntry(symbol,ramAddress);
                        ramAddress++;
                    }
                    result = "0" + codeObject.decimalToBinary(
                            symbolTable.getAddress(symbol));
                }
            }
			else if(secondPass.getCommandType() == secondPass.C_COMMAND)
			{
				c = codeObject.getComp(secondPass.getComp());

				d = codeObject.getDest(secondPass.getDest());

				j = codeObject.getJump(secondPass.getJump());
				if(c == null || d == null || j == null)
				{
					System.out.println("Invalid C Instructions. " +
							"Check the .asm file again.\n");
					System.exit(0);
				}

                result = "111" + c + d + j;

			}

			if(secondPass.getCommandType() != secondPass.NO_COMMAND &&
					secondPass.getCommandType() != secondPass.L_COMMAND)
			{
				outputFile.println(result);
			}
		}
		
		outputFile.close();
		System.out.println("The program has ended, and the " + 
							".hack file has been created.");
	}



}
