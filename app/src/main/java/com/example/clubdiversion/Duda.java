package com.example.clubdiversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clubdiversion.Utilidades.Utilidades;

import java.util.ArrayList;
import java.util.List;

public class Duda extends AppCompatActivity {

    Spinner spiDuda;
    List<String> Contenido;
    List<String> Links;
    ArrayAdapter<String> adapter;
    TextView txtSocioDuda;
    EditText editTextoDuda;
    EditText editTextoRespDuda;
    Button btnRespDuda;
    Button btnLimpiar;
    Button btnaceptarDuda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duda);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        txtSocioDuda = findViewById(R.id.txtSocioDuda);
        editTextoDuda = findViewById(R.id.editTextoDuda);
        editTextoRespDuda = findViewById(R.id.editTextoRespDuda);

        btnaceptarDuda = findViewById(R.id.btnaceptarDuda);
        btnRespDuda = findViewById(R.id.btnRespDuda);
        String so="";
        if(Utilidades.SOCIO_ADMINISTRADOR==1)
            so="Administrador";
        else
            so="Socio";
        txtSocioDuda.setText(so+": "+Utilidades.NOMBRE_SOCIO);


        Log.e("Salida","socadm"+Utilidades.SOCIO_ADMINISTRADOR);
        if(Utilidades.SOCIO_ADMINISTRADOR==0)
        {
            editTextoRespDuda.setEnabled(false);
            editTextoRespDuda.setText("Por responder");
            btnRespDuda.setVisibility(View.INVISIBLE);
        }

        spiDuda = findViewById(R.id.spiDuda);

        btnRespDuda.setEnabled(false);
        Limpiarcampos();


        editTextoDuda.requestFocus();
        editTextoRespDuda.setEnabled(false);
    }
    private void Limpiarcampos()
    {
        editTextoRespDuda.setText("");
        editTextoDuda.setText("");
        Lista();
        editTextoDuda.setEnabled(true);
        editTextoDuda.requestFocus();
        editTextoRespDuda.setEnabled(false);
    }
    private void Lista()
    {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BASE_DATOS,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String query ="SELECT * FROM "+Utilidades.T_Duda+" WHERE 1";
        Log.e("Salida",query);
        Log.e("Salida",Utilidades.CREA_TABLA_DUDA);
        Cursor c= Utilidades.Listar_Tabla(db,query);

        Links = new ArrayList<>();
        Contenido = new ArrayList<>();
        adapter= new ArrayAdapter<>(this, R.layout.spinner_doc, Contenido );
        spiDuda.setAdapter(adapter);
        Contenido.add("Seleccione la duda a consultar");
        Links.add("0");

        c.moveToFirst();
        if(c != null && c.getCount()>0)
        {
            do
            {
                int c0= c.getInt(0);
                String c1= c.getString(1);
                String c2= c.getString(2);
                Log.e("Salida","id="+c0+"  "+c1+"   "+c2+"  ");
                Contenido.add(c1);
                Links.add(c2);
            }while (c.moveToNext());
            spiDuda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.e("Salida","Entro");
                    if(i>0)
                    {
                        editTextoDuda.setText(spiDuda.getItemAtPosition(i).toString());
                        editTextoRespDuda.setText(Links.get(i).toString());
                        editTextoDuda.setEnabled(false);
                        if(Utilidades.SOCIO_ADMINISTRADOR==1)
                            editTextoRespDuda.setEnabled(true);
                        btnaceptarDuda.setEnabled(false);
                        btnRespDuda.setEnabled(true);
                    }
                    else
                        Log.e("Salida","No valido");
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            adapter= new ArrayAdapter<>(this, R.layout.spinner_doc, Contenido );
            spiDuda.setAdapter(adapter);
            editTextoRespDuda.requestFocus();

        }
        else
            Log.e("Salida","Vacio");
        c.close();
        db.close();


    }



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


    private ContentValues ValidarCampos(String Duda, String Resp)
    {
        ContentValues values = new ContentValues();
        Log.e("Salida",Duda+" sdfsdf "+Resp);
        if(Duda.trim().equals("")  )
        {
            Log.e("Salida",Duda+" Vacios "+Resp);
            values.put(Utilidades.DUDA_DUDA,"");
            values.put(Utilidades.DUDA_RESP,"");
        }
        else
        {
            Log.e("Salida","Llenos"+" Vacios "+Resp);
            values.put(Utilidades.DUDA_DUDA,Duda);
            values.put(Utilidades.DUDA_RESP,Resp);
        }
        return values;

    }
    public void btnCreaDuda(View view)
    {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BASE_DATOS,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String Duda;
        Duda = editTextoDuda.getText().toString().trim();
        String RespuestaDuda = "Por Responder";
        ContentValues values = ValidarCampos(Duda,RespuestaDuda);
        if(values.getAsString(Utilidades.DUDA_DUDA).equals(""))
        {
            Toast.makeText(getApplicationContext(), "Algún campo esta vacio!!!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String query ="SELECT * FROM "+Utilidades.T_Duda +" WHERE "+Utilidades.DUDA_DUDA+" ='"+Duda+"'";

            if(Utilidades.BuscaLogica(db , query))
            {
                Toast.makeText(getApplicationContext(), "Duda ya registrada", Toast.LENGTH_SHORT).show();
            }
            else
            {
//                if(Utilidades.SOCIO_ADMINISTRADOR==1)
                {
                    Long i= Utilidades.Insertar_En_Tabla(Utilidades.T_Duda, values, db);
                    Log.e("Salida","Long = "+i);
                    Log.e("Salida",values.toString());
                    Lista();
                }
            }

        }
        db.close();

        Limpiarcampos();
    }

    public void btnRespDuda(View view)
    {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BASE_DATOS,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String DUDA = editTextoDuda.getText().toString().trim();
        String RESPDUDA = editTextoRespDuda.getText().toString().trim();
        ContentValues values = ValidarCampos(DUDA,RESPDUDA);
        String[] args = new String []{ DUDA };
        int i= Utilidades.Upgrade_Tabla(Utilidades.T_Duda, values, Utilidades.DUDA_DUDA+"=?", args, db);
        btnaceptarDuda.setEnabled(true);
        btnRespDuda.setEnabled(false);
        Log.e("Salida","Long = "+i);
        db.close();
        Limpiarcampos();
        
    }

    public void bntIniciar(View view)
    {
        editTextoDuda.setEnabled(true);
        if(Utilidades.SOCIO_ADMINISTRADOR==1)
            editTextoRespDuda.setEnabled(true);
        btnaceptarDuda.setEnabled(true);
        editTextoRespDuda.setText("");
        editTextoDuda.setText("");
        editTextoDuda.requestFocus();
        btnRespDuda.setEnabled(false);
        Lista();

    }
}