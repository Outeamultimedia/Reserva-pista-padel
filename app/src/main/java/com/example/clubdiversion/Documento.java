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

public class Documento extends AppCompatActivity {


    EditText editDecDocum;
    EditText editLinkDocum;
    Spinner spiDesDoc;
    List<String> Contenido;
    List<String> Links;
    ArrayAdapter<String> adapter;
    TextView txtNonDoc;
    Button btnAceptarDocum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documento);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        txtNonDoc = findViewById(R.id.txtNonDoc);
        btnAceptarDocum = findViewById(R.id.btnAceptarDocum);
        editDecDocum = findViewById(R.id.editDecDocum);
        editLinkDocum = findViewById(R.id.editLinkDocum);
        spiDesDoc = findViewById(R.id.spiDesDoc);
        String so="";
        if(Utilidades.SOCIO_ADMINISTRADOR==1)
        {
            so="Administrador";
        }
        else
        {
            so="Socio";
            editDecDocum.setEnabled(false);
            editLinkDocum.setEnabled(false);
            btnAceptarDocum.setVisibility(View.INVISIBLE);
        }

        txtNonDoc.setText(so+": "+Utilidades.NOMBRE_SOCIO);
        //editLinkDocum.setText("https://repositorio.uchile.cl/tesis/uchile/2010/ec-gonzalez_mj/pdfAmont/ec-gonzalez_mj.pdf");
        Lista();
        Lista();
    }

    private void Lista()
    {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BASE_DATOS,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String query ="SELECT  * FROM "+Utilidades.T_Doc+" WHERE 1";
        Cursor c= Utilidades.Listar_Tabla(db,query);

        Links = new ArrayList<>();
        Contenido = new ArrayList<>();
        adapter= new ArrayAdapter<>(this, R.layout.spinner_doc, Contenido );
        spiDesDoc.setAdapter(adapter);
        Contenido.add("Seleccione PDF");
        Links.add("");

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
            spiDesDoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.e("Salida","Entro");
                    if(i>0)
                    {
                        Log.e("Salida",spiDesDoc.getItemAtPosition(i).toString()+"  "+Links.get(i).toString());

                        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                        intent.putExtra("Descripcion",spiDesDoc.getItemAtPosition(i).toString());
                        intent.putExtra("Link",Links.get(i).toString() );
                        startActivity(intent);

                    }
                    else
                        Log.e("Salida","No valido");

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            adapter= new ArrayAdapter<>(this, R.layout.spinner_doc, Contenido );
            spiDesDoc.setAdapter(adapter);

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

    public void VerDoc(View view) {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BASE_DATOS,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String Descripcion;
        Descripcion = editDecDocum.getText().toString().trim();
        String Link = editLinkDocum.getText().toString().trim();
        ContentValues values = ValidarCampos(Descripcion,Link);
        if(values.getAsString(Utilidades.DOC_DES).equals(""))
        {
            Toast.makeText(getApplicationContext(), "Algún campo esta vacio!!!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String query ="SELECT * FROM "+Utilidades.T_Doc +" WHERE "+Utilidades.DOC_DES+" ='"+Descripcion+"'";
            if(Utilidades.BuscaLogica(db , query))
            {
                Toast.makeText(getApplicationContext(), "Descripción ya registrada", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if(Utilidades.SOCIO_ADMINISTRADOR==1)
                {
                    Long i= Utilidades.Insertar_En_Tabla(Utilidades.T_Doc, values, db);
                    Log.e("Salida","Long = "+i);
                    Log.e("Salida",values.toString());
                    Lista();
                }
                else
                    Toast.makeText(getApplicationContext(), "Usted no es Administrador!!!", Toast.LENGTH_SHORT).show();
            }

        }
        db.close();


    }
    private ContentValues ValidarCampos(String DES, String LINK)
    {
        ContentValues values = new ContentValues();
        if(DES.trim().equals("") || LINK.trim().equals("") )
        {
            values.put(Utilidades.DOC_DES,"");
            values.put(Utilidades.DOC_LINK,"");

        }
        else
        {
            values.put(Utilidades.DOC_DES,DES);
            values.put(Utilidades.DOC_LINK,LINK);
        }
        return values;

    }

}