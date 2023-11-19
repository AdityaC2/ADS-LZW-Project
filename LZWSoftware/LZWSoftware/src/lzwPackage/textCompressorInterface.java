package lzwPackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class textCompressorInterface extends JFrame {
  private JPanel mainPanel;
  private JPanel sidePanel;
  void compressText() {
    mainPanel.removeAll();
    mainPanel.setLayout(new GridLayout(3, 1, 50, 50));
    JLabel prompt = new JLabel("Enter the text file name located on Desktop, with the extension (example.txt):");
    JTextField textinput = new JTextField();
    JButton selectButton = new JButton("Compress");
    selectButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String FileName = textinput.getText();
        new LZWTextCompressor(FileName);
      }
    });
    mainPanel.add(prompt);
    mainPanel.add(textinput);
    mainPanel.add(selectButton);

    mainPanel.revalidate();
    mainPanel.repaint();
  }
  void decompressText() {
    mainPanel.removeAll();
    mainPanel.setLayout(new GridLayout(3, 1, 50, 50));
    JLabel prompt = new JLabel("Enter the name of the compressed text file on Desktop, with .lzw extension (example.lzw):");
    JTextField textinput = new JTextField();
    JButton selectButton = new JButton("Decompress");
    selectButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String FileName = textinput.getText();
        new LZWTextDecompressor(FileName);
      }
    });
    mainPanel.add(prompt);
    mainPanel.add(textinput);
    mainPanel.add(selectButton);

    mainPanel.revalidate();
    mainPanel.repaint();
  }
  public textCompressorInterface() {
    super("Text Compressor");
    sidePanel = new JPanel();
    sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
    sidePanel.setBorder(new EmptyBorder(20, 30, 20, 20));
    sidePanel.setBackground(Color.DARK_GRAY);
    sidePanel.setPreferredSize(new Dimension(200, 600));
    mainPanel = new JPanel();
    mainPanel.setBackground(Color.WHITE);
    JLabel message = new JLabel("Text Compressor");
    message.setFont(new Font("Montserrat", Font.BOLD, 10));
    message.setForeground(Color.WHITE);
    message.addMouseListener(new MouseListener() {
      public void mouseClicked(MouseEvent e) {
        compressText();
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
    JLabel history = new JLabel("Text Decommpressor");
    history.setFont(new Font("Montserrat", Font.BOLD, 10));
    history.setForeground(Color.WHITE);
    history.addMouseListener(new MouseListener() {
      public void mouseClicked(MouseEvent e) {
        decompressText();
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
    compressText();
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