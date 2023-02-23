package SocketExample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerSocketStream
{
    public static void main(String[] args)
    {
        ServerSocket servidor = null;
        Socket cliente = null;
        final int PUERTO = 5000;
        ObjectInputStream in;
        ObjectOutputStream out;
        Gson gson = new Gson();
        List<User> listaUsers = new ArrayList<>();
        User encontrado = new User("");

        try{
            servidor = new ServerSocket(PUERTO);
            System.out.println("Servidor iniciado");
            while(true)
            {
                cliente = servidor.accept();
                System.out.println("Esperando operacion");
                FileWriter fw = new FileWriter("BBDD.txt",true);
                PrintWriter pw = new PrintWriter(fw);
                in = new ObjectInputStream(cliente.getInputStream());
                out = new ObjectOutputStream(cliente.getOutputStream());
                User usuario = (User)in.readObject();
                String opcion = usuario.opcionBBDD;
                FileReader fl = new FileReader("BBDD.txt");
                BufferedReader br = new BufferedReader(fl);

                String leer;
                while ((leer = br.readLine()) != null)
                {
                    User user = gson.fromJson(leer, User.class);
                    listaUsers.add(user);
                }
                switch (opcion)
                {
                    case "Insert":
                        boolean existe = false;
                        System.out.println("es un insert");
                        for(User s: listaUsers)
                        {
                            if(usuario.id.equals(s.id))
                            {
                               existe = true;
                            }
                        }
                        if(!existe)
                        {
                            if(usuario.id != null && usuario.nom != null && usuario.cognom != null)
                            {
                                User inserUser = new User(usuario.id, usuario.nom,usuario.cognom);
                                pw.write(gson.toJson(inserUser) + "\n");
                                User usr = new User("El usuario ha sido insertado correctamente");
                                out.writeObject(usr);
                                out.close();
                                pw.close();
                            }
                        }
                        else{
                            User userexist = new User("Esta id ya ha sido utilizada, no se ha podido insertar");
                            out.writeObject(userexist);
                            out.close();
                        }
                        pw.close();
                        break;

                    case "Select":
                        System.out.println("es un select");
                        boolean existeSelect = false;
                        for(User s: listaUsers)
                        {
                            if(usuario.id.equals(s.id))
                            {
                               encontrado = new User(s.id,s.nom,s.cognom);
                                System.out.println(encontrado.toString());
                                existeSelect = true;
                            }
                        }
                        if(!existeSelect)
                        {
                            encontrado = new User("No se han encontrado usuarios con esa id");
                        }
                        out.writeObject(encontrado);
                        out.close();
                        break;
                    case "Exit":
                        System.out.println("Cerrando Servidor.");
                        System.exit(0);
                        break;

                    case "Delete":
                        fw = new FileWriter("BBDD.txt",false);
                        System.out.println("esto es un delete");
                        for(int i = 0;i < listaUsers.size(); i++)
                        {
                            if(usuario.id.equals(listaUsers.get(i).id))
                            {
                                listaUsers.remove(listaUsers.get(i));
                            }
                        }
                        for(User s: listaUsers)
                        {
                            pw.write(gson.toJson(s) + "\n");
                        }
                        User ready = new User("Elemento eliminado");
                        out.writeObject(ready);
                        out.close();
                        break;

                }
                cliente.close();
            }
        }
        catch (IOException | ClassNotFoundException ex)
        {
            System.out.println(ex);
        }
    }
}
