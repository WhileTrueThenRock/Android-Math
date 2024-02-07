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
    private int[] scores;
    private LayoutInflater inflater;
    public AdapterPersonalizado(Context applicationContext, ArrayList<String> datos, int[] scores) {
        this.context = applicationContext;
        this.datos = datos;
        this.scores = scores;
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
        if (view == null) {
            view = inflater.inflate(R.layout.linearlayout, null);
        }

        TextView texto = view.findViewById(R.id.textView);
        texto.setText(datos.get(i).toString() + " | " + scores[i]);

        ImageView icono = view.findViewById(R.id.icon1);

        // Asigna el trofeo según la posición
        if (i < 4) {
            // Para los primeros 4 elementos
            int trofeoResourceId = getTrophyResource(scores[i]);
            icono.setImageResource(trofeoResourceId);
        } else {
            // Por defecto, asigna bronce para los demás
            icono.setImageResource(R.drawable.bronze);
        }

        return view;
    }



    private int getTrophyResource(int score) {
        if (score >= 24) {
            return R.drawable.platinum;
        } else if (score >= 16) {
            return R.drawable.gold;
        } else if (score >= 8) {
            return R.drawable.silver;
        } else {
            return R.drawable.bronze;
        }
    }
}
