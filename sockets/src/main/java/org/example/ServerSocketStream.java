package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketStream
{
    static boolean exit = false;

    public static void main(String[] args)
    {
        final int bindPort = 6666;
        boolean exit = false;

        try{
            ServerSocket serverSocket = new ServerSocket(bindPort);
            Socket server = serverSocket.accept();

            while (!exit){
                System.out.println("Conexion recibida");
                InputStream is = server.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader bf = new BufferedReader(isr);
                String linea = bf.readLine();
                if("hola".equals(linea)){
                    OutputStream os = server.getOutputStream();
                    PrintWriter pw = new PrintWriter(os);
                    pw.write(linea+" sent by server "+"\n");
                    pw.flush();
                }
                else{
                    OutputStream os = server.getOutputStream();
                    PrintWriter pw = new PrintWriter(os);
                    pw.write("nadie te ha preguntado eso");
                    pw.flush();
                }
            }
        }
        catch (IOException e){
            System.out.println("hola");
        }
    }
}
