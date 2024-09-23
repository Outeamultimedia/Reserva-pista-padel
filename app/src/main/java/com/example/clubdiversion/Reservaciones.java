package com.example.clubdiversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clubdiversion.Utilidades.Utilidades;

import java.util.Calendar;
import java.util.Locale;

public class Reservaciones extends AppCompatActivity {

    ImageView imageInstalacionReser;
    TextView txtNombreReser;
    TextView txtSocioReser;
    TextView editfechaReser;

    long FechaActual;
    long FechaReser;
    int NumeroReservacion;
    private int ultimoAnio, ultimoMes, ultimoDiaDelMes;
    int[] imagen = {
            0,
            R.drawable.descarga1,
            R.drawable.descarga2,
            R.drawable.descarga3,
            R.drawable.descarga4,
            R.drawable.descarga5,
            R.drawable.descarga6,
            R.drawable.descarga7,
            R.drawable.descarga8,
            R.drawable.descarga9

    };
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

    TextView techado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservaciones);
        imageInstalacionReser = findViewById(R.id.imageInstalacionReser);
        txtNombreReser = findViewById(R.id.txtNombreReser);
        txtSocioReser = findViewById(R.id.txtSocioReser);
        editfechaReser = findViewById(R.id.editfechaReser);
        techado = findViewById(R.id.techado);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bundle bundle = getIntent().getExtras();
        NumeroReservacion = bundle.getInt("Imagen");
        imageInstalacionReser.setImageResource(imagen[ NumeroReservacion]);
        txtNombreReser.setText(bundle.getString("Espacio"));
        txtSocioReser.setText(String.format("   %s",  bundle.getString("Socio")));

        techado.setText("Techado: "+bundle.getString("techado"));

        /*
        intent.putExtra("capacidad",capacidad[num]);
        intent.putExtra("techado",techado[num]);
        intent.putExtra("area",area[num]);*/



        final Calendar calendario = Calendar.getInstance();
        ultimoAnio = calendario.get(Calendar.YEAR);
        ultimoMes = calendario.get(Calendar.MONTH);
        ultimoDiaDelMes = calendario.get(Calendar.DAY_OF_MONTH);
        FechaActual = ultimoAnio*10000+ultimoMes*100+ultimoDiaDelMes;

        editfechaReser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialogoFecha = new DatePickerDialog(Reservaciones.this, AlertDialog.BUTTON_POSITIVE, listenerDeDatePicker, ultimoAnio, ultimoMes, ultimoDiaDelMes);
                dialogoFecha.show();
            }
        });
        txtSocioReser.setText(Utilidades.NOMBRE_SOCIO);

        //Mostrar

    }
    public void refrescarFechaEnEditText() {
        // Formateamos la fecha pero podríamos hacer cualquier otra cosa ;)
        String fecha = String.format(Locale.getDefault(), "%02d-%02d-%02d", ultimoDiaDelMes,  ultimoMes+1, ultimoAnio);
        final Calendar calendario = Calendar.getInstance();

        // La ponemos en el editText
        editfechaReser.setText(fecha);
    }

    private DatePickerDialog.OnDateSetListener listenerDeDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int anio, int mes, int diaDelMes) {
            // Esto se llama cuando seleccionan una fecha. Nos pasa la vista, pero más importante, nos pasa:
            // El año, el mes y el día del mes. Es lo que necesitamos para saber la fecha completa
            Log.e("Salida","EMPEZO");

            Log.e("Salida","FechaInicial = "+ultimoAnio+"/"+ultimoMes+"/"+ultimoDiaDelMes);
            Log.e("Salida","FechaReserva = "+anio+"/"+mes+"/"+diaDelMes);

            FechaReser = anio*10000+mes*100+diaDelMes;
            //if(FechaReser<FechaActual)
            Log.e("Salida","Actual = "+FechaActual+" Reser = "+FechaReser);
            //refrescarFechaEnEditText();
            String fecha = String.format(Locale.getDefault(), "%02d-%02d-%02d", diaDelMes,  mes+1, anio);
            if(FechaActual<FechaReser)
                editfechaReser.setText(fecha);
            else
                editfechaReser.setText("Pulse aquí para la fecha");

        }
    };



    public void btnAceptarInsta(View view)
    {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BASE_DATOS,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String FECHA = editfechaReser.getText().toString().trim();
        ContentValues values = new ContentValues();
        values.put(Utilidades.INST_FECHA,FECHA);
        values.put(Utilidades.INST_ID_INST,NumeroReservacion);;

        if(!editfechaReser.getText().toString().trim().equals("Pulse aquí para la fecha"))
        {
            String query ="SELECT * FROM "+Utilidades.T_INST +" WHERE "+Utilidades.INST_FECHA+" ='"+FECHA+"' and "+Utilidades.INST_ID_INST+"='"+NumeroReservacion+"'";
            Log.e("Salida",query);
            if(Utilidades.BuscaLogica(db , query))
            {
                Toast.makeText(getApplicationContext(), "Instalación ya reservada", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Utilidades.Insertar_En_Tabla(Utilidades.T_INST,values,db);
                Toast.makeText(getApplicationContext(), "Reservavión realizada", Toast.LENGTH_SHORT).show();
            }
        }
        else
            Toast.makeText(getApplicationContext(), "Seleccione la fecha", Toast.LENGTH_SHORT).show();
        db.close();
    }

    public void bntListReser(View view)
    {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BASE_DATOS,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String query ="SELECT * FROM "+Utilidades.T_INST +" WHERE 1";
        Cursor c = Utilidades.Listar_Tabla(db, query);
        c.moveToFirst();
        if(c != null && c.getCount()>0)
            do
            {
                int c0= c.getInt(0);
                String c1= c.getString(1);
                String c2= c.getString(2);
                Log.e("Salida","id="+c0+"  "+c1+"   "+c2+"  ");

            }while (c.moveToNext());
        else
            Log.e("Salida","Vacio");
        c.close();
        db.close();
    }
}