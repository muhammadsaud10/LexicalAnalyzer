package A2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LexicalAnalyzer {
    
    public static void main(String [] args) throws IOException
    {
        // Initialize file reader
        FileReader fr = null;
        while (true) 
        {
            if (args.length > 0)
            {
                try 
                {
                    // Attempt to open the input file
                    fr = new FileReader(args[0]);
                    break;
                }
                catch (FileNotFoundException e)
                {
                    // Print error message if file is not found
                    System.err.println("File not found. Please enter a valid file name.");
                    System.exit(1); // Exit the program with an error code
                }
            }
            else
            {
                // Print usage message and exit if no command line arguments are provided
                System.err.println("Usage: java LexicalAnalyzer <input_file>");
                System.exit(1); // Exit the program with an error code
            }
        }
        
        System.out.println(args[0]); // Print the name of the input file

        int i = fr.read(); // Read the first character
        int prev = 0; // Variable to store the previous character

        System.out.print("Lexeme: "); // Print the initial "Lexeme: "

        while (i != '$') // Loop until the special delimiter character '$'
        {
            if (i == ',')
            {
                prev = i;
                i = fr.read();
                continue;
            }

            if (i == ' ')
            {
                // Print newline and "Lexeme: " when encountering space
                System.out.print("\nLexeme: ");
                prev = i;
                i = fr.read();
                continue;
            }

            // Check for certain special characters
            if (i == '(' || i == ')' || i == '{' || i == '}')
            {
                if (prev != ' ')
                {
                    // Print newline and "Lexeme: " before the special character
                    System.out.print("\nLexeme: ");
                    System.out.print((char)i);
                    prev = i;
                    i = fr.read();
                    continue;
                }
                else
                {
                    // Print the special character
                    System.out.print((char)i);
                    prev = i;
                    i = fr.read();
                    continue;
                }
            }

            // Check for specific cases involving previous character
            if (prev == '(' || prev == ')' || prev == '{' || prev == '}' || prev == ';')
            {
                // Print newline and "Lexeme: " before certain characters
                System.out.print("\nLexeme: ");
                System.out.print((char)i);
                prev = i;
                i = fr.read();
                continue;
            }

            if (i == ';')
            {
                // Print newline and "Lexeme: " before semicolon
                System.out.print("\nLexeme: ");
                System.out.print((char)i);
                prev = i;
                i = fr.read();
                continue;
            }

            // Check for consecutive + or - signs
            if (i == '+' || i == '-')
            {
                if (prev == '+' || prev == '-')
                {
                    // Print the consecutive + or - signs
                    System.out.print((char)i);
                    prev = i;
                    i = fr.read();
                    continue;
                }
                else
                {
                    // Print newline and "Lexeme: " before + or -
                    System.out.print("\nLexeme: ");
                    System.out.print((char)i);
                    prev = i;
                    i = fr.read();
                    continue;
                }
            }

            // Print the current character
            System.out.print((char)i);
            prev = i;
            i = fr.read();
        }

        fr.close(); // Close the input file
    }
}
