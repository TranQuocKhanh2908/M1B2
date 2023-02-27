import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import java.awt.*;
import java.awt.event.*;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.util.Scanner;

public class App extends JFrame {
    private static final long serialVersionUID = 1L;
    private JButton btnSend, btnClear, btnExit;
    private JTextField txta, txtb, txtc, txtkq;

    public App() {
        createUI();
    }

    private void createUI() {
        JPanel pnNorth = new JPanel();
        pnNorth.setLayout(new BorderLayout());
        pnNorth.setBackground(Color.CYAN);
        JLabel lblNorth = new JLabel("Giải Phương Trình Bậc 2");
        pnNorth.add(lblNorth);
        Container con = getContentPane();
        con.add(pnNorth, BorderLayout.NORTH);

        JPanel pnCenter = new JPanel();
        con.add(pnCenter, BorderLayout.CENTER);
        JPanel pnInfor = new JPanel();
        pnInfor.setLayout(new BoxLayout(pnInfor, BoxLayout.Y_AXIS));
        pnCenter.add(pnInfor);

        JPanel pna = new JPanel();
        JLabel lbla = new JLabel("a:");
        txta = new JTextField(15);
        pna.add(lbla);
        pna.add(txta);
        pnInfor.add(pna);
        pnInfor.add(Box.createRigidArea(new Dimension(0, 5)));

        JPanel pnb = new JPanel();
        JLabel lblb = new JLabel("b:");
        txtb = new JTextField(15);
        pnb.add(lblb);
        pnb.add(txtb);
        pnInfor.add(pnb);
        pnInfor.add(Box.createRigidArea(new Dimension(0, 5)));

        JPanel pnc = new JPanel();
        JLabel lblc = new JLabel("c:");
        txtc = new JTextField(15);
        pnc.add(lblc);
        pnc.add(txtc);
        pnInfor.add(pnc);
        pnInfor.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel lblkq = new JLabel("kết quả:");
        lblkq.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        lblkq.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 25));
        txtkq = new JTextField(15);
        txtkq.setEditable(false);
        pnInfor.add(lblkq);
        pnInfor.add(txtkq);
        pnInfor.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel pnButton = new JPanel();
        btnSend = new JButton("Send");
        btnSend.addActionListener(new CMyEvent());
        btnClear = new JButton("Clear");
        btnClear.addActionListener(new CMyEvent());
        btnExit = new JButton("Exit");
        btnExit.addActionListener(new CMyEvent());
        pnButton.add(btnSend);
        pnButton.add(btnClear);
        pnButton.add(btnExit);
        pnInfor.add(pnButton);
    }

    private void proccessInformation() {
        try {
            Socket soc = new Socket("localhost", 8189);
            // Send Information to server
            OutputStream out = soc.getOutputStream();
            PrintWriter printOut = new PrintWriter(out, true);
            printOut.println(Double.parseDouble(txta.getText()));
            printOut.println(Double.parseDouble(txtb.getText()));
            printOut.println(Double.parseDouble(txtc.getText()));

            // Get information from server
            InputStream in=soc.getInputStream();
            try (Scanner sc = new Scanner(in)) {
                txtkq.setText(sc.nextLine());
            }
            soc.close();
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void clearText() {
        txta.setText("");
        txtb.setText("");
        txtc.setText("");
    }

    private class CMyEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            Object o = arg0.getSource();
            if (o.equals(btnSend)) {
                proccessInformation();
            } else if (o.equals(btnClear)) {
                clearText();
            } else if (o.equals(btnExit)) {
                System.exit(0);
            }
        }

    }

    /**
     * w
     * 
     * @param args
     */
    public static void main(String[] args) throws Exception {
        App client = new App();
        client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.setSize(400, 300);
        client.setVisible(true);
    }
}
