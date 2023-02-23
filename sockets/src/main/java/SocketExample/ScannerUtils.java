package SocketExample;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ScannerUtils
{
    Scanner sc;

    ScannerUtils(){
        sc = new Scanner(System.in);
    }

    public int pedirNumero() {
        String numero = "";
        boolean validar = false;
        while (!validar) {
            try {
                numero = sc.nextLine();
                if (Integer.parseInt(numero) >= 0 && Integer.parseInt(numero) < 4) {
                    validar = true;
                } else {
                    System.out.println("Introduce una opcion valida");
                }
            } catch (NumberFormatException e) {
                System.out.println("numero introducido no correcto");
                validar = false;
            }
        }
        return Integer.parseInt(numero);
    }

    public String pedirMensaje(String msj)
    {
        System.out.println(msj);
        try
        {
            msj = sc.nextLine();
        }
        catch (Exception e)
        {
            System.out.println("EL campo no debe estar vacio");
        }

        return msj;
    }
}
