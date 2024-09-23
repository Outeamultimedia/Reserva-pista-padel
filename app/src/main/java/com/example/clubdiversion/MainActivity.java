package com.example.clubdiversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.clubdiversion.Utilidades.Utilidades;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Inicio();
    }

    public void Inicio()
    {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BASE_DATOS,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String query ="SELECT  * FROM "+Utilidades.TABLA_SOCIO+" WHERE "+Utilidades. SOCIO_S_A+" = 1";
        Cursor c= Utilidades.Listar_Tabla(db,query);
        c.moveToFirst();
        if(c != null && c.getCount()>0)
        {
            do
            {
                int c0= c.getInt(0);
                String c1= c.getString(1);
                String c2= c.getString(2);
                String c3= c.getString(3);
                String c4= c.getString(4);
                String c5= c.getString(5);
                String c6= c.getString(6);
                Log.e("Salida","id="+c0+"  "+c1+"   "+c2+"  "+c3+"   "+c4+"  "+c5+"  "+c6);
            }while (c.moveToNext());
        }
        else
        {
            Intent intent = new Intent(getApplicationContext(), SocioRegistrar.class);
            startActivity(intent);
        }

        c.close();
        db.close();
        db.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id)
        {
            case R.id.Regisra:
                intent = new Intent(this, Registro.class);
                startActivity(intent);
                break;
            case R.id.Salir:
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Toast.makeText(getApplicationContext(), "Regreso", Toast.LENGTH_SHORT).show();
        Log.e("Salida","Regreso");
        Inicio();
    }

}