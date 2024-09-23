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
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.clubdiversion.Utilidades.Utilidades;

public class Registro extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_regresar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.Salir1:
                finish();
                break;
        }
        return true;
    }


    private EditText editSocioRegistro;
    private EditText editPasswordRegistro;
    private int TipoUsuario = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        editSocioRegistro = findViewById(R.id.editSocioRegistro);
        editPasswordRegistro = findViewById(R.id.editPasswordRegistro);
        editSocioRegistro.requestFocus();


    }



    public void Login(View view) {
        String SOCIO = editSocioRegistro.getText().toString().trim();
        String PASS  = editPasswordRegistro.getText().toString().trim();
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BASE_DATOS,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String query ="SELECT "+Utilidades.SOCIO_NOMBRE+", "+Utilidades.SOCIO_S_A+", " +
                Utilidades.SOCIO_ID+" FROM "+Utilidades.TABLA_SOCIO +" " +
                "WHERE "+Utilidades.SOCIO_NIP+" ='"+SOCIO+"' and " +
                ""+Utilidades.SOCIO_PASS+" = '"+PASS+"'";
        Log.e("Salida",query);
        Cursor c = Utilidades.BuscaSocio(db, query);
        c.moveToFirst();
        if(c != null && c.getCount()>0)
        {
            Log.e("Salida","aceptado "+c.getString(0)+"  "+c.getString(1)+"  "+c.getString(2));
            Utilidades.NOMBRE_SOCIO = c.getString(0);
            Utilidades.SOCIO_ADMINISTRADOR = c.getInt(1);
            Utilidades.SO_AD_ID = c.getInt(1);

            Intent intent = new Intent(this, Instalaciones.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Toast.makeText(this, "Socio no Registrado", Toast.LENGTH_SHORT).show();
        }
        db.close();


    }
/*
    public void sdfsf(View view)
    {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BASE_DATOS,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String query ="SELECT  * FROM "+Utilidades.TABLA_SOCIO+" WHERE 1";
        Cursor c= Utilidades.Listar_Tabla(db,query);

        c.moveToFirst();
        if(c != null && c.getCount()>0)
            do
            {
                int c0= c.getInt(0);
                String c1= c.getString(1);
                String c2= c.getString(2);
                String c3= c.getString(3);
                String c4= c.getString(4);
                String c5= c.getString(5);
                String c6= c.getString(6);
                Log.e("Salida","id="+c0+"  "+c1+"   "+c2+"  "+c3+"   "+c4+"  "+c5+"  "+c6+"  ");

            }while (c.moveToNext());
        else
            Log.e("Salida","Vacio");
        c.close();
        db.close();
    }


*/

}