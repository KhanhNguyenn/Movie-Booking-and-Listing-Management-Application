
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.util.Scanner;


/**
* QR class support creating QR code for receipt of booking transactions for customer.
*
* @author SS3-grp6
*/
public class QR {
    /**
    * Make QR code for the CustomerTransaction record.
    *
    * @param cust-CustomerTransaction
    */
    public void make_QR(CustomerTransaction cust)
    {
        try {
            String filePath = "qr_code.png";
            String charset = "UTF-8"; // or "ISO-8859-1"
            Map < EncodeHintType, ErrorCorrectionLevel > hintMap = new HashMap < EncodeHintType, ErrorCorrectionLevel > ();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            BitMatrix matrix = new MultiFormatWriter().encode(
                    new String(cust.getTID().getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, 200, 200, hintMap);
            MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
                    .lastIndexOf('.') + 1), new File(filePath));
            System.out.println("Digital receipt generated successfully. Please present this as your ticket.");
            disp_QR(cust);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
    * Display QR code for the CustomerTransaction record.
    *
    * @param cust-CustomerTransaction
    */
    public void disp_QR(CustomerTransaction cust)
    {
        JFrame f = new JFrame("QR Code"); //creates jframe f
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //this is your screen size
        ImageIcon image = new ImageIcon("qr_code.png"); //imports the image
        JLabel lbl = new JLabel(image); //puts the image into a jlabel

        lbl.setText("<html>" + cust.getShowLocation() + "<br>" + cust.printableDateTime()+"<br>Seats: " + cust.getSeats() + "</html>");
        lbl.setHorizontalTextPosition(JLabel.CENTER);
        lbl.setVerticalTextPosition(JLabel.BOTTOM);

        f.getContentPane().add(lbl); //puts label inside the jframe
        f.setSize(image.getIconWidth()+50, image.getIconHeight() + 100); //gets h and w of image and sets jframe to the size
        int x = (screenSize.width - f.getSize().width)/2; //These two lines are the dimensions
        int y = (screenSize.height - f.getSize().height)/2;//of the center of the screen
        f.setLocation(x, y); //sets the location of the jframe
        f.setVisible(true); //makes the jframe visible
        /*
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        f.setVisible(false);
        f.dispose();

         */
    }
}
