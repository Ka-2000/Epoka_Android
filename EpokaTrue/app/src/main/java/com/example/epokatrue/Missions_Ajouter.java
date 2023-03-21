package com.example.epokatrue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Missions_Ajouter extends Activity {

    EditText etDebut;
    EditText etFin;
    Spinner sLieu;
    ArrayList<Commune> lesCommunes;

    private String urlServiceWeb2;
    private String resultat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter);

        etDebut = findViewById(R.id.etDebut);
        etFin =  findViewById(R.id.etFin);
        sLieu = findViewById(R.id.sLieu);

        lesCommunes = new ArrayList<Commune>();

        setSpinnerCommunes();

    }

    private void setSpinnerCommunes() {
        String urlServiceWeb = "http://172.16.47.19/epoka/getLesCommunes.php";
        getServerDataJSON(urlServiceWeb);

        ArrayAdapter<Commune> adapter = new ArrayAdapter<Commune>(this, android.R.layout.simple_spinner_item, lesCommunes);
        sLieu.setAdapter(adapter);
    }

    private void getServerDataJSON(String urlServiceWeb) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        InputStream is = null;
        String ch = "";
        lesCommunes.clear();

        try {

            URL url = new URL(urlServiceWeb);
            HttpURLConnection connexion = (HttpURLConnection) url.openConnection();
            connexion.connect();
            is = connexion.getInputStream();

            String ligne;

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            while((ligne = br.readLine()) != null){

                ch = ch + ligne + "\n";
            }


            JSONArray jsonArray = new JSONArray(ch);
            ch = "";
            for ( int i =0; i<jsonArray.length(); i++) {
                JSONObject jsonObjet = jsonArray.getJSONObject(i);
                String nom = jsonObjet.getString("comNom");
                String cp = jsonObjet.getString("comCP");
                int id = jsonObjet.getInt("comId");
                Commune commune = new Commune(id,nom,cp);
                lesCommunes.add(commune);
            }


        }catch (Exception e){

            Toast.makeText(this, "Erreur récupération données : " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

        }
    }

    public void addMission(View button){
        String debut = etDebut.getText().toString();
        String fin = etFin.getText().toString();
        int idCommune = ((Commune) sLieu.getSelectedItem()).id;
        urlServiceWeb2 = "http://172.16.47.19/epoka/InsertMission.php?dateDebut=" + debut + "&dateFin=" + fin + "&idCommune=" + idCommune + "&idEmploye=" + Connexion.idEmploye;;
        resultat = getServerDataTexteBrut(urlServiceWeb2);

    }


    private String getServerDataTexteBrut(String urlServiceWeb) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        InputStream is = null;
        String ch = "";

        try {

            URL url = new URL(urlServiceWeb);
            HttpURLConnection connexion = (HttpURLConnection) url.openConnection();
            connexion.connect();
            is = connexion.getInputStream();

            String ligne;

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            while((ligne = br.readLine()) != null){

                ch = ch + ligne;
            }
            Toast.makeText(this, "Envoi vers la Base de Donnée effectué !" , Toast.LENGTH_LONG).show();

        }catch (Exception e){

            Toast.makeText(this, "Erreur d'envoi à la Base de Donnée", Toast.LENGTH_LONG).show();

        }

        return ch;
    }

}
