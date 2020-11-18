package com.example.covidapp_v5.ui.ficheros;

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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FicherosFragment extends Fragment {

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
        final String FILENAME = "ficherointerno";
        //esto de externos
        final Button botonleerext= root.findViewById(R.id.btnFELeer);
        // y este para el de raw
        final Button botonguardarraw= root.findViewById(R.id.btnFRGuardar);
        final Button botonleerraw= root.findViewById(R.id.btnFRLeer);

        //esto de internos
        botonguardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String cadena = textoescrito.getText().toString();

                FileOutputStream fos = null;
                try {
                    fos =  getContext().openFileOutput(FILENAME, 0);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    fos.write(cadena.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fos.close();
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
                String cadena;
                try {
                    ficherolectura = new InputStreamReader(getContext().openFileInput(FILENAME));
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
                }
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