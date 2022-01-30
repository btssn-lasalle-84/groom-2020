package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    Spinner listeNoms; // la vue
    List<String> noms; // les données
    ArrayAdapter<String> adapter; // l'adaptateur
    Button boutonCase1;
    Button boutonCase2;
    Button boutonCase3;
    Button boutonCase4;
    Button boutonCase5;
    Button boutonCase6;
    View myView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myView = findViewById(R.id.linearLayout4);
        myView.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            public void onSwipeLeft () {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

        initialiserBoutons();
        initialiserListeDeroulante();

        // On installe le gestionnaire d’écoute lors de la sélection d'une entrée de la liste
        listeNoms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id)
            {
                Toast toast = Toast.makeText(getApplicationContext(), noms.get(position), Toast.LENGTH_SHORT);
                toast.show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {
                // TODO Auto-generated method stub
            }
        });
    }

    private void initialiserListeDeroulante() {
        listeNoms = (Spinner)findViewById(R.id.spinner);

        // Des données à placer dans la liste déroulante
        noms = new ArrayList<String>();
        noms.add("Toto");
        noms.add("Titi");
        noms.add("Tata");

        // On associe les données à l'adaptateur
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, noms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // On associe l'adaptateur à la vue
        listeNoms.setAdapter(adapter);
    }

    private void initialiserBoutons() {
        boutonCase1 = (Button)findViewById(R.id.case1);
        boutonCase1.setOnClickListener(this);

        boutonCase2 = (Button)findViewById(R.id.case2);
        boutonCase2.setOnClickListener(this);

        boutonCase5 = (Button)findViewById(R.id.case5);
        boutonCase5.setOnClickListener(this);

        boutonCase6 = (Button)findViewById(R.id.case6);
        boutonCase6.setOnClickListener(this);
    }

    @Override
    public void onClick(View element)
    {
        Toast toast;
        switch (element.getId())
        {
            case R.id.case1:
                toast = Toast.makeText(getApplicationContext(), "Case 1", Toast.LENGTH_SHORT);
                toast.show();
                break;
            case R.id.case2:
                toast = Toast.makeText(getApplicationContext(), "Case 2", Toast.LENGTH_SHORT);
                toast.show();
                break;
            case R.id.case5:
                toast = Toast.makeText(getApplicationContext(), "Case 5", Toast.LENGTH_SHORT);
                toast.show();
                break;
            case R.id.case6:
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
                break;
        }
    }
}
