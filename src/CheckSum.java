import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CheckSum {
    private JButton clickToChooseButton;
    private JPanel myPanel;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JTextField textField1;
    private JButton verifyButton;
    private JButton copyToClipboardButton;

    private String file = "";

    public CheckSum() {
        textField1.setForeground(Color.GRAY);
        clickToChooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                int returnValue = jfc.showOpenDialog(myPanel);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    file = selectedFile.getAbsolutePath();
                    File f = new File(file);
                    jLabel1.setText(f.getName());
                    MessageDigest mdEnc = null;
                    try {
                        mdEnc = MessageDigest.getInstance("MD5");
                    } catch (NoSuchAlgorithmException ex) {
                        System.out.println("Exception while encrypting to md5");
                        ex.printStackTrace();
                    } // Encryption algorithm
                    mdEnc.update(file.getBytes(), 0, file.length());
                    String md5 = new BigInteger(1, mdEnc.digest()).toString(16);
                    while ( md5.length() < 32 ) {
                        md5 = "0"+md5;
                    }
                    jLabel2.setText(md5);
                }
            }
        });
        copyToClipboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection stringSelection = new StringSelection(jLabel2.getText());
                clipboard.setContents(stringSelection, null);
            }
        });
        textField1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField1.getText().equals("Enter here the correct value to check it")) {
                    textField1.setText("");
                    textField1.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField1.getText().isEmpty()) {
                    textField1.setForeground(Color.GRAY);
                    textField1.setText("Enter here the correct value to check it");
                }
            }
        });
        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String generated = jLabel2.getText();
                String correct = textField1.getText();
                if (generated.compareTo(correct) == 0) {
                    JOptionPane.showMessageDialog(null,
                            "The file is not corrupt.",
                            "Yey",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "The file is corrupt.",
                            "Oh no...",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }


    public static void main(String[] args) {
        JFrame jFrame = new JFrame("CheckSum");
        jFrame.setSize(new Dimension(500,500));
        jFrame.setMinimumSize(new Dimension(500,500));
        jFrame.setPreferredSize(new Dimension(500,500));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setLocation(dim.width/2-jFrame.getSize().width/2, dim.height/2-jFrame.getSize().height/2);
        jFrame.setContentPane(new CheckSum().myPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }

}
