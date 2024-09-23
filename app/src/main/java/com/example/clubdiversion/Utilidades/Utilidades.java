package com.example.clubdiversion.Utilidades;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Queue;

public class Utilidades {

    //CAMPOS socio
    public static final String BASE_DATOS = "club";
    public static final String TABLA_SOCIO = "socio";
    public static final String SOCIO_ID = "id";
    public static final String SOCIO_NIP = "nip";
    public static final String SOCIO_NOMBRE = "Nombre";
    public static final String SOCIO_DIRECCION = "Direccion";
    public static final String SOCIO_TLF = "Tlf";
    public static final String SOCIO_S_A = "SocAdm";
    public static final String SOCIO_PASS = "Pass";
    public static final String SOCIO_REPASS = "Repass";
    public static int SOCIO_ADMINISTRADOR=0;
    public static String NOMBRE_SOCIO;
    public static int SO_AD_ID;

    //CREA TABLA socio
    public static final String CREAR_TABLA_SOCIO = "CREATE TABLE "+TABLA_SOCIO +
            "("+SOCIO_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ""+SOCIO_NIP+" TEXT," +
            ""+SOCIO_NOMBRE+" TEXT," +
            ""+SOCIO_DIRECCION+" TEXT," +
            ""+SOCIO_TLF+" TEXT," +
            ""+SOCIO_S_A+" tinyint(1), " +
            ""+SOCIO_PASS+" TEXT)";


    //CAMPOS documento
    public static final String T_Doc = "documentos";
    public static final String DOC_ID = "id";
    public static final String DOC_DES = "descripcion";
    public static final String DOC_LINK = "link";

    //CREA TABLA documento
    public static final String CREA_TABLA_DOC = "CREATE TABLE "+T_Doc +
            "("+DOC_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ""+DOC_DES+" TEXT," +
            ""+DOC_LINK+" TEXT)";

    //CAMPOS dudas
    public static final String T_Duda = "duda";
    public static final String DUDA_ID = "id";
    public static final String DUDA_DUDA = "txtduda";
    public static final String DUDA_RESP = "txtresp";

    //CREA TABLA documento
    public static final String CREA_TABLA_DUDA = "CREATE TABLE "+T_Duda +
            " ("+DUDA_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ""+DUDA_DUDA+" TEXT, " +
            ""+DUDA_RESP+" TEXT)";


    //CAMPOS instalacion
    public static final String T_INST = "insta";
    public static final String INST_ID = "id";
    public static final String INST_ID_INST = "idInsta";
    public static final String INST_FECHA = "Fecha";

    //CREA TABLA Instalaciones
    public static final String CREA_TABLA_INSTA = "CREATE TABLE "+T_INST +
            " ("+INST_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ""+INST_ID_INST+" TEXT, " +
            ""+INST_FECHA+" TEXT)";



    public static Long Insertar_En_Tabla(String tabla, ContentValues values, SQLiteDatabase db )
    {
        Long idResul=-1L;
        idResul = db.insert(tabla,null,values);
        return idResul;
    }

    public static Cursor Listar_Tabla(SQLiteDatabase db , String query)
    {
        Cursor c = db.rawQuery(query,null);
        return c;
    }
    public static boolean BuscaLogica(SQLiteDatabase db , String query)
    {
        Cursor c  = db.rawQuery(query,null);
        if (c.moveToFirst())
        {
            Log.e("Salida","Si existe");
            return true;
        }
        else
            Log.e("Salida","No existe");
        return false;
    }

    public static Cursor BuscaSocio(SQLiteDatabase db , String query)
    {
        Cursor c  = db.rawQuery(query,null);

        return c;
    }

    public static int Upgrade_Tabla(String tabla, ContentValues values, String where, String[] args, SQLiteDatabase db )
    {
        int idResul=-1;
        idResul = db.update(tabla, values, where, args);
        return idResul;
    }

    public static int Eliminar_Tabla(String tabla, String where, String[] args, SQLiteDatabase db )
    {
        int i=0;
        db.delete(tabla, where, args);
        return i;
    }

}
