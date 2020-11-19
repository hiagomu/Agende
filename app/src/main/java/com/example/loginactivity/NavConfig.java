package com.example.loginactivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class NavConfig extends Fragment {
    private TextView textGeral;
    private TextView textNotify;
    private TextView textVisual;
    private TextView textContato;
    private TextView textSobre;
    private AlertDialog alerta;
    private Context context;

    public NavConfig() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();

        View view = inflater.inflate(R.layout.fragment_nav_config, container, false);

        loadView(view);
        click();

        return view;
    }
    private void alertaSobre(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.configure_alerta_nav_sobre_info_title);
        builder.setMessage(R.string.configure_nav_sobre_info);
        builder.setPositiveButton(R.string.alerta_fechar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(),"Obrigado", Toast.LENGTH_SHORT).show();
            }
        });
        alerta = builder.create();
        alerta.show();
    }

    private void contatoEmail(){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","agende.senac@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        startActivity(Intent.createChooser(emailIntent, "Abrir com"));
    }

    private void click(){
        textVisual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navConfig_to_configVisual);
            }
        });

        textContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contatoEmail();
            }
        });

        textSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertaSobre();
            }
        });
    }

    private void loadView(View view){
        textGeral = view.findViewById(R.id.configureTextGeral);
        textVisual = view.findViewById(R.id.configureTextVisual);
        textContato = view.findViewById(R.id.configureTextContato);
        textSobre = view.findViewById(R.id.configureTextSobre);
    }
}