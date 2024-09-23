package com.example.clubdiversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clubdiversion.Utilidades.Utilidades;

public class Instalaciones extends AppCompatActivity {



    LinearLayout layout1;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuinsta, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id)
        {
            case R.id.Item1:
                intent = new Intent(this, Duda.class);
                startActivity(intent);
                break;
            case R.id.Item2:
                intent = new Intent(this, Documento.class);
                startActivity(intent);
                break;
            case R.id.Item3:
                if(Utilidades.SOCIO_ADMINISTRADOR==1)
                {
                    intent = new Intent(this, SocioRegistrar.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(), "Usted no es Administrador", Toast.LENGTH_SHORT).show();

                break;

            case R.id.Item4:
                finish();

                break;
        }

        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instalaciones);




        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Log.e("Salida","Recibido ");

    }


    public void irReserve(View view) {

        Intent intent = new Intent(Instalaciones.this, Reservaciones.class);
        int id = view.getId();
        LinearLayout layout = findViewById(id);

        String LAYOUT =  layout.getTransitionName();
        String Num = LAYOUT.substring(8);

        int num = Integer.parseInt(Num);
        TextView txt= findViewById(R.id.txtDesespacio1);
        String[] capacidad = {"","40","55","60","7","14","19","12","26","7"};
        String[] techado= {"","SI","SI","SI","SI","NO","NO","NO","NO","NO"};
        String[] area= {"","80","150","220","40","45","80","50","30","40"};

        switch (num)
        {
            case 1:
                txt = findViewById(R.id.txtDesespacio1);
                break;
            case 2:
                txt = findViewById(R.id.txtDesespacio2);
                break;
            case 3:
                txt = findViewById(R.id.txtDesespacio3);
                break;
            case 4:
                txt = findViewById(R.id.txtDesespacio4);
                break;
            case 5:
                txt = findViewById(R.id.txtDesespacio5);
                break;
            case 6:
                txt = findViewById(R.id.txtDesespacio6);
                break;
            case 7:
                txt = findViewById(R.id.txtDesespacio7);
                break;
            case 8:
                txt = findViewById(R.id.txtDesespacio8);
                break;
            case 9:
                txt = findViewById(R.id.txtDesespacio9);
                break;
        }

        Log.e("Salida","Numero = "+num);
        intent.putExtra("Imagen",num);
        intent.putExtra("Espacio",txt.getText().toString());
        intent.putExtra("Socio","");
        intent.putExtra("capacidad",capacidad[num]);
        intent.putExtra("techado",techado[num]);
        intent.putExtra("area",area[num]);


        startActivity(intent);



    }
}