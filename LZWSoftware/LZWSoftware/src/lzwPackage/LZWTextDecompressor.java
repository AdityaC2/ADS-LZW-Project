package lzwPackage;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class LZWTextDecompressor {

    public LZWTextDecompressor(String fileName) {
        String inputFilePath = System.getProperty("user.home") + "/Desktop/" + fileName;
        String outputFilePath = System.getProperty("user.home") + "/Desktop/Decompressed_File.txt";
        File file = new File(inputFilePath);
        decompress(inputFilePath, outputFilePath);
        JOptionPane.showMessageDialog(null, "Decompression complete! Check file Decompressed_File.txt on Desktop");
    }

    public static void decompress(String inputFilePath, String outputFilePath) {
        try {
            DataInputStream inputStream = new DataInputStream(new FileInputStream(inputFilePath));
            FileOutputStream outputStream = new FileOutputStream(outputFilePath);

            // Initialize dictionary with ASCII values
            List<String> dictionary = new ArrayList<>();
            for (int i = 0; i < 256; i++) {
                dictionary.add("" + (char) i);
            }

            int nextCode = 257;
            int currentCode = inputStream.readUnsignedShort();
            StringBuilder result = new StringBuilder(dictionary.get(currentCode));
            outputStream.write(result.toString().getBytes());

            while (inputStream.available() > 0) {
                int code = inputStream.readUnsignedShort();

                String entry;
                if (code < dictionary.size()) {
                    // Code is within the current dictionary size
                    entry = dictionary.get(code);
                } else if (code == dictionary.size()) {
                    // Special case for code equal to the dictionary size
                    entry = result.toString() + result.charAt(0);
                } else {
                    // Invalid compressed code
                    throw new IllegalArgumentException("Bad compressed code");
                }

                // Update the dictionary and write to the output stream
                dictionary.add(result.toString() + entry.charAt(0));
                result = new StringBuilder(entry);
                outputStream.write(entry.getBytes());
            }

            // Close input and output streams
            inputStream.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
