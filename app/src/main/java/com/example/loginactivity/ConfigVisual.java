package com.example.loginactivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ConfigVisual extends Fragment {

    private TextView textPadrao;
    private TextView textLaranja;
    private TextView textVerde;
    private TextView textMarrom;
    private Context context;
    private AlertDialog alerta;

    public ConfigVisual() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //ConfigVisual foi criado com o intuito de personalizar o app com algumas variações de cores
        //Ao selecionar a cor que deseja como tema,
        // irá exibir uma pergunta em uma AlertDialog pedindo para confirmar ou cancelar a ação.

        context = getContext();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_config_visual, container, false);

        loadViews(view);
        click(view);

        return view;
    }

    private void click(View view){
        textPadrao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertaVisual();
            }
        });

        textLaranja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertaVisual();
            }
        });

        textVerde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertaVisual();
            }
        });
        textMarrom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertaVisual();
                }
        });
    }

    private void alertaVisual(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.configure_alerta_visual_info_title);
        builder.setMessage(R.string.configure_alerta_visual_info);
        builder.setPositiveButton(R.string.alerta_selecionar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(),"Selecionou", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.alerta_cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(),"Cancelou", Toast.LENGTH_SHORT).show();
            }
        });
        alerta = builder.create();
        alerta.show();
    }

    private void loadViews(View view){
        textPadrao = view.findViewById(R.id.textVisualPadrao);
        textLaranja = view.findViewById(R.id.textVisualLaranja);
        textVerde = view.findViewById(R.id.textVisualVerde);
        textMarrom = view.findViewById(R.id.textVisualMarrom);

    }
}