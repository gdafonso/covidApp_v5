package com.example.covidapp_v5.ui.bbdd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.covidapp_v5.R;

import java.util.ArrayList;

public class AdaptadorLugar extends BaseAdapter {

    // Se declaran las clases necesarias para posteriormente añadirlas como argumentos al constructor
    private Context context;
    private ArrayList<Lugar> lugarArrayList;
    private LayoutInflater inflater;

    /*
    Constructor que será invocado desde el componente ListView (como argumento del método
    setAdapter()) y que recibe el contexto de la aplicación y los objetos almacenados en un
    ArrayList
     */
    public AdaptadorLugar(Context context, ArrayList<Lugar> lugarArrayList){
        this.context = context;
        this.lugarArrayList = lugarArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount(){
        return lugarArrayList.size();
    }

    @Override
    public Object getItem(int posicion){
        return lugarArrayList.get(posicion);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent){
        View v = convertview;
        ViewHolder vista = new ViewHolder();

        if(convertview == null) {
            v = inflater.inflate(R.layout.fragment_bbdd, parent, false);

            /*Se asocian los controles de tipo TextView con sus recursos a nivel de layout.*/
            vista.txtNombre = (TextView)v.findViewById(R.id.txtBBDD_nombre);
            vista.txtDireccion = (TextView)v.findViewById(R.id.txtBBDD_direccion);
            vista.txtTelefono = (TextView)v.findViewById(R.id.txtBBDD_telefono);

			/*
			Se asignan a cada componente el índice de la columna a mostrar, a partir de los resultados
			de la consulta procesada. En cada ítem del componente de selección ListView se mostrarán
			los registros de id, cliente y teléfono almacenado.
			*/
            vista.txtNombre.setText(" | Nombre: " + lugarArrayList.get(position).getNombre() + " | ");
            vista.txtDireccion.setText("Cliente: " + lugarArrayList.get(position).getDireccion() + " |");
            vista.txtTelefono.setText("Telefono: " + lugarArrayList.get(position).getTelefono() + " | ");
        } else {
            vista = (ViewHolder) v.getTag();
        }
        return v;
    }

    static class ViewHolder {
        public TextView txtNombre, txtDireccion, txtTelefono;
    }

}
