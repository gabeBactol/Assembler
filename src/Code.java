import java.util.HashMap;

public class Code
{
    private HashMap<String, String> compCodes;
    private HashMap<String, String> destCodes;
    private HashMap<String, String> jumpCodes;

    public Code()
    {
        compCodes = new HashMap<>();
        compCodes.put("0"  ,"0101010");
        compCodes.put("1"  ,"0111111");
        compCodes.put("-1" ,"0111010");
        compCodes.put("D"  ,"0001100");
        compCodes.put("A"  ,"0110000");
        compCodes.put("M"  ,"1110000");
        compCodes.put("!D" ,"0001101");
        compCodes.put("!A" ,"0110001");
        compCodes.put("!M" ,"1110001");
        compCodes.put("-D" ,"0001111");
        compCodes.put("-A" ,"0110011");
        compCodes.put("-M" ,"1110011");
        compCodes.put("D+1","0011111");
        compCodes.put("1+D","0011111");
        compCodes.put("A+1","0110111");
        compCodes.put("1+A","0110111");
        compCodes.put("M+1","1110111");
        compCodes.put("1+M","1110111");
        compCodes.put("D-1","0001110");
        compCodes.put("A-1","0110010");
        compCodes.put("M-1","1110010");
        compCodes.put("D+A","0000010");
        compCodes.put("A+D","0000010");
        compCodes.put("D+M","1000010");
        compCodes.put("M+D","1000010");
        compCodes.put("D-A","0010011");
        compCodes.put("D-M","1010011");
        compCodes.put("A-D","0000111");
        compCodes.put("A-D","1000111");
        compCodes.put("D&A","0000000");
        compCodes.put("A&D","0000000");
        compCodes.put("D&M","1000000");
        compCodes.put("M&D","1000000");
        compCodes.put("D|A","0010101");
        compCodes.put("A|D","0010101");
        compCodes.put("D|M","1010101");
        compCodes.put("M|D","1010101");

        destCodes = new HashMap<>();
        destCodes.put(""   ,"000");
        destCodes.put("A"  ,"100");
        destCodes.put("D"  ,"010");
        destCodes.put("M"  ,"001");
        destCodes.put("AD" ,"110");
        destCodes.put("DA" ,"110");
        destCodes.put("MD" ,"011");
        destCodes.put("DM" ,"011");
        destCodes.put("AM" ,"110");
        destCodes.put("MA" ,"110");
        destCodes.put("ADM","111");
        destCodes.put("AMD","111");
        destCodes.put("DAM","111");
        destCodes.put("DMA","111");
        destCodes.put("MAD","111");
        destCodes.put("MDM","111");

        jumpCodes = new HashMap<>();
        jumpCodes.put(""   ,"000");
        jumpCodes.put("JGT","001");
        jumpCodes.put("JEQ","010");
        jumpCodes.put("JLT","100");
        jumpCodes.put("JGE","011");
        jumpCodes.put("JNE","101");
        jumpCodes.put("JLE","110");
        jumpCodes.put("JMP","111");
    }
    public String getComp(String mnemonic)
    {
        if(compCodes.containsKey(mnemonic))
        {
            return compCodes.get(mnemonic);
        }
        else
        {
            return null;
        }
    }
    public String getDest(String mnemonic)
    {
        if(destCodes.containsKey(mnemonic))
        {
            return destCodes.get(mnemonic);
        }
        else
        {
            return null;
        }
    }
    public String getJump(String mnemonic)
    {
        if(jumpCodes.containsKey(mnemonic))
        {
            return jumpCodes.get(mnemonic);
        }
        else
        {
            return null;
        }
    }
    public String decimalToBinary(int decimal)
    {
        String binary = "";
        for(int i = 0; i < 15; i++)
        {
            if(decimal%2!=0)
            {
                binary = "1" + binary;
            }
            else
            {
                binary =  "0" + binary;
            }
            decimal/=2;
        }
        return binary;
    }

}
