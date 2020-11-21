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
        //salida.append(motionEvent.toString()+"\n");
        String acciones[] = {"ACTION_DOWN", "ACTION_UP", "ACTION_MOVE", "ACTION_CANCEL",
        "ACTION_OUTSIDE", "ACTION_POINTER_DOWN", "ACTION_POINTER_UP"};
        int accion = motionEvent.getAction();
        int codigoAccion = accion & motionEvent.ACTION_MASK;
        salida.append(acciones[codigoAccion]);
        for(int i=0; i<motionEvent.getPointerCount(); i++){
            salida.append(" puntero:" + motionEvent.getPointerId(i) + " x:" + motionEvent.getX(i) + " y:" + motionEvent.getY(i));
        }
        salida.append("\n");
        return false;
    }
}
