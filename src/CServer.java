import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CServer {
    public static void main(String[] args) {
        try {
            try (ServerSocket server = new ServerSocket(8189)) {
                System.out.println("Starting PTB2..." + server.getLocalPort());
                while (true) {
                    Socket soc = server.accept();
                    CServerThread srv = new CServerThread(soc);

                    Thread t = new Thread(srv);
                    Thread.sleep(100);
                    t.start();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
