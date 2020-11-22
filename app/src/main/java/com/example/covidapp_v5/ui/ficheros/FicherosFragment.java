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

        final String[] outFileName = new String[1];
        //esto de internos
        //onClick
        botonguardar.setOnClickListener(view -> {

            // ruta fichero DB
            final String inFileName = "//data//data//com.example.covidapp_v5//databases//Lugares";
            // nombre fichero backup DB
            String cadena = textoescrito.getText().toString();

            File dbFile = new File(inFileName);
            FileInputStream fis;
            OutputStream output;
            try {

                fis = new FileInputStream(dbFile);
                String outFileName1 = "/data/data/com.example.covidapp_v5/files/"+ cadena + ".db";
                String[] parts = outFileName1.split("/");
                System.out.println("PARTE[1]" + parts[1]); // data
                System.out.println("PARTE[2]" + parts[2]); // data
                System.out.println("PARTE[3]" + parts[3]); // com.example.covidapp_v5
                System.out.println("PARTE[4]" + parts[4]); // files
                System.out.println("PARTE[5]" + parts[5]); // BACKUP.db

                FILENAME[0] = parts[5];

                // Open the empty db as the output stream
                output = new FileOutputStream(outFileName1);

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
        });

        botonleer.setOnClickListener(v -> {

            InputStreamReader ficherolectura=null;
            String cadena = FILENAME[0];
            etiqueta.append("Se ha generado la copia de seguridad: " + cadena);
        });

        //esto de externos, hasta aquí de internos
        botonleerext.setOnClickListener(v -> {
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

        });

        return root;
    }

}