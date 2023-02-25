package client;

import common.ScannerUtils;
import common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientService
{
//host = "127.0.0.1" puerto = 5000
   private String host;
   private int puerto;
   private ScannerUtils scan = new ScannerUtils();
   private String opcionBBDD = "";

    public ClientService()
    {

    }

    public ClientService(String host, int puerto, String opcionBBDD)
    {
        this.host = host;
        this.puerto = puerto;
        this.opcionBBDD = opcionBBDD;
    }

    public void startClient()
    {
        try
        {
            while (true)
            {
                Socket cliente = new Socket(host, puerto);
                ObjectOutputStream obj = new ObjectOutputStream(cliente.getOutputStream());
                ObjectInputStream inp = new ObjectInputStream(cliente.getInputStream());
                int opcion = scan.pedirNumero("1: Insert new user\n2: Select user\n3:Delete user\n0: Exit\nIntroduzca su opcion:...");

                switch (opcion)
                {
                    case 0:
                        User salirUser = new User("Exit");
                        obj.writeObject(salirUser);
                        obj.close();
                        inp.close();
                        cliente.close();
                        System.out.println("" + "\n");
                        System.out.println("Comunicacion finalizada. Tu informacion a sido enviada a google para que sus datos sean tratados y usados para analisis y controlar el mundo.\nQue tenga un buen dia.");
                        System.exit(0);
                        break;

                    case 1:
                        System.out.println("Ha seleccionado Insertar nuevo Usuario:");
                        opcionBBDD = "Insert";
                        obj.writeObject(crearUser(opcionBBDD,scan));
                        User usr = (User)inp.readObject();
                        System.out.println(usr.getOpcionBBDD());
                        break;

                    case 2:
                        System.out.println("Ha seleccionado la opcion Seleccionar por id");
                        opcionBBDD = "Select";
                        obj.writeObject(select(opcionBBDD,scan));
                        User resposta = (User)inp.readObject();
                        comprobarRespuesta(resposta);
                        break;

                    case 3:
                        System.out.println("Ha seleccionado la opcion de borrar por id");
                        opcionBBDD = "Delete";
                        obj.writeObject(borrarUser(opcionBBDD,scan));
                        User respostaDelete = (User)inp.readObject();
                        System.out.println(respostaDelete.getOpcionBBDD());
                        break;
                }
            }
        }
        catch (IOException | ClassNotFoundException ex)
        {
            System.out.println(ex);
        }
    }

    public static User crearUser(String opcion, ScannerUtils scan)
    {
        String id = scan.pedirMensaje("Introduzca el id del usuario");
        String nombre = scan.pedirMensaje("Introduzca el nombre del usuario");
        String apellidos = scan.pedirMensaje("Introduzca los apellidos del usuario");
        User nuevoUser = new User(opcion,id,nombre,apellidos);
        return nuevoUser;
    }

    public static User select(String opcion, ScannerUtils scan)
    {
        String id_select = scan.pedirMensaje("Introduzca la id a revisar");
        User selectUser = new User(opcion,id_select);
        return selectUser;
    }

    public static void comprobarRespuesta(User user)
    {
        if(user.getId() == null)
        {
            System.out.println(user.getOpcionBBDD());
        }
        else{
            System.out.println(user);
        }
    }

    public static User borrarUser(String opcion, ScannerUtils scan)
    {
        String id_sel = scan.pedirMensaje("Introduzca la id a borrar");
        User borrarUser = new User(opcion,id_sel);
        return borrarUser;
    }

}
