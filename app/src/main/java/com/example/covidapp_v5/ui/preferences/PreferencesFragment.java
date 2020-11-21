package com.example.covidapp_v5.ui.preferences;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.covidapp_v5.R;

import java.util.HashSet;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.SENSOR_SERVICE;

public class PreferencesFragment extends Fragment {
    public static final String PREFS_NAME = "MySharedFile";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_preferences, container, false);

        final TextView etiqueta = root.findViewById(R.id.txtView);
        final Button botonmuestratodo = root.findViewById(R.id.btnMostrar);
        final Button botonsensores = root.findViewById(R.id.btnAbrirSensores);
        final Button botonontouch = root.findViewById(R.id.btnAbrirOnTouch);

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

        botonsensores.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SensoresFragment.class);
                startActivity(intent);
            }
        });

        botonontouch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), OnTouchFragment.class);
                startActivity(intent);
            }
        });

    return root;
    }


}