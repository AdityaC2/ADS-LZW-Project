package lzwPackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
public class imageCompressorInterface extends JFrame {
  private JPanel mainPanel;
  private JPanel sidePanel;
  void compressImage() {
    mainPanel.removeAll();
    mainPanel.setLayout(new GridLayout(3, 1, 50, 50));
    JLabel prompt = new JLabel("Enter the image name located on Desktop, with the extension (example.png):");
    JTextField textinput = new JTextField();
    JButton selectButton = new JButton("Compress");
    selectButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String FileName = textinput.getText();
        new LZWImageCompressor(FileName);
      }
    });
    mainPanel.add(prompt);
    mainPanel.add(textinput);
    mainPanel.add(selectButton);

    mainPanel.revalidate();
    mainPanel.repaint();
  }
  void decompressImage() {
    mainPanel.removeAll();
    mainPanel.setLayout(new GridLayout(5, 1, 50, 50));
    JLabel prompt = new JLabel("Enter the name of the compressed image on Desktop, with .lzw extension (example.lzw):");
    JTextField textinput = new JTextField();
    String[] imageFormats = {
      ".png",
      ".jpg"
    };
    JLabel prompt2 = new JLabel("Select the extension of the original file");
    JComboBox < String > formatComboBox = new JComboBox < > (imageFormats);
    JButton selectButton = new JButton("Decompress");
    selectButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String selectedFormat = (String) formatComboBox.getSelectedItem();
        String FileName = textinput.getText();
        new LZWImageDecompressor(FileName, selectedFormat);
      }
    });
    mainPanel.add(prompt);
    mainPanel.add(textinput);
    mainPanel.add(prompt2);
    mainPanel.add(formatComboBox);
    mainPanel.add(selectButton);

    mainPanel.revalidate();
    mainPanel.repaint();
  }
  public imageCompressorInterface() {
    super("Image Compressor");
    sidePanel = new JPanel();
    sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
    sidePanel.setBorder(new EmptyBorder(20, 30, 20, 20));
    sidePanel.setBackground(Color.DARK_GRAY);
    sidePanel.setPreferredSize(new Dimension(200, 600));
    mainPanel = new JPanel();
    mainPanel.setBackground(Color.WHITE);
    JLabel message = new JLabel("Image Compressor");
    message.setFont(new Font("Montserrat", Font.BOLD, 10));
    message.setForeground(Color.WHITE);
    message.addMouseListener(new MouseListener() {
      public void mouseClicked(MouseEvent e) {
        compressImage();
      }
      @Override
      public void mousePressed(MouseEvent e) {}
      @Override
      public void mouseReleased(MouseEvent e) {}
      @Override
      public void mouseEntered(MouseEvent e) {}
      @Override
      public void mouseExited(MouseEvent e) {}
    });
    JLabel history = new JLabel("Image Decommpressor");
    history.setFont(new Font("Montserrat", Font.BOLD, 10));
    history.setForeground(Color.WHITE);
    history.addMouseListener(new MouseListener() {
      public void mouseClicked(MouseEvent e) {
        decompressImage();
      }
      @Override
      public void mousePressed(MouseEvent e) {}
      @Override
      public void mouseReleased(MouseEvent e) {}
      @Override
      public void mouseEntered(MouseEvent e) {}
      @Override
      public void mouseExited(MouseEvent e) {}
    });
    compressImage();
    sidePanel.add(Box.createVerticalGlue());
    sidePanel.add(message);
    sidePanel.add(Box.createVerticalGlue());
    sidePanel.add(history);
    sidePanel.add(Box.createVerticalGlue());
    setLayout(new BorderLayout());
    add(sidePanel, BorderLayout.WEST);
    add(mainPanel, BorderLayout.CENTER);
    setDefaultCloseOperation(HIDE_ON_CLOSE);
    setSize(800, 500);
    setResizable(false);
    setLocationRelativeTo(null);
    setVisible(true);
  }
  public static void main(String[] args) {
    new imageCompressorInterface();
  }
}