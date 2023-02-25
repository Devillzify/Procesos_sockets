package server;

public class ServerSocketStream
{
    public static void main(String[] args)
    {
        UserService userService = new UserService(5000,"");
        userService.iniciarServidor();
    }
}
