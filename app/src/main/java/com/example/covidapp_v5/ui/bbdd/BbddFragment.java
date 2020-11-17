package com.example.covidapp_v5.ui.bbdd;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
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

public class BbddFragment extends Fragment {

    private TextView txtTotalRegistros, txtCabecera, txtResultados;
    private EditText txtNombre, txtDireccion, txtTelefono;
    private Cursor c;
    private Bundle bundle;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_bbdd, container, false);

        /*
        Se enlazan los componentes declarados con sus recursos a nivel de layout.
        */
        txtCabecera = root.findViewById(R.id.lblBBDD_cabecera);
        txtNombre = root.findViewById(R.id.txtBBDD_nombre);
        txtDireccion = root.findViewById(R.id.txtBBDD_direccion);
        txtTelefono = root.findViewById(R.id.txtBBDD_telefono);
        txtTotalRegistros = root.findViewById(R.id.lblBBDD_total);
        txtResultados = root.findViewById(R.id.txtBBDD_listado);
        final Button btnGuardar = (Button) root.findViewById(R.id.btnBBDD_guardar);

        /*
        Se declara e inicializa la clase ContentResolver,
        referenciándole el método getContentResolver(),
		para posteriormente invocar al método query(),
		y asignar el resultado de la consulta a un objeto Cursor.
		*/
        ContentResolver resolver = getActivity().getApplicationContext().getContentResolver();

        c = resolver.query(CompartirDatos.CONTENT_URI,
                CompartirDatos.columnas, null, null, null);

        // Se obtiene el número de registros devueltos por la consulta.
        int totalRegistros = c.getCount();
        // Se muestra el número de registros en un componente de tipo TextView.
        txtTotalRegistros.setText(String.valueOf(totalRegistros));

        /*
        Evento onClick para insertar los datos introducidos en los campos EditText.
        */
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){

                String nombre = txtNombre.getText().toString();
                String direccion = txtDireccion.getText().toString();
                String telefono = txtTelefono.getText().toString();
                if (nombre.equals("") || direccion.equals("") || telefono.equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Es necesario que introduzca todos los datos.",
                            Toast.LENGTH_LONG).show();
                } else {
                    /*
                    Declaramos e inicializamos la clase ContentValues, para almacenar los datos del
                    nuevo registro.
                    */
                    ContentValues values = new ContentValues();
                    values.put(EstructuraDatos.COLUMN_NAME_NOMBRE, nombre);
                    values.put(EstructuraDatos.COLUMN_NAME_DIRECCION, direccion);
                    values.put(EstructuraDatos.COLUMN_NAME_TELEFONO, telefono);
                    /*
                    Se invoca al método insert(), indicando la URI definida y los valores a
                    insertar entre sus argumentos.
                    */
                    getActivity().getApplicationContext().getContentResolver().
                            insert(CompartirDatos.CONTENT_URI, values);
                    refrescarPantalla();
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Se ha almacenado un nuevo cliente: " + nombre,
                            Toast.LENGTH_LONG).show();

                    txtNombre.setText("");
                    txtDireccion.setText("");
                    txtTelefono.setText("");
                }
            }
        });
        mostrarRegistrosAplicacion();
        return root;
    }


    /*
    Método que volverá a ejecutar la aplicación para refrescar el número de registros almacenados.
    */
    public void refrescarPantalla()
    {
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    /*
    Método que permite consultar la tabla Lugares, almacenando los resultados
	en un objeto Cursor. Este método devuelve un ArrayList de objetos Lugares,
	con todos los registros almacenados.
	*/
    public void mostrarRegistrosAplicacion() {

        ContentResolver resolver = getActivity().getApplicationContext().getContentResolver();
        c = resolver.query(CompartirDatos.CONTENT_URI,
                CompartirDatos.columnas, null, null, null);

        if (c.moveToFirst()){
            String nombre, direccion, telefono;
            int colNombre, colDireccion, colTelefono;
            colNombre = c.getColumnIndex(EstructuraDatos.COLUMN_NAME_NOMBRE);
            colDireccion = c.getColumnIndex(EstructuraDatos.COLUMN_NAME_DIRECCION);
            colTelefono = c.getColumnIndex(EstructuraDatos.COLUMN_NAME_TELEFONO);

            txtResultados.setText("");
            do {
                nombre = c.getString(colNombre);
                direccion = c.getString(colDireccion);
                telefono = c.getString(colTelefono);

                txtResultados.append(nombre + " - " + direccion + " - " + telefono + "\n");
            }while(c.moveToNext());
        }
    }


}