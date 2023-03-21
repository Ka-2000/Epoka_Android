package com.example.epokatrue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;

public class Menu_Choix extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuchoix);
    }

    public void NextPage (View Button) {
        Intent intent = new Intent(getApplicationContext(), Missions_Ajouter.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void PreviousPage (View Button) {
        Intent intent = new Intent(getApplicationContext(), Connexion.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
