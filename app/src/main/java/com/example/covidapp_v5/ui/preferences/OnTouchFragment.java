package com.example.covidapp_v5.ui.preferences;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.covidapp_v5.R;

public class OnTouchFragment extends AppCompatActivity implements View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_touch);

        TextView entrada = findViewById(R.id.label_entrada);
        entrada.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        TextView salida = findViewById(R.id.label_salida);
        salida.append(motionEvent.toString()+"\n");
        return false;
    }
}
