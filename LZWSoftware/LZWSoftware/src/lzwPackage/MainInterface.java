package lzwPackage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainInterface extends JFrame {

  public MainInterface() {
    super("Compression Interface");
    setSize(300, 150);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel panel = new JPanel();
    add(panel);
    placeComponents(panel);

    setVisible(true);
  }

  private void placeComponents(JPanel panel) {
    panel.setLayout(null);

    JLabel titleLabel = new JLabel("Choose Compression Type");
    titleLabel.setBounds(80, 10, 200, 25);
    panel.add(titleLabel);

    JButton stringButton = new JButton("Compress String");
    stringButton.setBounds(80, 40, 150, 25);
    panel.add(stringButton);

    JButton imageButton = new JButton("Compress Image");
    imageButton.setBounds(80, 75, 150, 25);
    panel.add(imageButton);

    stringButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        new textCompressorInterface();
      }
    });

    imageButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        new imageCompressorInterface();
      }
    });
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new MainInterface();
      }
    });
  }
}