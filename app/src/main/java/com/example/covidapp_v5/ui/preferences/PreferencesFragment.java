package com.example.covidapp_v5.ui.preferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
        final EditText cuadronombre = root.findViewById(R.id.CampoNombre);
        final EditText cuadroalias = root.findViewById(R.id.CampoAlias);
        final Button botonguardarnombre = root.findViewById(R.id.btnGuardarNombre);
        final Button botonguardaralias = root.findViewById(R.id.btnGuardarNick);
        final ToggleButton toggle = root.findViewById(R.id.btnAvisos);
        final Button botonguardatodo = root.findViewById(R.id.btnGuardarTodo);
        final Button botonmuestratodo = root.findViewById(R.id.btnMostrar);
        final Button botonxmlpreferences = root.findViewById(R.id.btnXml);
        final Button botonmostrarxmlpreferences = root.findViewById(R.id.btnMostrarXml);

        etiqueta.setText("");

        botonguardarnombre.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String nombre = cuadronombre.getText().toString();
                SharedPreferences preferencias = getParentFragment().getActivity().getSharedPreferences("perfil", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("nombre", nombre);
                editor.commit();
                cuadronombre.setText("");
            }//onClick
        });

        botonguardaralias.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String nick = cuadroalias.getText().toString();
                SharedPreferences preferencias = getParentFragment().getActivity().getSharedPreferences("perfil", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("alias", nick);
                editor.commit();
                cuadroalias.setText("");
            }//onClick
        });
        toggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final String musica;
                if (toggle.isChecked())
                    musica = "ON";
                else
                    musica = "OFF";

                SharedPreferences preferencias = getParentFragment().getActivity().getSharedPreferences("perfil", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("musica", musica);
                editor.commit();
            }//onClick
        });

        botonguardatodo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences preferencias = getParentFragment().getActivity().getSharedPreferences("perfil", MODE_PRIVATE);
                String nombre = cuadronombre.getText().toString();
                String nick = cuadroalias.getText().toString();
                final String musica;
                if (toggle.isChecked())
                    musica = "ON";
                else
                    musica = "OFF";

                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("nombre", nombre);
                editor.commit();
                editor.putString("alias", nick);
                editor.commit();
                editor.putString("musica", musica);
                editor.commit();
                cuadroalias.setText("");
                cuadronombre.setText("");
            }
        });

        botonmuestratodo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                SharedPreferences preferencias = getParentFragment().getActivity().getSharedPreferences("perfil", MODE_PRIVATE);
                String nombre = preferencias.getString("nombre", "no definido");
                String alias = preferencias.getString("alias", "no definido");
                String musiconoff = preferencias.getString("musica", "sin definir");
                etiqueta.setText(nombre + ", " + alias + ", " + musiconoff);
            }//onClick
        });

        botonxmlpreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intencionxml = new Intent(getActivity(), SettingsContainerActivity.class);
                PreferencesFragment.this.startActivity(intencionxml);
            }
        });

        botonmostrarxmlpreferences.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                SharedPreferences preferencias = getParentFragment().getActivity().getSharedPreferences("com.example.unidad5_preferences", MODE_PRIVATE);
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
                etiqueta.setText(nombre + ", " + apellidos + ", " + nivelpreocupacion + ", " + sintomasstring + ", " + notificaciones + ", " + positivos);

            }//onClick
        });
    return root;
    }
}
