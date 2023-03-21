package com.example.epokatrue;

public class Commune {
    public int id;
    public String nom;
    public String cp;

    public Commune (int unId, String unNom, String unCP) {
        id = unId;
        nom = unNom;
        cp = unCP;
    }

    @Override
    public String toString(){
        return nom + " (" + cp +")";

    }
}
