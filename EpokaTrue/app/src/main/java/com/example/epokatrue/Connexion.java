package com.example.epokatrue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Connexion extends AppCompatActivity {

    private EditText matricule;

    public static int idEmploye;
    private EditText motDePasse;
    private String urlServiceWeb;
    private String resultat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        matricule = findViewById(R.id.etReference);
        motDePasse = findViewById(R.id.etMotDePasse);

    }

    public void connexion(View button){

        idEmploye =  Integer.parseInt(matricule.getText().toString());
        urlServiceWeb = "http://172.16.47.19/epoka/authentifier.php?num=" + idEmploye + "&motdepasse=" + motDePasse.getText().toString();
        resultat = getServerDataTexteBrut(urlServiceWeb);
        String motclef = resultat.split(" ")[0];

        if(motclef.equals("Bonjour")) {

            Intent intent = new Intent(getApplicationContext(), Menu_Choix.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        }else{

            Toast.makeText(this, "Identifiant ou mot de passe incorrect " , Toast.LENGTH_LONG).show();
        }
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


        }catch (Exception e){

            Toast.makeText(this, "Erreur récupération données : " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

        }

        return ch;
    }
}