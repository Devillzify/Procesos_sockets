package common;

import java.io.Serializable;

public class User implements Serializable
{
   private String opcionBBDD;
   private String id;
   private String nom;
   private String cognom;

    public User(String opcionBBDD,String id, String nom, String cognom)
    {
        this.opcionBBDD = opcionBBDD;
        this.id = id;
        this.nom = nom;
        this.cognom = cognom;
    }

    public User(String id, String nom, String cognom)
    {
        this.id = id;
        this.nom = nom;
        this.cognom = cognom;
    }

    public User(String opcionBBDD, String id)
    {
        this.opcionBBDD = opcionBBDD;
        this.id = id;
    }

    public User(String opcionBBDD)
    {
        this.opcionBBDD = opcionBBDD;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getNom()
    {
        return nom;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public String getCognom()
    {
        return cognom;
    }

    public String getOpcionBBDD()
    {
        return opcionBBDD;
    }

    public void setOpcionBBDD(String opcionBBDD)
    {
        this.opcionBBDD = opcionBBDD;
    }

    public void setCognom(String cognom)
    {
        this.cognom = cognom;
    }

    @Override
    public String toString()
    {
        return "User: " +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", cognom='" + cognom + '\'';
    }
}
