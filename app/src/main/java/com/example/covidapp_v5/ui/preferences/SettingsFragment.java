package com.example.covidapp_v5.ui.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.covidapp_v5.MainActivity;
import com.example.covidapp_v5.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashSet;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends PreferenceFragmentCompat {
    private OnFragmentInteractionListener mListener;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load the preferences from an XML resource
        setPreferencesFromResource(R.xml.preferences, rootKey);

        Preference backup = findPreference("Backup");
        Preference restore = findPreference("Restore");
        backup.setOnPreferenceClickListener(preference -> {
            SharedPreferences xml = preference.getSharedPreferences();//("com.example.unidad5_preferences", MODE_PRIVATE);
            HashSet vacio = new HashSet<String>();
            vacio.add("1");
            vacio.add("2");
            String nombre = xml.getString("Nombre", "no definido");
            String apellidos = xml.getString("Apellidos", "no definido");
            String notificaciones = String.valueOf(xml.getBoolean("Notificaciones", false));
            String nivelpreocupacion = xml.getString("NivelPreocupación", "sin definir");
            HashSet sintomas = (HashSet) xml.getStringSet("Sintomas", vacio);
            String sintomasstring = sintomas.toString();
            String positivos = String.valueOf(xml.getBoolean("Positivos", false));

            SharedPreferences fichero = preference.getContext().getSharedPreferences ("perfil", MODE_PRIVATE);
            SharedPreferences.Editor editor = fichero.edit();
            editor.putString("nombre", nombre);
            editor.commit();
            editor.putString("apellidos", apellidos);
            editor.commit();
            editor.putString("notificaciones", notificaciones);
            editor.commit();
            editor.putString("nivelpreocupacion", nivelpreocupacion);
            editor.commit();
            //editor.putString("sintomas", sintomas);
            //editor.commit();
            editor.putString("sintomasstring", sintomasstring);
            editor.commit();
            editor.putString("positivos", positivos);
            editor.commit();

            String estado;
            boolean memok=false;
            estado = Environment.getExternalStorageState();
            if (estado.equals(Environment.MEDIA_MOUNTED)){
                memok = true;
            }
            if (memok){
                try{
                    File ruta = new File(getContext().getExternalFilesDir(null),"ficheroexterno.txt");
                    Toast toast1 =Toast.makeText(getContext().getApplicationContext(),ruta.toString(), Toast.LENGTH_SHORT);
                    toast1.show();
                    OutputStreamWriter salida = new OutputStreamWriter(new FileOutputStream(ruta));
                    salida.write(nombre + apellidos + notificaciones + nivelpreocupacion + sintomas + sintomasstring + positivos );
                    salida.close();
                }catch (Exception ex){
                    Log.e("Ficherosexternos", "Error al escribir fichero en memoria externa");
                }
            }
            return true;
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {}
}