import messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Administrator on 2014/12/22.
 */
public class ServerThread extends Thread
{
    Socket socket;
    private static ObjectInputStream ois;
    private static ObjectOutputStream oos;
    public ServerThread(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void run()
    {
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            while (true) {
                Object message = ois.readObject();
                crack(message);
            }
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void crack(Object object)
    {
        if(object instanceof Message) {
            onMessage((Message) object);
        }
    }

    public void onMessage(Message message)
    {
        System.out.println(message.getMessage());
        try {
            oos.writeObject(new Message("Server Response"));
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
