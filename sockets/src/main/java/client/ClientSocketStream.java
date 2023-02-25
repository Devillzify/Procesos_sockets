package client;

public class ClientSocketStream
{
    public static void main(String[] args)
    {
        ClientService clientService = new ClientService("127.0.0.1",5000,"");
        clientService.startClient();
    }
}