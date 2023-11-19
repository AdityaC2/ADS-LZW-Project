package lzwPackage;
import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

public class LZWImageCompressor {

    // HashMap to store the dictionary
    public static HashMap<String, Integer> dictionary = new HashMap<>();

    // Initial size of the dictionary
    public static int dictSize = 256;

    // Current string and file name
    public static String current = "", fileName = "", bitPattern = "";

    // Byte and buffer variables for processing
    public static byte inputByte;
    public static byte[] buffer = new byte[3];

    // Flag to indicate whether the buffer is on the left or right
    public static boolean isLeft = true;

    // Constructor to initiate compression process
    public LZWImageCompressor(String fName) {
        fileName = System.getProperty("user.home") + "/Desktop/" + fName;
        try {
            File file = new File(fileName);
            compress();
            JOptionPane.showMessageDialog(null, "Compression complete! Check file Compressed_Image.lzw on Desktop");
        } catch (IOException ie) {
            JOptionPane.showMessageDialog(null, "File not found!");
        }
    }

    // Compression algorithm
    public static void compress() throws IOException {
        int i, byteValue;
        char currentChar;

        // Initialize the dictionary with ASCII values
        for (i = 0; i < 256; i++) {
            dictionary.put(Character.toString((char) i), i);
        }

        // Open input and output files
        RandomAccessFile inputFile = new RandomAccessFile(fileName, "r");
        String[] fileNameWithoutExtension = fileName.split("\\.");
        RandomAccessFile outputFile = new RandomAccessFile("Compressed_Image.lzw", "rw");

        try {
            // Read the first byte and convert it to an integer
            inputByte = inputFile.readByte();
            byteValue = new Byte(inputByte).intValue();
            if (byteValue < 0) byteValue += 256;
            currentChar = (char) byteValue;
            current = "" + currentChar;

            // Main compression loop
            while (true) {
                inputByte = inputFile.readByte();
                byteValue = new Byte(inputByte).intValue();
                if (byteValue < 0) byteValue += 256;
                currentChar = (char) byteValue;

                if (dictionary.containsKey(current + currentChar)) {
                    // If the combination is in the dictionary, extend the current string
                    current = current + currentChar;
                } else {
                    // Otherwise, write the current string's code to the output file
                    bitPattern = convertTo12Bit(dictionary.get(current));
                    if (isLeft) {
                        buffer[0] = (byte) Integer.parseInt(bitPattern.substring(0, 8), 2);
                        buffer[1] = (byte) Integer.parseInt(bitPattern.substring(8, 12) + "0000", 2);
                    } else {
                        buffer[1] += (byte) Integer.parseInt(bitPattern.substring(0, 4), 2);
                        buffer[2] = (byte) Integer.parseInt(bitPattern.substring(4, 12), 2);
                        for (i = 0; i < buffer.length; i++) {
                            outputFile.writeByte(buffer[i]);
                            buffer[i] = 0;
                        }
                    }
                    isLeft = !isLeft;

                    // Add the new combination to the dictionary
                    if (dictSize < 4096) dictionary.put(current + currentChar, dictSize++);
                    current = "" + currentChar;
                }
            }

        } catch (IOException ie) {
            // Handle end of file, write the last code to the output file
            bitPattern = convertTo12Bit(dictionary.get(current));
            if (isLeft) {
                buffer[0] = (byte) Integer.parseInt(bitPattern.substring(0, 8), 2);
                buffer[1] = (byte) Integer.parseInt(bitPattern.substring(8, 12) + "0000", 2);
                outputFile.writeByte(buffer[0]);
                outputFile.writeByte(buffer[1]);
            } else {
                buffer[1] += (byte) Integer.parseInt(bitPattern.substring(0, 4), 2);
                buffer[2] = (byte) Integer.parseInt(bitPattern.substring(4, 12), 2);
                for (i = 0; i < buffer.length; i++) {
                    outputFile.writeByte(buffer[i]);
                    buffer[i] = 0;
                }
            }
            // Close input and output files
            inputFile.close();
            outputFile.close();
        }
    }

    // Utility method to convert an integer to a 12-bit binary string
    public static String convertTo12Bit(int i) {
        String to12Bit = Integer.toBinaryString(i);
        while (to12Bit.length() < 12) to12Bit = "0" + to12Bit;
        return to12Bit;
    }
}