package com.example.covidapp_v5.ui.bbdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class BaseDatos extends SQLiteOpenHelper {

    public static String DB_FILEPATH = "/data/data/{package_name}/databases/Lugares.db";

    /*
    Se declara e inicializa una variable encargada de controlar el tipo de dato
    de cada columna de la tabla.
    */
    private static final String TEXT_TYPE = " TEXT";

    /*
    Se declara e inicializa una consulta Transact-Sql para la creación
	de una tabla formada por tres campos o columnas.
	*/
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + EstructuraDatos.TABLE_NAME + " (" +
                    EstructuraDatos._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    EstructuraDatos.COLUMN_NAME_NOMBRE + TEXT_TYPE + ", " +
                    EstructuraDatos.COLUMN_NAME_DIRECCION + TEXT_TYPE + ", " +
                    EstructuraDatos.COLUMN_NAME_TELEFONO + TEXT_TYPE + ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + EstructuraDatos.TABLE_NAME;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Lugares";

    public BaseDatos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    /*Método que recibe la consulta Transact-SQL para para crear la Tabla.*/
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    /*Método que elimina la tabla y vuelve a invocar al método que la crea.*/
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}
