import messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Administrator on 2014/12/22.
 */
public class ServerThread implements Runnable
{
    Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
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
        } else if(object instanceof String) {
            onMessage((String) object);
        }
    }

    public void onMessage(Message message)
    {
        System.out.println(message.getMessage());
        try {
            oos.writeObject(new Message("Server Response"));
            oos.flush();
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void onMessage(String message)
    {
        System.out.println(message);
        try {
            oos.writeObject(message);
            oos.flush();
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
