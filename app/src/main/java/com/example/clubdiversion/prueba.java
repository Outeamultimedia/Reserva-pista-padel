package com.example.clubdiversion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class prueba extends AppCompatActivity {

    Spinner spiPrueba;
    List<String> Contenido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
        spiPrueba = findViewById(R.id.spiPrueba);

        Contenido = new ArrayList<>();
        Contenido.add("Seleccione PDF");
        Contenido.add("Julio2");
        Contenido.add("Julio3");
        Contenido.add("Julio4");

        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, R.layout.spinner_doc, Contenido );
        spiPrueba.setAdapter(adapter);
        spiPrueba.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(i>0)
                    Log.e("Salida",spiPrueba.getItemAtPosition(i).toString());
                else
                    Log.e("Salida","No valido");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );


    }
}