package server;

import common.User;
import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class UserService
{
    private ServerSocket servidor = null;
    private Socket cliente = null;
    private int puerto;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private FileWriter fw;
    private PrintWriter pw;
    private Gson gson = new Gson();
    private User encontrado;
    private List<User> listaUsers;

    public UserService()
    {

    }

    public UserService(int port, String opcionBBDD)
    {
        this.puerto = port;
        this.encontrado = new User(opcionBBDD);
    }

    private List<User> cargarUsuarios(Gson gson)
    {
        List<User> users = new ArrayList<>();
        try
        {
            FileReader fl = new FileReader("BBDD.txt");
            BufferedReader br = new BufferedReader(fl);
            String leer;
            while ((leer = br.readLine()) != null)
            {
                User user = gson.fromJson(leer, User.class);
                users.add(user);
            }
        } catch (IOException ex)
        {
            System.out.println(ex);
        }
        for (User s : users)
        {
            System.out.println(s);
        }
        return users;
    }

    private User comprobarID(List<User> listaUsers, User usuario, Gson gson)
    {
        boolean existe = false;
        User finalizado = new User("");
        try
        {
            FileWriter fw = new FileWriter("BBDD.txt", true);
            PrintWriter pw = new PrintWriter(fw);
            for (User s : listaUsers)
            {
                if(usuario.getId().equals(s.getId()))
                {
                    existe = true;
                }
            }
            if (!existe)
            {
                if (usuario.getId() != null && usuario.getNom() != null && usuario.getCognom() != null)
                {
                    finalizado = new User(usuario.getId(), usuario.getNom(), usuario.getCognom());
                    pw.write(gson.toJson(finalizado) + "\n");
                    finalizado = new User("El usuario ha sido insertado correctamente");
                    pw.close();
                }
            } else
            {
                finalizado = new User("Esta id ya ha sido utilizada, no se ha podido insertar");
            }
        } catch (IOException ex)
        {
            System.out.println(ex);
        }
        return finalizado;
    }

    public void iniciarServidor()
    {
        try
        {
            servidor = new ServerSocket(this.puerto);
            System.out.println("Servidor iniciado");
            while (true)
            {
                cliente = servidor.accept();
                System.out.println("Esperando operacion");
                in = new ObjectInputStream(cliente.getInputStream());
                out = new ObjectOutputStream(cliente.getOutputStream());
                User usuario = (User)in.readObject();
                String opcion = usuario.getOpcionBBDD();
                listaUsers = cargarUsuarios(gson);

                switch (opcion)
                {
                    case "Insert":
                        System.out.println("es un insert");
                        out.writeObject(comprobarID(listaUsers, usuario, gson));
                        break;

                    case "Select":
                        System.out.println("es un select");
                        boolean existeSelect = false;
                        for (User s : listaUsers)
                        {
                            if (s.getId().equalsIgnoreCase(usuario.getId()))
                            {
                                encontrado = s;
                                System.out.println(encontrado);
                                existeSelect = true;
                            }
                        }
                        if (!existeSelect)
                        {
                            encontrado = new User("No se han encontrado usuarios con esa id");
                            System.out.println(encontrado);
                        }
                        out.writeObject(encontrado);
                        out.close();
                        break;

                    case "Exit":
                        System.out.println("Cerrando Servidor.");
                        System.exit(0);
                        break;

                    case "Delete":
                        boolean enc = false;
                        User ready = new User("");
                        fw = new FileWriter("BBDD.txt", false);
                        pw = new PrintWriter(fw);
                        System.out.println("esto es un delete");
                        for (int i = 0; i < listaUsers.size(); i++)
                        {
                            if (usuario.getId().equals(listaUsers.get(i).getId()))
                            {
                                listaUsers.remove(listaUsers.get(i));
                                ready = new User("El usuario ha sido eliminado");
                                enc = true;
                            }
                        }
                        for (User s : listaUsers)
                        {
                            pw.write(gson.toJson(s) + "\n");
                        }

                        if (!enc)
                        {
                            ready = new User("No hay usuarios con esa id");
                        }
                        out.writeObject(ready);
                        out.close();
                        fw.close();
                        pw.close();
                        break;
                }
                cliente.close();
            }
        } catch (IOException | ClassNotFoundException ex)
        {
            System.out.println(ex);
        }
    }
}
