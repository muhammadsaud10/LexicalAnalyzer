package A2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Processor {
    public static void main(String [] args) throws IOException
    {
        // Initialize file reader and writer
        FileReader fr = null;
        FileWriter fw = new FileWriter("out1_tmp.txt");

        // Check if command line arguments are provided
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
                System.err.println("Usage: java Processor <input_file>");
                System.exit(1); // Exit the program with an error code
            }
        }

        int i = 0;
        int prev = 0;
        char[] cbuf = new char[170]; // Create a character buffer to store read characters
        int idx = 0; // Index to keep track of the current position in the buffer

        // Read characters from the input file
        while((i = fr.read()) != -1)
        {
            // Remove empty lines (carriage return)
            if (i == '\r') {
                prev = i;
                continue;
            }
            // Continue skipping characters if carriage return followed by newline
            if (prev == '\r' && i == '\n') {
                prev = i;
                continue;
            }

            // Store the current character in the buffer and increment the index
            cbuf[idx] = (char)i;
            idx++;
            prev = i;
        }

        cbuf[idx] = '$'; // Add a special delimiter at the end of the buffer
        idx++;

        // Write the contents of the buffer to the output file
        fw.write(cbuf, 0, idx);

        // Print the contents of the buffer to the console
        System.out.println(cbuf);

        // Close the input and output files
        fr.close();
        fw.close();
    }
}
