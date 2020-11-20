package com.example.covidapp_v5.ui.ficheros;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.covidapp_v5.R;
import com.example.covidapp_v5.ui.bbdd.BaseDatos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;

import static com.example.covidapp_v5.ui.bbdd.BaseDatos.*;

public class FicherosFragment extends Fragment {

    private static final String DB_NAME="Lugares";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_ficheros, container, false);

        //esto general
        final EditText textoescrito= root.findViewById(R.id.editGuardar);
        final TextView etiqueta= root.findViewById(R.id.txtLeido);
        textoescrito.setText("");

        //esto de internos
        final Button botonguardar= root.findViewById(R.id.btnFIGuardar);
        final Button botonleer= root.findViewById(R.id.btnFILeer);
        final String[] FILENAME = {""};
        final String[][] OUTPUTFILENAME = {{""}};

        //esto de externos
        final Button botonleerext= root.findViewById(R.id.btnFELeer);

        // y este para el de raw
        final Button botonguardarraw= root.findViewById(R.id.btnFRGuardar);
        final Button botonleerraw= root.findViewById(R.id.btnFRLeer);

        final String[] outFileName = new String[1];
        //esto de internos
        botonguardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // ruta fichero DB
                final String inFileName = "//data//data//com.example.covidapp_v5//databases//Lugares";
                // nombre fichero backup DB
                String cadena = textoescrito.getText().toString();

                File dbFile = new File(inFileName);
                FileInputStream fis;
                OutputStream output;
                try {

                    fis = new FileInputStream(dbFile);
                    String outFileName = "/data/data/com.example.covidapp_v5/files/"+ cadena + ".db";
                    String[] parts = outFileName.split("/");
                    System.out.println("PARTE[1]" + parts[1]); // data
                    System.out.println("PARTE[2]" + parts[2]); // data
                    System.out.println("PARTE[3]" + parts[3]); // com.example.covidapp_v5
                    System.out.println("PARTE[4]" + parts[4]); // files
                    System.out.println("PARTE[5]" + parts[5]); // BACKUP.db

                    FILENAME[0] = parts[5];

                    // Open the empty db as the output stream
                    output = new FileOutputStream(outFileName);

                    // Transfer bytes from the inputfile to the outputfile
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer))>0){
                        output.write(buffer, 0, length);
                    }

                    // Close the streams
                    output.flush();
                    output.close();
                    fis.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                textoescrito.setText("");
                etiqueta.setText("");
            }//onClick
        });

        botonleer.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                InputStreamReader ficherolectura=null;
                String cadena = FILENAME[0];
                etiqueta.append("Se ha generado la copia de seguridad: " + cadena);
               /* try {
                    ficherolectura = new InputStreamReader(getContext().openFileInput(FILENAME[0]));

                    BufferedReader br= new BufferedReader(ficherolectura);
                    cadena = br.readLine();
                    while (cadena != null){
                        etiqueta.append(cadena);
                        cadena=br.readLine();
                    }
                } catch (Exception ex) {
                    Log.e("Aplicación ficheros", "Error leyendo de fichero");
                }
                finally{
                    try{
                        if (ficherolectura!=null)
                            ficherolectura.close();
                    }catch (IOException ioe){
                        ioe.printStackTrace();
                    }
                }*/
            }
        });

        //esto de externos, hasta aquí de internos
        botonleerext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    File ruta = new File(getContext().getExternalFilesDir(null),"ficheroexterno.txt");
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(ruta)));
                    String cadena = br.readLine();
                    br.close();
                    Toast toast1 =Toast.makeText(getContext().getApplicationContext(),cadena, Toast.LENGTH_SHORT);
                    etiqueta.setText(cadena);
                    toast1.show();
                }catch (Exception ex){
                    Log.e("Ficherosexternos", "Error al leer fichero en memoria externa");
                }

            }
        });


        //// y a partir de aquí, los ficheros Raw
        botonleerraw.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                BufferedReader br;
                InputStream ficheroraw;
                String todo="";
                try
                {
                    String cadena;
                    ficheroraw = getResources().openRawResource(R.raw.android);
                    br =new BufferedReader(new InputStreamReader(ficheroraw));
                    while ((cadena = br.readLine()) != null){
                        Log.i("Aplicacion Ficheros raw", cadena);
                        etiqueta.setText(cadena);
                    }
                    //etiqueta.setText(todo);
                    ficheroraw.close();
                }
                catch (IOException e){
                    e.printStackTrace();}
            }
        });

        botonguardarraw.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                etiqueta.setText("En ficheros Raw, sólo se puede leer no escribir");

            }
        });

        return root;
    }

}