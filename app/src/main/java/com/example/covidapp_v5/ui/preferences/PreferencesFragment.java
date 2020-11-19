package com.example.covidapp_v5.ui.preferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.covidapp_v5.R;

import java.util.HashSet;

import static android.content.Context.MODE_PRIVATE;

public class PreferencesFragment extends Fragment {
    public static final String PREFS_NAME = "MySharedFile";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_preferences, container, false);

        final TextView etiqueta = root.findViewById(R.id.txtView);
        final Button botonmuestratodo = root.findViewById(R.id.btnMostrar);

        etiqueta.setText("");

        botonmuestratodo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                SharedPreferences preferencias = root.getContext().getSharedPreferences("perfil", MODE_PRIVATE);
                HashSet vacio = new HashSet<String>();
                vacio.add("1");
                vacio.add("2");
                String nombre = preferencias.getString("Nombre", "no definido");
                String apellidos = preferencias.getString("Apellidos", "no definido");
                String notificaciones = String.valueOf(preferencias.getBoolean("Notificaciones", false));
                String nivelpreocupacion = preferencias.getString("NivelPreocupación", "sin definir");
                HashSet sintomas = (HashSet) preferencias.getStringSet("Sintomas", vacio);
                String sintomasstring = sintomas.toString();
                String positivos = String.valueOf(preferencias.getBoolean("Positivos", false));
                etiqueta.setSingleLine(false);
                etiqueta.setText("Nombre: " + nombre + System.getProperty("line.separator")
                        + "Apellidos: " + apellidos + System.getProperty("line.separator")
                        + "Preocupación: " + nivelpreocupacion + System.getProperty("line.separator")
                        + "Síntomas: " + sintomasstring + System.getProperty("line.separator")
                        + "Notificaciones: " + notificaciones + System.getProperty("line.separator")
                        + "Positivos: " + positivos);
            }//onClick
        });
    return root;
    }
}