package SocketExample;

import com.google.gson.Gson;

import java.io.*;
import java.net.*;

import static java.lang.Thread.sleep;

public class ClientSocketStream
{
    public static void main(String[] args)
    {
        final String HOST = "127.0.0.1";
        final int PUERTO = 5000;
        DataInputStream in;
        DataOutputStream out;
        ScannerUtils scan = new ScannerUtils();
        String opcionBBDD = "";

        try
        {
            while (true)
            {
                Socket cliente = new Socket(HOST, PUERTO);
                ObjectOutputStream obj = new ObjectOutputStream(cliente.getOutputStream());
                ObjectInputStream inp = new ObjectInputStream(cliente.getInputStream());
                System.out.println("1: Insert new user\n2: Select user\n3:Delete user\n0: Exit");
                System.out.println("Introduzca su opcion:...");
                int opcion = scan.pedirNumero();

                switch (opcion)
                {
                    case 0:
                        User salirUser = new User("Exit");
                        obj.writeObject(salirUser);
                        obj.close();
                        cliente.close();
                        System.out.println("Comunicacion finalizada. Tu informacion a sido enviada a google para que sus datos sean tratados y usados para analisis y controlar el mundo.\nQue tenga un buen dia.");
                        System.exit(0);
                    break;

                    case 1:
                        System.out.println("Ha seleccionado Insertar nuevo Usuario:");
                        opcionBBDD = "Insert";
                        String id = scan.pedirMensaje("Introduzca el id del usuario");
                        String nombre = scan.pedirMensaje("Introduzca el nombre del usuario");
                        String apellidos = scan.pedirMensaje("Introduzca los apellidos del usuario");
                        User nuevoUser = new User(opcionBBDD,id,nombre,apellidos);
                        obj.writeObject(nuevoUser);
                        User usr = (User)inp.readObject();
                        System.out.println(usr.opcionBBDD);
                        break;

                    case 2:
                        System.out.println("Ha seleccionado la opcion Seleccionar por id");
                        opcionBBDD = "Select";
                        String id_select = scan.pedirMensaje("Introduzca la id a revisar");
                        User pedirUser = new User(opcionBBDD,id_select);
                        obj.writeObject(pedirUser);
                        User resposta = (User)inp.readObject();
                        System.out.println(resposta.opcionBBDD);
                        break;

                    case 3:
                        System.out.println("Ha seleccionado la opcion de borrar por id");
                        opcionBBDD = "Delete";
                        String id_sel = scan.pedirMensaje("Introduzca la id a borrar");
                        User borrarUser = new User(opcionBBDD,id_sel);
                        obj.writeObject(borrarUser);
                        User respostaDelete = (User)inp.readObject();
                        System.out.println(respostaDelete.opcionBBDD);
                        break;
                }
            }
        }
        catch (IOException | ClassNotFoundException ex)
        {
            System.out.println(ex);
        }
    }

}