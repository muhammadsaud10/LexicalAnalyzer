package A2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Preprocessor 
{
    public static void main(String [] args) throws IOException
    {
        // Initialize file reader and writer
        FileReader fr = null;
        FileWriter fw = new FileWriter("out1_tmp.txt");

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
                System.err.println("Usage: java Preprocessor <input_file>");
                System.exit(1); // Exit the program with an error code
            }
        }
        
        int i;
        int prev = 0;
        
        // Loop through each character in the input file
        while((i=fr.read())!=-1)
        {
            // Remove extra spaces after newline
            if (i == ' ' && prev == '\n')
            {
                while ((i=fr.read()) == ' ');
                prev = i;
            }
            
            // Skip single-line comments
            if (i=='/' && (i=fr.read()) == '/')
            {
                prev = i;
            } 
            else if (i == '*') {
                // Skip block comments
                while((i=fr.read())!='/');
                prev = i;
                continue;
            }
            if(i=='/' && prev=='/')
            {
                // Skip remaining characters on the current line for single-line comments
                while((i=fr.read())!='\n');
                continue;
            }
            
            // Remove duplicate spaces and tabs
            if ((i == ' ' && prev == ' ') || (i == '\t' && prev == '\n'))
            {
                prev = i;
                continue;
            }
            
            // Remove "import" statements
            if (i == 'i')
            {
                prev = i;
                if ((i=fr.read()) == 'm')
                {
                    // Skip the entire line for import statements
                    while((i=fr.read())!='\n');
                    prev = i;
                    continue;
                }
                else
                {
                    fw.write(prev);
                }
            }
            
            // Write the current character to the output file
            fw.write(i);
            prev = i;
        }
        
        // Close input and output files
        fr.close();
        fw.close();

        // Open the temporary output file for further processing
        fr = new FileReader("out1_tmp.txt");
        fw = new FileWriter("out1.txt");
        prev = 0;

        // Loop through each character in the temporary output file
        while((i=fr.read())!=-1)
        {
            // Remove empty lines
            if (i == '\r' && prev == '\n' || prev == 0 && i == '\r') {
                prev = i;
                continue;
            }
            if (prev == '\r' && i == '\n') {
                prev = i;
                continue;
            }
            
            // Remove extra spaces before semicolons or carriage returns
            if (i == ' ') {
                prev = i;
                if ((i=fr.read()) == ';' || i=='\r') {
                    prev = i;
                } else {
                    fw.write(prev);
                }
            }
            
            // Write the current character to the output file
            fw.write(i);
            prev = i;
        }
        
        // Close input and output files
        fr.close();
        fw.close();
        
        // Open the final processed output file for printing
        fr = new FileReader("out1.txt");
        
        // Display the contents of the processed output file
        System.out.println("***** out1.txt *****");
        Scanner input = new Scanner(fr);
        while((input.hasNextLine()))
        {
            System.out.println(input.nextLine());
        }
        input.close();
        fr.close();
    }
}
