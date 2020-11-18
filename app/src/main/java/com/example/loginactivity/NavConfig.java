package com.example.loginactivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginactivity.ui.NavActivity;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();
        View view = inflater.inflate(R.layout.fragment_nav_config, container, false);
        textGeral = view.findViewById(R.id.configureTextGeral);

        textSobre = view.findViewById(R.id.configureTextSobre);
        textSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Teste",Toast.LENGTH_LONG).show();
                alerta();
            }
        });
        return view;
    }
    private void alerta(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.configure_nav_sobre_info_title);
        builder.setMessage(R.string.configure_nav_sobre_info);
        builder.setPositiveButton(R.string.configure_nav_sobre_info_y, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(),"positivy button", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.configure_nav_sobre_info_n, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(),"negative button",Toast.LENGTH_SHORT).show();
            }
        });
        alerta = builder.create();
        alerta.show();
    }
}