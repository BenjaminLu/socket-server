import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    public void runServer(int port)
    {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                Thread serverThread = new Thread(new ServerThread(socket));
                serverThread.setDaemon(true);
                serverThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
