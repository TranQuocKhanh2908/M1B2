import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CServerThread implements Runnable {
    
    private Socket m_soc;
    public CServerThread (Socket soc) {
        m_soc = soc;
    }

    private String processInfomation(double a, double b, double c) {
        String sKQ = "";
        if (a == 0) {
            if (b == 0) {
                if (c == 0)
                    sKQ = "Phương Trình có Vô số nghiệm";
                else
                    sKQ = "Phương trình vô nghịêm";
            } else {
                String sFormat = String.format("%10.2f", -c / b);
                sKQ = "Pt có 1 no x=" + sFormat;
            }
        } else {
            double delta = Math.pow(b, 2) - 4 * a * c;
            if (delta < 0) {
                sKQ = "Phương trình vô nghiệm";
            } else if (delta == 0) {
                String sFormat = String.format("%10.2f", -b / (2 * a));
                sKQ = "Phương trình có nghiệm kép x1=x2=" + sFormat;
            } else {
                double x1 = (-b - Math.sqrt(delta)) / (2 * a);
                double x2 = (-b + Math.sqrt(delta)) / (2 * a);
                String sFormat1 = String.format("%10.2f", x1);
                String sFormat2 = String.format("%10.2f", x2);
                sKQ = "phương trình có 2 nghiệm x1=" + sFormat1 + " ; x2=" + sFormat2;
            }
        }
        return sKQ;
    }

    @Override
    public void run() {
        try {
            InputStream in = m_soc.getInputStream();
            try (Scanner sc = new Scanner(in)) {
                double a = sc.nextDouble();
                double b = sc.nextDouble();
                double c = sc.nextDouble();
                String sKq = processInfomation(a, b, c);

                OutputStream out = m_soc.getOutputStream();
                PrintWriter printOut = new PrintWriter(out, true);
                printOut.println(sKq);
            }
            m_soc.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
