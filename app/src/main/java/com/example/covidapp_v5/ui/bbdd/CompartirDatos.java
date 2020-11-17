package com.example.covidapp_v5.ui.bbdd;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/*
Clase ComaprtirDatos que hereda de la clase base ContentProvider encargada de establecer los
mecanismos necesarios para intercambiar información con el resto de aplicaciones
 */
public class CompartirDatos extends ContentProvider {

    private static final String AUTORIDAD = "com.example.covidapp_v5.ui.bbdd.CompartirDatos";

    /*
    Se declara e inicializa una constante de la clase Uri que recogerá la URI que identificará
    de manera única el Content Provider
     */
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTORIDAD + "/" + EstructuraDatos.TABLE_NAME);
    public static final int LUGARES = 1;
    public static final int LUGARES_ID = 2;
    private SQLiteDatabase db;
    BaseDatos datos;
    private static final UriMatcher uriMatcher;
    // Se inicializa la calse UriMatcher para definir los tipos de URI y poder devovlerlos a su tipo
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTORIDAD, EstructuraDatos.TABLE_NAME, LUGARES);
        uriMatcher.addURI(AUTORIDAD, EstructuraDatos.TABLE_NAME + "/#", LUGARES_ID);
    }

    // Array de Strings con todos los campos existentes en la tabla Lugares
    public static String[] columnas = new String[]{EstructuraDatos._ID,
            EstructuraDatos.COLUMN_NAME_NOMBRE,
            EstructuraDatos.COLUMN_NAME_DIRECCION,
            EstructuraDatos.COLUMN_NAME_TELEFONO};

    /*
    Método que procesará las solicitudes de eliminación de datos, recibiendo entre sus parámetros
    la URI del Content Provider y lso argumentos de selección y orden definidos.
    Devovlerá el número de filas afectadas
     */
    @Override
    public int delete(Uri uri, String selection, String[] arg2) {
        int id;

        String where = selection;
        if(uriMatcher.match(uri) == LUGARES_ID)
        {
            /*
            Se establece la cláusula WHERE con la condición del campo
            y valor como condición de eliminación.
            */
            where = EstructuraDatos._ID + "=" + uri.getLastPathSegment();
        }
        db = datos.getReadableDatabase();
        /*
        Se invoca al método delete(), indicando entre sus argumentos la tabla,
        y la cláusula WHERE con la condición
        que debe cumplir el registro a eliminar.
        */
        id = db.delete(EstructuraDatos.TABLE_NAME, where, arg2);

        return id;
    }

    @Override
    public String getType(Uri uri){
        return null;
    }

    /*
    Método que posibilita la inserción de datos devolviendo la URI que hace referencia al registro
    introducido.
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long regId = 1;
        SQLiteDatabase db = datos.getWritableDatabase();
        regId = db.insert(EstructuraDatos.TABLE_NAME, null, values);
        Uri newUri = ContentUris.withAppendedId(CONTENT_URI, regId);
        return newUri;
    }

    /*
    Método donde se inicializa la base de datos SQLite utilizada para el
    Content Provider
     */
    @Override
    public boolean onCreate(){
        datos = new BaseDatos(getContext());
        return true;
    }

    /*
    Método que permite consutlar los datos almacenados.
    Este método devuelve un objeto Cursor con los datos solicitados al Content Provider
     */
    @Override
    public Cursor query(Uri uri, String[] columnas, String arg2, String[] arg3, String arg4){
        datos = new BaseDatos(getContext());
        // Se establecen permisso de lectura
        db = datos.getReadableDatabase();
        // Se invoca al método rawQuery() con la consulta Transact-SQL a procesar
        Cursor c = db.rawQuery("SELECT * FROM LUGARES", null);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3){
        return 0;
    }

}
