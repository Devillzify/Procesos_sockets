package org.example;
import java.io.*;
import java.net.*;

public class ClientSocketStream
{
   public static  BufferedReader getStream(InputStream is){
       InputStreamReader isr = new InputStreamReader(is);
       BufferedReader bfr = new BufferedReader(isr);
       return bfr;
   }

    public static void main(String[] args)
    {
        String destino = "localhost";
        int puertoDestino = 6666;
        Socket socket = new Socket();
        InetSocketAddress direccion = new InetSocketAddress(destino,puertoDestino);
        try{
            socket.connect(direccion);
            while (true){
                System.out.println("Client text:");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String txt = reader.readLine();
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                pw.print(txt + "\n");
                pw.flush();
                BufferedReader bfr = ClientSocketStream.getStream(socket.getInputStream());
                System.out.println("el resultado fue: " + bfr.readLine());
                //socket.close();
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.err.println("error del  client");
        }
    }
}