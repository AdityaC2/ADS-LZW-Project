package lzwPackage;

import java.io.*;
import javax.swing.JOptionPane;

public class LZWImageDecompressor {

  // Array to store dictionary entries
  static public String[] dictionaryArray;
  
  // File-related variables
  static public String fileName = "", extension = "", baseFileName;
  
  // Dictionary size and current/previous word indices
  static public int dictSize = 256, currentWordIndex, previousWordIndex;
  
  // Buffer for reading bytes and flag for buffer position
  static public byte[] buffer = new byte[3];
  static public boolean isLeft = true;

  // Constructor to initiate decompression process
  LZWImageDecompressor(String fName, String fileExtension) {
    baseFileName = fName;
    fileName = System.getProperty("user.home") + "/Desktop/" + fName;
    LZWImageDecompressor.extension = fileExtension;
    try {
      File file = new File(fileName);
      decompress();
      JOptionPane.showMessageDialog(null, "Decompression complete! Check file Decompressed_Image.extension on Desktop");
    } catch (IOException ie) {
      JOptionPane.showMessageDialog(null, "File not found!");
    }
  }

  // Decompression algorithm
  public static void decompress() throws IOException {
    // Initialize the dictionary array
    dictionaryArray = new String[4096];
    int i;

    // Initialize the dictionary with ASCII values
    for (i = 0; i < 256; i++) {
      dictionaryArray[i] = Character.toString((char) i);
    }

    // Open input and output files
    RandomAccessFile inputFile = new RandomAccessFile(fileName, "r");
    RandomAccessFile outputFile = new RandomAccessFile(System.getProperty("user.home") + "/Desktop/Decompressed_Image" + extension, "rw");

    try {
      // Read the first two bytes from the input file
      buffer[0] = inputFile.readByte();
      buffer[1] = inputFile.readByte();
      previousWordIndex = getIntValue(buffer[0], buffer[1], isLeft);
      isLeft = !isLeft;
      outputFile.writeBytes(dictionaryArray[previousWordIndex]);

      while (true) {
        // Read the next byte(s) based on the buffer position
        if (isLeft) {
          buffer[0] = inputFile.readByte();
          buffer[1] = inputFile.readByte();
          currentWordIndex = getIntValue(buffer[0], buffer[1], isLeft);
        } else {
          buffer[2] = inputFile.readByte();
          currentWordIndex = getIntValue(buffer[1], buffer[2], isLeft);
        }
        isLeft = !isLeft;

        // Update the dictionary and write to the output file
        if (currentWordIndex >= dictSize) {
          if (dictSize < 4096) {
            dictionaryArray[dictSize] = dictionaryArray[previousWordIndex] + dictionaryArray[previousWordIndex].charAt(0);
          }
          dictSize++;
          outputFile.writeBytes(dictionaryArray[previousWordIndex] + dictionaryArray[previousWordIndex].charAt(0));
        } else {
          if (dictSize < 4096) {
            dictionaryArray[dictSize] = dictionaryArray[previousWordIndex] + dictionaryArray[currentWordIndex].charAt(0);
          }
          dictSize++;
          outputFile.writeBytes(dictionaryArray[currentWordIndex]);
        }
        previousWordIndex = currentWordIndex;
      }
    } catch (EOFException e) {
      // Handle end of file, close input and output files
      inputFile.close();
      outputFile.close();
    }
  }

  // Utility method to convert bytes to an integer
  public static int getIntValue(byte b1, byte b2, boolean isLeft) {
    String t1 = Integer.toBinaryString(b1 & 0xFF);
    String t2 = Integer.toBinaryString(b2 & 0xFF);

    // Ensure correct bit lengths
    while (t1.length() < 8) t1 = "0" + t1;
    if (t1.length() == 32) t1 = t1.substring(24, 32);

    while (t2.length() < 8) t2 = "0" + t2;
    if (t2.length() == 32) t2 = t2.substring(24, 32);

    // Combine bytes and convert to integer
    if (isLeft) {
      return Integer.parseInt(t1 + t2.substring(0, 4), 2);
    } else {
      return Integer.parseInt(t1.substring(4, 8) + t2, 2);
    }
  }
}
