import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CheckSum {
    private JButton clickToChooseButton;
    private JPanel myPanel;
    private JLabel jLabel1;
    private JLabel jLabel2;

    private String file = "";

    public CheckSum() {
        clickToChooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                int returnValue = jfc.showOpenDialog(myPanel);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    file = selectedFile.getAbsolutePath();
                    jLabel1.setText(file);
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
                    jLabel2.setText(md5.toString());
                    //System.out.println(md5);
                }
            }
        });
    }

//    private void createUIComponents() {
//        // TODO: place custom component creation code here
//    }

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
