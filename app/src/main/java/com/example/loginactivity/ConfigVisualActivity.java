package com.example.loginactivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginactivity.theme.Utils;

public class ConfigVisualActivity extends AppCompatActivity {

    private TextView textPadrao;
    private TextView textLaranja;
    private TextView textVerde;
    private TextView textMarrom;
    private Context context;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(ConfigVisualActivity.this);
        setContentView(R.layout.fragment_config_visual);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = getApplicationContext();
        loadViews();

        textPadrao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(ConfigVisualActivity.this);
                builder.setTitle(R.string.configure_alerta_visual_info_title);
                builder.setMessage(R.string.configure_alerta_visual_info);
                builder.setPositiveButton(R.string.alerta_selecionar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Selecionou", Toast.LENGTH_SHORT).show();
                        Utils.changeToTheme(ConfigVisualActivity.this, Utils.THEME_DEFAULT);
                    }
                });
                builder.setNegativeButton(R.string.alerta_cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Cancelou", Toast.LENGTH_SHORT).show();
                    }
                });
                alerta = builder.create();
                alerta.show();
            }
        });

//        textLaranja.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick (View v){
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle(R.string.configure_alerta_visual_info_title);
//                builder.setMessage(R.string.configure_alerta_visual_info);
//                builder.setPositiveButton(R.string.alerta_selecionar, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(context, "Selecionou", Toast.LENGTH_SHORT).show();
//                        Utils.changeToTheme(ConfigVisualActivity.this, Utils.THEME_ORANGE);
//                    }
//                });
//                builder.setNegativeButton(R.string.alerta_cancelar, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(context, "Cancelou", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                alerta = builder.create();
//                alerta.show();
//            }
//        });
//
//        textVerde.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick (View v){
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle(R.string.configure_alerta_visual_info_title);
//                builder.setMessage(R.string.configure_alerta_visual_info);
//                builder.setPositiveButton(R.string.alerta_selecionar, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(context, "Selecionou", Toast.LENGTH_SHORT).show();
//                        Utils.changeToTheme(ConfigVisualActivity.this, Utils.THEME_GREEN);
//                    }
//                });
//                builder.setNegativeButton(R.string.alerta_cancelar, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(context, "Cancelou", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                alerta = builder.create();
//                alerta.show();
//            }
//        });
//
//        textMarrom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick (View v){
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle(R.string.configure_alerta_visual_info_title);
//                builder.setMessage(R.string.configure_alerta_visual_info);
//                builder.setPositiveButton(R.string.alerta_selecionar, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(context, "Selecionou", Toast.LENGTH_SHORT).show();
//                        Utils.changeToTheme(ConfigVisualActivity.this, Utils.THEME_BROWN);
//                    }
//                });
//                builder.setNegativeButton(R.string.alerta_cancelar, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(context, "Cancelou", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                alerta = builder.create();
//                alerta.show();
//            }
//        });
}

    private void loadViews(){
        textPadrao = findViewById(R.id.textVisualPadrao);
        textLaranja = findViewById(R.id.textVisualLaranja);
        textVerde = findViewById(R.id.textVisualVerde);
        textMarrom = findViewById(R.id.textVisualMarrom);

    }
}