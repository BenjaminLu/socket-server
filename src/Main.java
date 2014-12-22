/**
 * Created by Administrator on 2014/12/22.
 */
public class Main
{
    private static final int SERVER_PORT = 5443;
    public static void main(String[] args)
    {
        new Server().runServer(SERVER_PORT);
    }
}
