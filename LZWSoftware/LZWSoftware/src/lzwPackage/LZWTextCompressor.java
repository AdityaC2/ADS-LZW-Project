package lzwPackage;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class LZWTextCompressor {

    public LZWTextCompressor(String fileName) {
        String inputFilePath = System.getProperty("user.home") + "/Desktop/" + fileName;
        String outputFilePath = System.getProperty("user.home") + "/Desktop/Compressed_File.lzw";
        File file = new File(inputFilePath);
        compress(inputFilePath, outputFilePath);
        JOptionPane.showMessageDialog(null, "Compression complete! Check file Compressed_File.lzw on Desktop");
    }

    public static void compress(String inputFilePath, String outputFilePath) {
        try {
            FileInputStream inputStream = new FileInputStream(inputFilePath);
            DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(outputFilePath));

            // Initialize dictionary with ASCII values
            Map<String, Integer> dictionary = new HashMap<>();
            for (int i = 0; i < 256; i++) {
                dictionary.put("" + (char) i, i);
            }

            int nextCode = 257;
            StringBuilder currentCode = new StringBuilder();

            int byteRead;
            while ((byteRead = inputStream.read()) != -1) {
                char ch = (char) byteRead;
                String newCode = currentCode.toString() + ch;

                if (dictionary.containsKey(newCode)) {
                    // If the new code is in the dictionary, extend the current code
                    currentCode = new StringBuilder(newCode);
                } else {
                    // Write the code for the current sequence to the output stream
                    outputStream.writeShort(dictionary.get(currentCode.toString()));

                    // Add the new sequence to the dictionary
                    dictionary.put(newCode, nextCode++);
                    
                    // Reset the current code to the current character
                    currentCode = new StringBuilder("" + ch);
                }
            }

            // Write the last code to the output stream
            if (!currentCode.toString().isEmpty()) {
                outputStream.writeShort(dictionary.get(currentCode.toString()));
            }

            // Close input and output streams
            inputStream.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
