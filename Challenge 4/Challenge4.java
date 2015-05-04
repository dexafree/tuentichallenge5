import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * TUENTI CHALLENGE 2015
 * CHALLENGE 4
 * Dexafree
 */
public class Challenge4 {

    public void execute(String input, String output) throws IOException {

        // Read all the lines in the file
        List<String> lines = Files.readAllLines(Paths.get(input), StandardCharsets.UTF_8);

        // Get the Base64 String
        String base64String = lines.get(0);

        lines.remove(0);

        // Get the number of lines
        int numLines = Integer.parseInt(lines.get(0));

        lines.remove(0);

        // Convert the Base64 String to bits
        String macroString = getInputAsBitString(base64String);

        // Open the file
        PrintWriter out = new PrintWriter(output);

        // Iterate through lines
        for(int i=0;i<numLines; i++){

            String line = lines.get(i);

            // Check if it must be Little Endian
            boolean isLittleEndian = getModifier(line, "L");

            // Check if it must be Reversed
            boolean mustReverse = getModifier(line, "R");

            // Get how many bits we have to read
            int stringLength = getLength(line);

            // Give feedback
            System.out.println(i+1);

            // Splice the String
            String bytesToParse = macroString.substring(0, stringLength);

            // Make the operations
            BigInteger num = extract(bytesToParse, isLittleEndian, mustReverse);

            // Write the number to the file
            out.println(num);

            // Delete the spliced bits from the base String
            macroString = macroString.substring(stringLength, macroString.length());

        }

        // Close the file
        out.close();

    }

    private int getLength(String line){
        return Integer.parseInt(line.replaceAll("\\D+", ""));
    }

    private boolean getModifier(String str, String charToContain){
        return str.contains(charToContain);
    }

    private BigInteger extract(String bits, boolean isLittleEndian, boolean reverse){

        String total = bits;

        if(isLittleEndian) {

            // Calculate how many bytes we need
            int bytesNeeded = ((total.length() + 8 - 1) / 8);

            // Create the String[] that will contain the bytes
            String[] bytes = new String[bytesNeeded];

            // Create the bytes
            int i = 0;
            while (total.length() > 8) {
                String group = total.substring(0, 8);
                bytes[i] = group;
                i++;
                total = total.substring(8, total.length());
            }

            bytes[i] = total;

            // As it is Little Endian, we have to reverse the bytes
            Collections.reverse(Arrays.asList(bytes));

            // Concat the bytes content
            StringBuilder buffer = new StringBuilder();
            for (String b : bytes) {
                buffer.append(b);
            }

            total = buffer.toString();
        }


        // IF we have to reverse it, do it now
        if(reverse)
            total = new StringBuffer(total).reverse().toString();


        // Convert it to Integer and return
        return new BigInteger(total, 2);

    }

    private String getInputAsBitString(String input){

        String result = "";
        for(Character c : input.toCharArray()){
            result += base64CharTo6Bit(c);
        }
        return result;
    }

    private String base64CharTo6Bit(char base64Char){
        int val = 0;
        if(base64Char >= 'A' && base64Char <= 'Z'){
            val = base64Char - 'A';
        } else if (base64Char >= 'a' && base64Char <= 'z'){
            val = base64Char - 'a' + 26;
        } else if (base64Char >= '0' && base64Char <= '9'){
            val = base64Char - '0' + 52;
        } else if (base64Char == '+'){
            val = 62;
        } else if (base64Char == '/'){
            val = 63;
        }

        String binary = Integer.toBinaryString(val);

        while(binary.length() < 6){
            binary = '0'+binary;
        }

        return binary;
    }


    public static void main(String[] args){

        try {
            new Challenge4().execute(args[0], args[1]);
        } catch (IOException e){
            System.out.println("File not found");
        }
    }
}
