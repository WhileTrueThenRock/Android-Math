package com.example.grigoreadrianmaths.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.grigoreadrianmaths.R;

import java.util.ArrayList;

public class AdapterPersonalizado extends BaseAdapter {
    private ArrayList datos;
    private Context context;
    private LayoutInflater inflater;

    public AdapterPersonalizado(Context applicationContext, ArrayList<String> datos) {
        this.context = applicationContext;
        this.datos = datos;
        this.inflater = LayoutInflater.from(applicationContext);
    }

    @Override
    public int getCount() {
        return datos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.linearlayout, null);
        TextView texto = view.findViewById(R.id.textView);
        texto.setText(datos.get(i).toString());

        ImageView icon = view.findViewById(R.id.icon1);
        // Asigna el trofeo según la posición
        if (i < 4) {
            // Para los primeros 4 elementos
            int trofeoResourceId = getTrophyResource(i);
            icon.setImageResource(trofeoResourceId);
        } else {
            // Por defecto, asigna bronce para los demás
            icon.setImageResource(R.drawable.bronze);
        }

        return view;
    }

    // Método para obtener el recurso del trofeo según la posición
    private int getTrophyResource(int position) {
        switch (position) {
            case 0:
                return R.drawable.platinum; // Primer lugar - Oro
            case 1:
                return R.drawable.gold; // Segundo lugar - Plata
            case 2:
                return R.drawable.silver; // Tercer lugar - Bronce
            case 3:
                return R.drawable.bronze; // Cuarto lugar - Platino
            default:
                return R.drawable.bronze; // Por si acaso
        }
    }
}
